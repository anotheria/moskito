package net.anotheria.moskito.core.stats.impl;

import net.anotheria.moskito.core.predefined.ServiceStats;
import net.anotheria.moskito.core.stats.Interval;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class NegativeCurrentRequestsTest {
	@Test public void testIntervalUpdateBehaviour(){
		Interval interval = IntervalRegistry.getInstance().getInterval("1m");
		Interval[] arr = new Interval[]{interval};
		ServiceStats stats = new ServiceStats("foo", arr);
		stats.addRequest();
		stats.addRequest();
		stats.addRequest();
		stats.notifyRequestFinished();
		stats.notifyRequestFinished();
		stats.notifyRequestFinished();
		
		assertEquals(0, stats.getCurrentRequests(), "Current requests should be 0");
		assertEquals(0, stats.getCurrentRequests("1m"), "Current requests should be 0");
		

		stats.addRequest();
		stats.addRequest();
		stats.addRequest();
		
		//stats.
		((IntervalImpl)interval).update();
		
		assertEquals(3, stats.getCurrentRequests(), "Current requests should be 3");
		assertEquals(3, stats.getCurrentRequests("1m"), "Current requests should be 3");

		stats.notifyRequestFinished();
		stats.notifyRequestFinished();
		stats.notifyRequestFinished();

		((IntervalImpl)interval).update();

		assertEquals(0, stats.getCurrentRequests(), "Current requests should be 0");
		//TODO -> this is yet broken !assertEquals(0, stats.getCurrentRequests("1m"), "Current requests should be 0");
	}
}
