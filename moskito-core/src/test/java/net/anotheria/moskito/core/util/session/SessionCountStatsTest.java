package net.anotheria.moskito.core.util.session;


import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.stats.impl.IntervalRegistry;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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

    @Test
    public void testGetValueByNameAsString(){
        SessionCountStats stats = SessionCountFactory.DEFAULT_INSTANCE.createStatsObject("sessions");

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

        assertEquals("5",stats.getValueByNameAsString("cur", "1m", TimeUnit.NANOSECONDS)); //current 5 in last minute
        assertEquals("5",stats.getValueByNameAsString("current", "default", TimeUnit.NANOSECONDS)); //current 5 in total

        assertEquals("5",stats.getValueByNameAsString("new", "1m", TimeUnit.NANOSECONDS)); //created 5 in last minute
        assertEquals("15",stats.getValueByNameAsString("new", "default", TimeUnit.NANOSECONDS)); //created 15 in total

        assertEquals("0",stats.getValueByNameAsString("del", "1m", TimeUnit.NANOSECONDS)); //deleted 0 in last minute
        assertEquals("10",stats.getValueByNameAsString("del", "default", TimeUnit.NANOSECONDS)); //deleted 10 in total

        assertEquals("0",stats.getValueByNameAsString("min", "1m", TimeUnit.NANOSECONDS)); //min 0 in last minute

        assertEquals("10",stats.getValueByNameAsString("max", "default", TimeUnit.NANOSECONDS)); //max 10 in total
        assertEquals("0",stats.getValueByNameAsString("min", "default", TimeUnit.NANOSECONDS)); //min 0 in total

    }

    @Test
    public void testToStatsString(){
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

        String statsString1m = stats.toStatsString("1m", TimeUnit.NANOSECONDS);//timeunit doesnt matter here.
        String statsStringDefault = stats.toStatsString("default", TimeUnit.NANOSECONDS);//timeunit doesnt matter here.
        assertTrue(statsString1m.contains("Cur: 5"));
        assertTrue(statsString1m.contains("Min: 0"));
        assertTrue(statsString1m.contains("Max: 5"));
        assertTrue(statsString1m.contains("New: 5"));
        assertTrue(statsString1m.contains("Del: 0"));

        assertTrue(statsStringDefault.contains("Cur: 5"));
        assertTrue(statsStringDefault.contains("Min: 0"));
        assertTrue(statsStringDefault.contains("Max: 10"));
        assertTrue(statsStringDefault.contains("New: 15"));
        assertTrue(statsStringDefault.contains("Del: 10"));
    }
}
