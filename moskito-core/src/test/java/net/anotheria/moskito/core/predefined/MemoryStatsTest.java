package net.anotheria.moskito.core.predefined;

import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.impl.IntervalRegistry;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Memory values are sampled levels. An interval without a sample must keep the last sampled value instead of dropping to 0,
 * and min/max must start each interval at that value.
 */
public class MemoryStatsTest {

	private static final String INTERVAL = "1m";
	private static final long GB = 1024L * 1024 * 1024;

	private static Interval[] intervals(){
		return new Interval[]{IntervalRegistry.getInstance().getInterval(INTERVAL)};
	}

	private static void rollover(){
		IntervalRegistry.getInstance().forceUpdateIntervalForTestingPurposes(INTERVAL);
	}

	@Test public void testMemoryStatsKeepValueInIntervalWithoutSample(){
		MemoryStats stats = new MemoryStats("test", intervals());
		stats.updateMemoryValue(100);
		rollover();
		assertEquals(100, stats.getCurrent(INTERVAL));

		//no sample in this interval, e.g. the updater was late under load.
		rollover();
		assertEquals(100, stats.getCurrent(INTERVAL), "current should keep the last sample");
		assertEquals(100, stats.getMin(INTERVAL), "min should start at the last sample");
		assertEquals(100, stats.getMax(INTERVAL), "max should start at the last sample");
	}

	@Test public void testMemoryStatsMinIsNotAlwaysZero(){
		MemoryStats stats = new MemoryStats("test", intervals());
		stats.updateMemoryValue(100);
		stats.updateMemoryValue(80);
		stats.updateMemoryValue(120);
		assertEquals(80, stats.getMin(null));
		assertEquals(120, stats.getMax(null));

		rollover();
		assertEquals(80, stats.getMin(INTERVAL));
		assertEquals(120, stats.getMax(INTERVAL));

		stats.updateMemoryValue(110);
		rollover();
		//the interval started at 120 (last sample), then 110 was sampled.
		assertEquals(110, stats.getMin(INTERVAL));
		assertEquals(120, stats.getMax(INTERVAL));
	}

	@Test public void testMemoryPoolStatsKeepValuesInIntervalWithoutSample(){
		MemoryPoolStats stats = new MemoryPoolStats("pool", intervals());
		stats.setInit(10);
		stats.setUsed(100);
		stats.setCommited(200);
		stats.setMax(300);
		rollover();
		rollover();

		assertEquals(10, stats.getInit(INTERVAL));
		assertEquals(100, stats.getUsed(INTERVAL));
		assertEquals(100, stats.getMinUsed(INTERVAL));
		assertEquals(100, stats.getMaxUsed(INTERVAL));
		assertEquals(200, stats.getCommited(INTERVAL));
		assertEquals(200, stats.getMinCommited(INTERVAL));
		assertEquals(200, stats.getMaxCommited(INTERVAL));
		assertEquals(300, stats.getMax(INTERVAL));
	}

	@Test public void testMemoryPoolStatsMinAboveIntegerMaxValue(){
		MemoryPoolStats stats = new MemoryPoolStats("pool", intervals());
		stats.setUsed(4 * GB);
		stats.setUsed(3 * GB);
		stats.setCommited(5 * GB);
		assertEquals(3 * GB, stats.getMinUsed(null), "min used must not be capped at Integer.MAX_VALUE");
		assertEquals(5 * GB, stats.getMinCommited(null), "min commited must not be capped at Integer.MAX_VALUE");
	}
}
