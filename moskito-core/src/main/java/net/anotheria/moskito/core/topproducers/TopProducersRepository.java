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
 * A producer is only ranked in a category it actually had a value in during the interval, an idle producer is not
 * ranked at all. Producers that disappear from the producer registry lose their accumulated entry on the next update,
 * so that the ranking doesn't grow forever in setups with short living producers.
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
		this(MoskitoConfigurationHolder.getConfiguration().getTopProducersConfig(),
				new ProducerRegistryAPIFactory().createProducerRegistryAPI());
		IntervalRegistry.getInstance().getInterval(intervalName).addSecondaryIntervalListener(this);
		LOGGER.debug("Started top producers ranking on interval {}", intervalName);
	}

	/**
	 * Creates a repository that is not attached to an interval yet, whoever creates it has to drive it by calling
	 * {@link #intervalUpdated(Interval)}. Used by the singleton constructor, which attaches itself to the configured
	 * interval afterwards, and by tests, which score the intervals themselves.
	 * @param config the configuration to take the interval name from.
	 * @param aProducerRegistryAPI the api to read the producers to rank from.
	 */
	TopProducersRepository(TopProducersConfig config, IProducerRegistryAPI aProducerRegistryAPI) {
		intervalName = config.getIntervalName();
		producerRegistryAPI = aProducerRegistryAPI;
	}

	@Override
	public void intervalUpdated(Interval interval) {

		final Map<Category, ProducerTemporaryEntry> entries = new HashMap<>();
		for (final Category c : Category.values())
			entries.put(c, new ProducerTemporaryEntry(c.name(), 0));

		final Set<String> registeredProducerIds = new HashSet<>();
		final List<IStatsProducer> producers = producerRegistryAPI.getAllProducers();
		//nothing to rank - and, more important, nothing to base the cleanup at the end of this method on, an empty
		//registry would drop every accumulated entry.
		if (producers.isEmpty())
			return;

		for (final IStatsProducer producer : producers) {
			//collected for all producers, not only for the rankable ones - a producer that is registered but not
			//rankable right now (empty stats for example) should keep the entry it accumulated so far.
			registeredProducerIds.add(producer.getProducerId());

			List<?> stats = producer.getStats();
			if (stats == null || stats.isEmpty())
				continue;

            //We don't consider builtin producers, for example ServiceStatistics.
            if (producer.getCategory().equals("builtin"))
                continue;
			//for now, we only handle request oriented stats, maybe we will handle more in the future.
			if (!(stats.get(0) instanceof RequestOrientedStats))
				continue;

			final RequestOrientedStats stat = (RequestOrientedStats) stats.get(0);
			for (Category c : Category.values()) {
				final long aValue = c.extractValue(stat.getValueByNameAsString(c.getValueName(), intervalName, TimeUnit.NANOSECONDS));
				//A producer that did nothing in this category in this interval isn't ranked in it. Ranking it would
				//create an entry for every idle producer and keep it forever, and it would pull the average and the
				//bottom score of every producer that is only active from time to time down to zero.
				if (aValue > 0)
					entries.get(c).insert(new ProducerTemporaryEntry(producer.getProducerId(), aValue));
			}
		}

		//Producers that left the registry must not keep their entry for the lifetime of the jvm.
		producerEntries.keySet().retainAll(registeredProducerIds);

		//now we have ranked producers for last interval, we can create total ranks.
		for (final Category c : Category.values()) {
			ProducerTemporaryEntry temporaryEntry = entries.get(c);
			while (temporaryEntry != null) {
				if (registeredProducerIds.contains(temporaryEntry.getProducerId()))
					addScore(c, temporaryEntry, temporaryEntry.getScore());

				List<ProducerTemporaryEntry> same = temporaryEntry.getSame();
				if (same != null && !same.isEmpty())
					for (ProducerTemporaryEntry s : same)
						if (registeredProducerIds.contains(s.getProducerId()))
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
	 * {@code limit} entries. Producers that were never ranked in the category are skipped. Producers with an equal
	 * score are ordered by producer id, so that repeated calls return a stable order. The ranking is based on the
	 * scores as they were at the time of the call, the interval thread keeps updating them afterwards.
	 * @param targetCategory the category to rank by.
	 * @param limit the maximum number of entries to return, a value {@code <= 0} means no limit.
	 * @return the top ranked producer entries.
	 */
	public List<ProducerEntry> getTopProducers(Category targetCategory, int limit) {
		//The score has to be read exactly once per entry and the copy has to be sorted. Sorting on the live score lets
		//the interval thread change the sort keys while the sort is running, which makes TimSort bail out with
		//"Comparison method violates its general contract!" as soon as there are more than 32 entries.
		record ScoredEntry(ProducerEntry entry, long score) {}

		List<ScoredEntry> scored = new ArrayList<>(producerEntries.size());
		for (ProducerEntry entry : producerEntries.values()) {
			ProducerEntryValue value = entry.getValue(targetCategory);
			if (value != null)
				scored.add(new ScoredEntry(entry, value.getCumulatedScore()));
		}

		scored.sort(Comparator.comparingLong(ScoredEntry::score).reversed()
				.thenComparing(scoredEntry -> scoredEntry.entry().getProducerId()));

		final int size = limit > 0 && scored.size() > limit ? limit : scored.size();
		List<ProducerEntry> ret = new ArrayList<>(size);
		for (int i = 0; i < size; i++)
			ret.add(scored.get(i).entry());
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
