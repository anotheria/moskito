package net.java.dev.moskito.core.stats.impl;

import static org.junit.Assert.assertEquals;
import net.java.dev.moskito.core.predefined.ServiceStats;
import net.java.dev.moskito.core.stats.Interval;

import org.junit.Test;

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
		
		assertEquals("Current requests should be 0", 0, stats.getCurrentRequests());
		assertEquals("Current requests should be 0", 0, stats.getCurrentRequests("1m"));
		

		stats.addRequest();
		stats.addRequest();
		stats.addRequest();
		
		//stats.
		((IntervalImpl)interval).update();
		
		assertEquals("Current requests should be 3", 3, stats.getCurrentRequests());
		assertEquals("Current requests should be 3", 3, stats.getCurrentRequests("1m"));

		stats.notifyRequestFinished();
		stats.notifyRequestFinished();
		stats.notifyRequestFinished();

		((IntervalImpl)interval).update();

		assertEquals("Current requests should be 0", 0, stats.getCurrentRequests());
		//TODO -> this is yet broken !assertEquals("Current requests should be 0", 0, stats.getCurrentRequests("1m"));
	}
}
