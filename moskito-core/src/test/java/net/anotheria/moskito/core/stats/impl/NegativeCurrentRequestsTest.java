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
		assertEquals(3, stats.getMaxCurrentRequests("1m"), "The interval maximum should include the in-flight baseline");

		// No new request starts in the next interval, but all three remain in flight.
		((IntervalImpl)interval).update();
		assertEquals(3, stats.getCurrentRequests("1m"), "The current-request level should carry across rollovers");
		assertEquals(3, stats.getMaxCurrentRequests("1m"), "The interval maximum should start at the in-flight baseline");

		stats.notifyRequestFinished();
		stats.notifyRequestFinished();
		stats.notifyRequestFinished();

		((IntervalImpl)interval).update();

		assertEquals(0, stats.getCurrentRequests(), "Current requests should be 0");
		assertEquals(0, stats.getCurrentRequests("1m"), "Current requests should be 0 after requests finish across an interval rollover");
		assertEquals(3, stats.getMaxCurrentRequests("1m"), "The interval maximum should retain the starting in-flight level");
	}
}
