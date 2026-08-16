package net.anotheria.moskito.core.topproducers;

import net.anotheria.moskito.core.config.producers.TopProducersConfig;
import net.anotheria.moskito.core.predefined.ServiceStats;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.IProducerFilter;
import net.anotheria.moskito.core.registry.IProducerRegistryAPI;
import net.anotheria.moskito.core.registry.IntervalInfo;
import net.anotheria.moskito.core.registry.NoSuchProducerException;
import net.anotheria.moskito.core.stats.impl.IntervalRegistry;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * Tests the ranking of the {@link TopProducersRepository}. The repository is created with its own producer registry api
 * instead of the global one, so that the ranking contains exactly the producers of the test at hand - other tests in
 * the same jvm register producers in the global registry and reset it underneath us.
 *
 * @author lrosenberg
 */
public class TopProducersRepositoryTest {

	/**
	 * Interval the test producers are ranked on.
	 */
	private static final String INTERVAL = "1m";

	@Test
	public void testRankingIsDescendingAndStable() {
		//two producers with an equal value on purpose, they share a rank and must come back in a stable, id based order.
		TopProducersRepository repository = rank(
				new TestProducer("light", 100),
				new TestProducer("medium-b", 200),
				new TestProducer("heavy", 300),
				new TestProducer("medium-a", 200));

		List<ProducerEntry> ranked = repository.getTopProducers(Category.REQUESTS, 0);

		assertEquals(List.of("heavy", "medium-a", "medium-b", "light"),
				ranked.stream().map(ProducerEntry::getProducerId).toList());
	}

	@Test
	public void testScorePerInterval() {
		TopProducersRepository repository = rank(
				new TestProducer("light", 100),
				new TestProducer("medium-b", 200),
				new TestProducer("heavy", 300),
				new TestProducer("medium-a", 200));

		//the score of one interval is the number of producers ranked below, tied producers share their score and the
		//internal zero-valued sentinel counts as one element below everyone with a value > 0.
		assertEquals(4, scoreOf(repository, "heavy"));
		assertEquals(2, scoreOf(repository, "medium-a"));
		assertEquals(2, scoreOf(repository, "medium-b"));
		assertEquals(1, scoreOf(repository, "light"));
	}

	@Test
	public void testLimitIsHonored() {
		TopProducersRepository repository = rank(
				new TestProducer("light", 100),
				new TestProducer("medium", 200),
				new TestProducer("heavy", 300));

		assertEquals(List.of("heavy", "medium"),
				repository.getTopProducers(Category.REQUESTS, 2).stream().map(ProducerEntry::getProducerId).toList());
		assertEquals(3, repository.getTopProducers(Category.REQUESTS, 0).size());
	}

	@Test
	public void testIdleProducersAreNotRanked() {
		TopProducersRepository repository = rank(new TestProducer("busy", 100), new TestProducer("idle", 0));

		assertEquals(List.of("busy"), rankedIds(repository));
		assertEquals(1, repository.getAllProducerEntries().size(), "an idle producer must not get an entry at all");
	}

	@Test
	public void testProducersLeavingTheRegistryAreDropped() {
		TestProducerRegistryAPI registry =
				new TestProducerRegistryAPI(new TestProducer("heavy", 300), new TestProducer("light", 100));
		TopProducersRepository repository = new TopProducersRepository(new TopProducersConfig(), registry);

		score(repository);
		assertEquals(List.of("heavy", "light"), rankedIds(repository));

		registry.unregister("light");
		score(repository);

		assertEquals(List.of("heavy"), rankedIds(repository));
		assertEquals(1, repository.getAllProducerEntries().size(),
				"the entry of a producer that left the registry must be dropped");
	}

	/**
	 * Creates a repository ranking exactly the given producers and lets it score one interval.
	 */
	private static TopProducersRepository rank(TestProducer... producers) {
		TopProducersRepository repository =
				new TopProducersRepository(new TopProducersConfig(), new TestProducerRegistryAPI(producers));
		score(repository);
		return repository;
	}

	/**
	 * Lets the given repository score one interval.
	 */
	private static void score(TopProducersRepository repository) {
		//roll the interval, so that the interval scoped values of the stats contain the requests added to them since
		//the last roll.
		IntervalRegistry.getInstance().forceUpdateIntervalForTestingPurposes(INTERVAL);
		repository.intervalUpdated(IntervalRegistry.getInstance().getInterval(INTERVAL));
	}

	private static List<String> rankedIds(TopProducersRepository repository) {
		return repository.getTopProducers(Category.REQUESTS, 0).stream().map(ProducerEntry::getProducerId).toList();
	}

	private static long scoreOf(TopProducersRepository repository, String producerId) {
		for (ProducerEntry entry : repository.getAllProducerEntries()) {
			if (entry.getProducerId().equals(producerId)) {
				ProducerEntryValue value = entry.getValue(Category.REQUESTS);
				assertNotNull(value, "producer " + producerId + " was not ranked by requests");
				return value.getCumulatedScore();
			}
		}
		throw new AssertionError("producer " + producerId + " is not ranked at all");
	}

	/**
	 * A minimal request oriented producer with a fixed number of requests on its first stats object.
	 */
	private static class TestProducer implements IStatsProducer {

		private final String producerId;
		private final List<IStats> stats = new ArrayList<>();

		TestProducer(String aProducerId, int requests) {
			producerId = aProducerId;

			ServiceStats serviceStats = new ServiceStats("cumulated");
			for (int i = 0; i < requests; i++)
				serviceStats.addRequest();
			stats.add(serviceStats);
		}

		@Override
		public List<IStats> getStats() {
			return stats;
		}

		@Override
		public String getProducerId() {
			return producerId;
		}

		@Override
		public String getCategory() {
			return "test";
		}

		@Override
		public String getSubsystem() {
			return "test";
		}
	}

	/**
	 * A producer registry api serving a fixed set of producers, so that the ranking under test is unaffected by the
	 * producers other tests register in the global registry.
	 */
	private static class TestProducerRegistryAPI implements IProducerRegistryAPI {

		private final List<IStatsProducer> producers;

		TestProducerRegistryAPI(TestProducer... someProducers) {
			producers = new ArrayList<>(Arrays.asList(someProducers));
		}

		void unregister(String producerId) {
			producers.removeIf(producer -> producer.getProducerId().equals(producerId));
		}

		@Override
		public List<IStatsProducer> getAllProducers() {
			return producers;
		}

		@Override
		public IStatsProducer getProducer(String producerId) {
			for (IStatsProducer producer : producers)
				if (producer.getProducerId().equals(producerId))
					return producer;
			throw new NoSuchProducerException(producerId);
		}

		@Override
		public List<IStatsProducer> getAllProducersByCategory(String category) {
			throw new UnsupportedOperationException("not needed for ranking");
		}

		@Override
		public List<IStatsProducer> getAllProducersBySubsystem(String subsystem) {
			throw new UnsupportedOperationException("not needed for ranking");
		}

		@Override
		public List<IStatsProducer> getProducers(IProducerFilter... filters) {
			throw new UnsupportedOperationException("not needed for ranking");
		}

		@Override
		public List<String> getCategories() {
			throw new UnsupportedOperationException("not needed for ranking");
		}

		@Override
		public List<String> getSubsystems() {
			throw new UnsupportedOperationException("not needed for ranking");
		}

		@Override
		public List<IntervalInfo> getPresentIntervals() {
			throw new UnsupportedOperationException("not needed for ranking");
		}
	}
}
