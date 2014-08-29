package net.anotheria.moskito.core.predefined;

import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.stats.impl.IntervalRegistry;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 11.11.12 23:00
 */
public class QueueStatsTest {
	//this is a very basic test without testing for important cases, just to start with.
	@Test
	public void testBasicFunctionality(){
		IntervalRegistry.getInstance().forceUpdateIntervalForTestingPurposes("snapshot");
		QueueStats stats = new QueueStats();
		stats.addEnqueued();
		stats.addDequeued();
		IntervalRegistry.getInstance().forceUpdateIntervalForTestingPurposes("snapshot");
		assertEquals(1, stats.getEnqueued("snapshot"));
		assertEquals(1, stats.getDequeued("snapshot"));
	}

	@Test public void testUnsetQueueStatDoesntCrashByToString(){
		assertNotNull(new QueueStats().toString());
		assertNotNull(new QueueStats().toStatsString());
	}

	@Test public void testGerValueByName(){
		QueueStats stats = new QueueStats();
		stats.addEnqueued();
		stats.addDequeued();
		assertNotNull(stats.getAvailableValueNames());
		assertEquals("1", stats.getValueByNameAsString("enq", null, TimeUnit.SECONDS));
		assertEquals("1", stats.getValueByNameAsString("deq", null, TimeUnit.SECONDS));
		assertNull(stats.getValueByNameAsString("FOO-UNKNOWN", null, TimeUnit.SECONDS));
	}
}
