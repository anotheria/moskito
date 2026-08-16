package net.anotheria.moskito.core.topproducers;

import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.producers.TopProducersConfig;
import net.anotheria.moskito.core.predefined.RequestOrientedStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.IProducerRegistryAPI;
import net.anotheria.moskito.core.registry.ProducerRegistryAPIFactory;
import net.anotheria.moskito.core.stats.IIntervalListener;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.stats.impl.IntervalRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Continuously ranks all registered producers by a set of {@link Category categories} (requests, total time, errors,
 * error rate, max concurrent requests) and accumulates a score per producer over time. On every update of the
 * configured interval the producers are ranked for the last interval, and the position in this ranking is added as a
 * score to the producer's accumulated {@link ProducerEntry}. This way producers that consistently consume the most
 * resources accumulate the highest score and can be presented as optimization targets.
 * <p>
 * This is a pure, ui-independent moskito-core component; presentation layers (webui, mcp, ...) read the ranking through
 * {@link #getTopProducers(Category, int)} and map it to their own transfer objects.
 *
 * @author lrosenberg
 * @since 17.05.16 23:38
 */
public final class TopProducersRepository implements IIntervalListener {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TopProducersRepository.class);

	/**
	 * The singleton instance.
	 */
	private static volatile TopProducersRepository INSTANCE;

	/**
	 * Api to access the producer registry.
	 */
	private final IProducerRegistryAPI producerRegistryAPI;

	/**
	 * Accumulated ranking per producer id.
	 */
	private final ConcurrentMap<String, ProducerEntry> producerEntries = new ConcurrentHashMap<>();

	/**
	 * Name of the interval the ranking is updated on.
	 */
	private final String intervalName;

	/**
	 * Returns the singleton instance of the TopProducersRepository, creating (and thereby starting) it on first access.
	 * @return the one and only instance.
	 */
	public static TopProducersRepository getInstance() {
		if (INSTANCE == null) {
			synchronized (TopProducersRepository.class) {
				if (INSTANCE == null)
					INSTANCE = new TopProducersRepository();
			}
		}
		return INSTANCE;
	}

	private TopProducersRepository() {
		TopProducersConfig config = MoskitoConfigurationHolder.getConfiguration().getTopProducersConfig();
		intervalName = config.getIntervalName();
		producerRegistryAPI = new ProducerRegistryAPIFactory().createProducerRegistryAPI();
		IntervalRegistry.getInstance().getInterval(intervalName).addSecondaryIntervalListener(this);
		LOGGER.debug("Started top producers ranking on interval {}", intervalName);
	}

	@Override
	public void intervalUpdated(Interval interval) {

		final Map<Category, ProducerTemporaryEntry> entries = new HashMap<>();
		for (final Category c : Category.values())
			entries.put(c, new ProducerTemporaryEntry(c.name(), 0));

		final Set<String> producerIds = new HashSet<>();
		final List<IStatsProducer> producers = producerRegistryAPI.getAllProducers();
		if (producers.isEmpty())
			return;

		for (final IStatsProducer producer : producers) {
			List<?> stats = producer.getStats();
			if (stats == null || stats.size() == 0)
				continue;

            //We don't consider builtin producers, for example ServiceStatistics.
            if (producer.getCategory().equals("builtin"))
                continue;
			//for now, we only handle request oriented stats, maybe we will handle more in the future.
			if (!(stats.get(0) instanceof RequestOrientedStats))
				continue;
			producerIds.add(producer.getProducerId());

			final RequestOrientedStats stat = (RequestOrientedStats) stats.get(0);
			for (Category c : Category.values()) {
				final long aValue = c.extractValue(stat.getValueByNameAsString(c.getValueName(), intervalName, TimeUnit.NANOSECONDS));
				entries.get(c).insert(new ProducerTemporaryEntry(producer.getProducerId(), aValue));
			}
		}

		//now we have ranked producers for last interval, we can create total ranks.
		for (final Category c : Category.values()) {
			ProducerTemporaryEntry temporaryEntry = entries.get(c);
			while (temporaryEntry != null) {
				if (producerIds.contains(temporaryEntry.getProducerId()))
					addScore(c, temporaryEntry, temporaryEntry.getScore());

				List<ProducerTemporaryEntry> same = temporaryEntry.getSame();
				if (same != null && !same.isEmpty())
					for (ProducerTemporaryEntry s : same)
						if (producerIds.contains(s.getProducerId()))
							addScore(c, s, temporaryEntry.getScore());

				temporaryEntry = temporaryEntry.getNext();
			}
		}
	}

	private void addScore(Category c, ProducerTemporaryEntry temporaryEntry, int score) {
		final String producerId = temporaryEntry.getProducerId();
		ProducerEntry entry = producerEntries.get(producerId);
		if (entry == null) {
			entry = new ProducerEntry();
			entry.setProducerId(producerId);

			IStatsProducer aProducerFromRegistry = producerRegistryAPI.getProducer(producerId);
			entry.setProducerSubsystem(aProducerFromRegistry.getSubsystem());
			entry.setProducerCategory(aProducerFromRegistry.getCategory());

			final ProducerEntry old = producerEntries.putIfAbsent(entry.getProducerId(), entry);
			if (old != null)
				entry = old;
		}
		entry.addScore(c, score);
	}

	/**
	 * Returns the producers with the highest accumulated score in the given category, ordered descending, at most
	 * {@code limit} entries. Producers that were never ranked in the category are skipped.
	 * @param targetCategory the category to rank by.
	 * @param limit the maximum number of entries to return, a value {@code <= 0} means no limit.
	 * @return the top ranked producer entries.
	 */
	public List<ProducerEntry> getTopProducers(Category targetCategory, int limit) {
		List<ProducerEntry> ret = new ArrayList<>();
		for (ProducerEntry entry : producerEntries.values()) {
			if (entry.getValue(targetCategory) != null)
				ret.add(entry);
		}

		ret.sort(Comparator.comparingLong(
				(ProducerEntry e) -> e.getValue(targetCategory).getCumulatedScore()).reversed());

		if (limit > 0 && ret.size() > limit)
			return new ArrayList<>(ret.subList(0, limit));
		return ret;
	}

	/**
	 * Returns all currently ranked producer entries. The returned list is a snapshot copy.
	 * @return all ranked producer entries.
	 */
	public List<ProducerEntry> getAllProducerEntries() {
		return new ArrayList<>(producerEntries.values());
	}

}
