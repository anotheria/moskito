package net.anotheria.moskito.core.predefined;

import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.stats.impl.IntervalRegistry;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 07.12.12 13:33
 */
public class ThreadStateStatsTest {
	@Test
	public void testMinMax(){
		IntervalRegistry.getInstance().forceUpdateIntervalForTestingPurposes("snapshot");
		ThreadStateStats stats = new ThreadStateStats("foo");
		stats.updateCurrentValue(100);

		IntervalRegistry.getInstance().forceUpdateIntervalForTestingPurposes("snapshot");
		assertEquals(100, stats.getMin("snapshot"));
		assertEquals(100, stats.getMax("snapshot"));
		assertEquals(100, stats.getCurrent("snapshot"));

		stats.updateCurrentValue(100);
		stats.updateCurrentValue(50);
		stats.updateCurrentValue(750);
		stats.updateCurrentValue(150);

		IntervalRegistry.getInstance().forceUpdateIntervalForTestingPurposes("snapshot");
		assertEquals(50, stats.getMin("snapshot"));
		assertEquals(750, stats.getMax("snapshot"));
		assertEquals(150, stats.getCurrent("snapshot"));

	}

	public void testGetValueByName(){
		IntervalRegistry.getInstance().forceUpdateIntervalForTestingPurposes("snapshot");
		ThreadStateStats stats = new ThreadStateStats("foo");

		stats.updateCurrentValue(100);
		stats.updateCurrentValue(50);
		stats.updateCurrentValue(750);
		stats.updateCurrentValue(150);
		IntervalRegistry.getInstance().forceUpdateIntervalForTestingPurposes("snapshot");

		assertEquals("50", stats.getValueByNameAsString("min", "snapshot", (TimeUnit)null));
		assertEquals("750", stats.getValueByNameAsString("max", "snapshot", (TimeUnit)null));
		assertEquals("150", stats.getValueByNameAsString("current", "snapshot", (TimeUnit)null));
	}
}
