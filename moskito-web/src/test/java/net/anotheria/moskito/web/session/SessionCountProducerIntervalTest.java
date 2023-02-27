package net.anotheria.moskito.web.session;

import net.anotheria.moskito.core.stats.impl.IntervalRegistry;
import net.anotheria.moskito.core.util.session.SessionCountStats;
import org.junit.Test;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * This test tests if the counter like session counting doesn't get reseted after an interval update.
 * In fact this is regression test for: https://jira.opensource.anotheria.net/browse/MSK-40.
 * @author lrosenberg
 *
 */
public class SessionCountProducerIntervalTest {
	@Test public void testSnapshot(){
	 	IntervalRegistry.getInstance().forceUpdateIntervalForTestingPurposes("snapshot");
	 	SessionCountProducer p = new SessionCountProducer();
		final HttpSession session = mock(HttpSession.class);

	 	for (int i=0; i<100; i++){
	 		p.sessionCreated(new HttpSessionEvent(session));
	 		if (i%2==0)
	 			p.sessionDestroyed(new HttpSessionEvent(session));
	 	}
	 	IntervalRegistry.getInstance().forceUpdateIntervalForTestingPurposes("snapshot");
	 	for (int i=0; i<100; i++){
	 		p.sessionCreated(new HttpSessionEvent(session));
	 		if (i%2==0)
	 			p.sessionDestroyed(new HttpSessionEvent(session));
	 	}
	 	IntervalRegistry.getInstance().forceUpdateIntervalForTestingPurposes("snapshot");
	 	SessionCountStats stats = p.getStats().get(0);
	 	//System.out.println("Stats "+stats);
	 	//System.out.println("Stats "+stats.toStatsString("snapshot"));
	 	
	 	assertEquals(100, stats.getCurrentSessionCount(null));
	 	assertEquals(100, stats.getCurrentSessionCount("snapshot"));
	}
}
