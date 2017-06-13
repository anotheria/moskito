package net.anotheria.moskito.extensions.monitoring.parser;

import net.anotheria.moskito.extensions.monitoring.metrics.ApacheMetrics;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Test for ApacheStatusParser class.
 *
 * @author dzhmud
 */
public class ApacheStatusParserTest {

    private static final String STATUS_TEMPLATE_EXTENDED = "localhost\n" +
            "ServerVersion: Apache/2.4.25 (Ubuntu)\n" +
            "ServerMPM: event\n" +
            "Server Built: 2017-02-10T16:53:43\n" +
            "CurrentTime: Monday, 22-May-2017 14:40:00 EEST\n" +
            "RestartTime: Monday, 22-May-2017 14:26:32 EEST\n" +
            "ParentServerConfigGeneration: 1\n" +
            "ParentServerMPMGeneration: 0\n" +
            "ServerUptimeSeconds: 807\n" +
            "ServerUptime: 13 minutes 27 seconds\n" +
            "Load1: 0.49\n" +
            "Load5: 0.82\n" +
            "Load15: 0.62\n" +
            "Total Accesses: 9\n" +
            "Total kBytes: 14\n" +
            "CPUUser: .12\n" +
            "CPUSystem: .14\n" +
            "CPUChildrenUser: 0\n" +
            "CPUChildrenSystem: 0\n" +
            "CPULoad: .0322181\n" +
            "Uptime: 807\n" +
            "ReqPerSec: .0111524\n" +
            "BytesPerSec: 17.7646\n" +
            "BytesPerReq: 1592.89\n" +
            "BusyWorkers: 1\n" +
            "IdleWorkers: 49\n" +
            "ConnsTotal: 1\n" +
            "ConnsAsyncWriting: 0\n" +
            "ConnsAsyncKeepAlive: 1\n" +
            "ConnsAsyncClosing: 0\n" +
            "Scoreboard: _SSRRRWWWWKKKKKDDCCCLLLLLGIII...\n";

    private static final String STATUS_TEMPLATE = "localhost\n" +
            "ServerVersion: Apache/2.4.25 (Ubuntu)\n" +
            "ServerMPM: event\n" +
            "Server Built: 2017-02-10T16:53:43\n" +
            "CurrentTime: Monday, 22-May-2017 14:25:55 EEST\n" +
            "RestartTime: Monday, 22-May-2017 14:25:32 EEST\n" +
            "ParentServerConfigGeneration: 1\n" +
            "ParentServerMPMGeneration: 0\n" +
            "ServerUptimeSeconds: 23\n" +
            "ServerUptime: 23 seconds\n" +
            "Load1: 0.48\n" +
            "Load5: 0.32\n" +
            "Load15: 0.30\n" +
            "BusyWorkers: 1\n" +
            "IdleWorkers: 49\n" +
            "ConnsTotal: 12\n" +
            "ConnsAsyncWriting: 3\n" +
            "ConnsAsyncKeepAlive: 4\n" +
            "ConnsAsyncClosing: 5\n" +
            "Scoreboard: _SSRRRWWWWKKKKKDDCCCLLLLLGIII...\n";

    private static final double delta = 0.00001;

    @Test
    public void testParsingStatus() {
        StatusData<ApacheMetrics> status = ApacheStatusParser.INSTANCE.parse(STATUS_TEMPLATE);

        assertEquals("localhost", status.get(ApacheMetrics.HOSTNAME));
        assertEquals(null, status.get(ApacheMetrics.TOTAL_ACCESSES));
        assertEquals(null, status.get(ApacheMetrics.TOTAL_KBYTES));
        assertEquals(null, status.get(ApacheMetrics.REQUESTS_PER_SEC));
        assertEquals(null, status.get(ApacheMetrics.KBYTES_PER_SEC));
        assertEquals(1, status.get(ApacheMetrics.WORKERS_BUSY));
        assertEquals(49, status.get(ApacheMetrics.WORKERS_IDLE));
        assertEquals(23L, status.get(ApacheMetrics.SERVER_UPTIME));
        assertEquals("23 seconds", status.get(ApacheMetrics.UPTIME));
        assertEquals(null, status.get(ApacheMetrics.CPU_LOAD));
        assertEquals(null, status.get(ApacheMetrics.CPU_USER));
        assertEquals(null, status.get(ApacheMetrics.CPU_SYSTEM));
        assertEquals(null, status.get(ApacheMetrics.CPU_CHILDREN_USER));
        assertEquals(null, status.get(ApacheMetrics.CPU_CHILDREN_SYSTEM));
        assertEquals(0.48, (double)status.get(ApacheMetrics.LOAD_1M), delta);
        assertEquals(0.32, (double)status.get(ApacheMetrics.LOAD_5M), delta);
        assertEquals(0.30, (double)status.get(ApacheMetrics.LOAD_15M), delta);
        assertEquals(12L, status.get(ApacheMetrics.CONNECTIONS_TOTAL));
        assertEquals(3L, status.get(ApacheMetrics.CONNECTIONS_ASYNC_WRITING));
        assertEquals(4L, status.get(ApacheMetrics.CONNECTIONS_ASYNC_KEEPALIVE));
        assertEquals(5L, status.get(ApacheMetrics.CONNECTIONS_ASYNC_CLOSING));

        assertEquals(2, status.get(ApacheMetrics.SCOREBOARD_STARTINGUP));
        assertEquals(3, status.get(ApacheMetrics.SCOREBOARD_READINGREQUEST));
        assertEquals(4, status.get(ApacheMetrics.SCOREBOARD_SENDINGREPLY));
        assertEquals(5, status.get(ApacheMetrics.SCOREBOARD_KEEPALIVE));
        assertEquals(5, status.get(ApacheMetrics.SCOREBOARD_LOGGING));
        assertEquals(2, status.get(ApacheMetrics.SCOREBOARD_DNSLOOKUP));
        assertEquals(3, status.get(ApacheMetrics.SCOREBOARD_CLOSINGCONNECTION));
        assertEquals(1, status.get(ApacheMetrics.SCOREBOARD_GRACEFULLYFINISHING));
        assertEquals(3, status.get(ApacheMetrics.SCOREBOARD_IDLECLEANUP));
        assertEquals(3, status.get(ApacheMetrics.SCOREBOARD_OPENSLOT));
        assertEquals(1, status.get(ApacheMetrics.SCOREBOARD_WAITINGFORCONNECTION));
        assertEquals(32, status.get(ApacheMetrics.SCOREBOARD_TOTAL));

    }
    
    @Test
    public void testParsingExtendedStatus() {
        StatusData<ApacheMetrics> status = ApacheStatusParser.INSTANCE.parse(STATUS_TEMPLATE_EXTENDED);

        assertEquals("localhost", status.get(ApacheMetrics.HOSTNAME));
        assertEquals(9L, status.get(ApacheMetrics.TOTAL_ACCESSES));
        assertEquals(14L, status.get(ApacheMetrics.TOTAL_KBYTES));
        assertEquals(9L, status.get(ApacheMetrics.REQUESTS_PER_SEC));
        assertEquals(14L, status.get(ApacheMetrics.KBYTES_PER_SEC));
        assertEquals(1, status.get(ApacheMetrics.WORKERS_BUSY));
        assertEquals(49, status.get(ApacheMetrics.WORKERS_IDLE));
        assertEquals(807L, status.get(ApacheMetrics.SERVER_UPTIME));
        assertEquals("13 minutes 27 seconds", status.get(ApacheMetrics.UPTIME));
        assertEquals(0.0322181, (double)status.get(ApacheMetrics.CPU_LOAD), 0.0000001);
        assertEquals(0.12, (double)status.get(ApacheMetrics.CPU_USER), delta);
        assertEquals(0.14, (double)status.get(ApacheMetrics.CPU_SYSTEM), delta);
        assertEquals(0.0, (double)status.get(ApacheMetrics.CPU_CHILDREN_USER), delta);
        assertEquals(0.0, (double)status.get(ApacheMetrics.CPU_CHILDREN_SYSTEM), delta);
        assertEquals(0.49, (double)status.get(ApacheMetrics.LOAD_1M), delta);
        assertEquals(0.82, (double)status.get(ApacheMetrics.LOAD_5M), delta);
        assertEquals(0.62, (double)status.get(ApacheMetrics.LOAD_15M), delta);
        assertEquals(1L, status.get(ApacheMetrics.CONNECTIONS_TOTAL));
        assertEquals(0L, status.get(ApacheMetrics.CONNECTIONS_ASYNC_WRITING));
        assertEquals(1L, status.get(ApacheMetrics.CONNECTIONS_ASYNC_KEEPALIVE));
        assertEquals(0L, status.get(ApacheMetrics.CONNECTIONS_ASYNC_CLOSING));

        assertEquals(2, status.get(ApacheMetrics.SCOREBOARD_STARTINGUP));
        assertEquals(3, status.get(ApacheMetrics.SCOREBOARD_READINGREQUEST));
        assertEquals(4, status.get(ApacheMetrics.SCOREBOARD_SENDINGREPLY));
        assertEquals(5, status.get(ApacheMetrics.SCOREBOARD_KEEPALIVE));
        assertEquals(5, status.get(ApacheMetrics.SCOREBOARD_LOGGING));
        assertEquals(2, status.get(ApacheMetrics.SCOREBOARD_DNSLOOKUP));
        assertEquals(3, status.get(ApacheMetrics.SCOREBOARD_CLOSINGCONNECTION));
        assertEquals(1, status.get(ApacheMetrics.SCOREBOARD_GRACEFULLYFINISHING));
        assertEquals(3, status.get(ApacheMetrics.SCOREBOARD_IDLECLEANUP));
        assertEquals(3, status.get(ApacheMetrics.SCOREBOARD_OPENSLOT));
        assertEquals(1, status.get(ApacheMetrics.SCOREBOARD_WAITINGFORCONNECTION));
        assertEquals(32, status.get(ApacheMetrics.SCOREBOARD_TOTAL));
    }

    @Test
    public void testExceptionsAndCornerCases() {
        //null is not OK
        try {
            ApacheStatusParser.INSTANCE.parse(null);
            fail("Expected IllegalArgumentException!");
        } catch (IllegalArgumentException e){}
        //empty String is still not OK
        try {
            ApacheStatusParser.INSTANCE.parse("");
            fail("Expected IllegalArgumentException!");
        } catch (IllegalArgumentException e){}
        //
        assertEquals("hostname!", ApacheStatusParser.INSTANCE.parse("hostname!").get(ApacheMetrics.HOSTNAME));

        //test that line with wrong data is silently ignored and next lines are parsed.
        String wrongDataType = "hostname\nServerUptimeSeconds: 1024\nTotal Accesses: bazillion\nBusyWorkers: 14";
        StatusData<ApacheMetrics> status = ApacheStatusParser.INSTANCE.parse(wrongDataType);
        assertEquals("hostname", status.get(ApacheMetrics.HOSTNAME));
        assertEquals(1024L, status.get(ApacheMetrics.SERVER_UPTIME));
        assertEquals(null, status.get(ApacheMetrics.TOTAL_ACCESSES));
        assertEquals(null, status.get(ApacheMetrics.TOTAL_KBYTES));
        assertEquals(14, status.get(ApacheMetrics.WORKERS_BUSY));

        assertEquals(0.0, (double)ApacheStatusParser.INSTANCE.parse("hostname\nCPUUser: 0\n").get(ApacheMetrics.CPU_USER), delta);
        assertEquals(null, ApacheStatusParser.INSTANCE.parse("hostname\n: \nCPUUser: sdf\nUNKNOWN_FIELD: value\n").get(ApacheMetrics.CPU_USER));
    }
    

}
