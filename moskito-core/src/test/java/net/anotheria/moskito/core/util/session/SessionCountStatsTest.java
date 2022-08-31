package net.anotheria.moskito.core.util.session;


import net.anotheria.moskito.core.stats.impl.IntervalRegistry;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SessionCountStatsTest {
    @Test
    public void testSessionCreationDeletion(){
        SessionCountStats stats = new SessionCountStats();

        //First we create 10 sessions, then we destroy 10 sessions, then we create 5 again.

        for (int i=0; i<10; i++)
            stats.notifySessionCreated();

        IntervalRegistry.getInstance().forceUpdateIntervalForTestingPurposes("1m");

        for (int i=0; i<10; i++)
            stats.notifySessionDestroyed();
        IntervalRegistry.getInstance().forceUpdateIntervalForTestingPurposes("1m");

        for (int i=0; i<5; i++)
            stats.notifySessionCreated();
        IntervalRegistry.getInstance().forceUpdateIntervalForTestingPurposes("1m");

        assertEquals(5,stats.getCreatedSessionCount("1m")); //created 5 in last minute
        assertEquals(15,stats.getCreatedSessionCount("default")); //created 15 in total minute

        assertEquals(0, stats.getDestroyedSessionCount("1m")); //destroyed 0 in last minute
        assertEquals(10, stats.getDestroyedSessionCount("default")); //totally destroyed 10

        assertEquals(5, stats.getCurrentSessionCount("1m")); //in last 1 minute we had 5
        assertEquals(5, stats.getMaxSessionCount("1m")); //max in  1 minute we had was 5
        //TODO why does this assert fail?
        assertEquals(0, stats.getMinSessionCount("1m")); //min in  1 minute we had was 5

        assertEquals(10, stats.getMaxSessionCount("default")); //max in  default we had was 10
        assertEquals(0, stats.getMinSessionCount("default")); //min in  default we had was 0

    }
}
