package net.java.dev.moskito.web.session;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

import net.anotheria.anoprise.mocking.MockFactory;

import org.junit.Test;
import static org.junit.Assert.*;
/**
 * This test tests if the counter like session counting doesn't get reseted after an interval update.
 * In fact this is regression test for: https://jira.opensource.anotheria.net/browse/MSK-40.
 * @author lrosenberg
 *
 */
public class SessionCountProducerIntervalTest {
	@Test public void testSnapshot(){
	 	net.java.dev.moskito.core.stats.impl.IntervalRegistry.getInstance().forceUpdateIntervalForTestingPurposes("snapshot");
	 	SessionCountProducer p = new SessionCountProducer();
		final HttpSession session = MockFactory.createMock(HttpSession.class);

	 	for (int i=0; i<100; i++){
	 		p.sessionCreated(new HttpSessionEvent(session));
	 		if (i%2==0)
	 			p.sessionDestroyed(new HttpSessionEvent(session));
	 	}
	 	net.java.dev.moskito.core.stats.impl.IntervalRegistry.getInstance().forceUpdateIntervalForTestingPurposes("snapshot");
	 	for (int i=0; i<100; i++){
	 		p.sessionCreated(new HttpSessionEvent(session));
	 		if (i%2==0)
	 			p.sessionDestroyed(new HttpSessionEvent(session));
	 	}
	 	net.java.dev.moskito.core.stats.impl.IntervalRegistry.getInstance().forceUpdateIntervalForTestingPurposes("snapshot");
	 	SessionCountStats stats = (SessionCountStats) p.getStats().get(0);
	 	//System.out.println("Stats "+stats);
	 	//System.out.println("Stats "+stats.toStatsString("snapshot"));
	 	
	 	assertEquals(100, stats.getCurrentSessionCount(null));
	 	assertEquals(100, stats.getCurrentSessionCount("snapshot"));
	}
}
