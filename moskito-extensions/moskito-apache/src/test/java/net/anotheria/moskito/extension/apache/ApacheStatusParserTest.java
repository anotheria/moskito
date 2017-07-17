package net.anotheria.moskito.extension.apache;

import net.anotheria.util.StringUtils;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Random;

/**
 * Test for ApacheStatusParser class.
 *
 * @author dzhmud
 */
public class ApacheStatusParserTest {

    private final String STATUS_TEMPLATE_EXTENDED = "localhost\n" +
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
            "Scoreboard: _________________________________W________________....................................................................................................\n";

    private final String STATUS_TEMPLATE = "localhost\n" +
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
            "ConnsTotal: 0\n" +
            "ConnsAsyncWriting: 0\n" +
            "ConnsAsyncKeepAlive: 0\n" +
            "ConnsAsyncClosing: 0\n" +
            "Scoreboard: ___W______________________________________________....................................................................................................\n";


    @Test
    @Ignore
    public void testParsingExtendedStatus() {
        System.out.println("_________________________________W________________....................................................................................................".length());
        int i = 0;
        for (String line : StringUtils.tokenize(STATUS_TEMPLATE_EXTENDED, '\n')) {
            System.out.println(++i + "\t"+line);
            int delimIndex = line.indexOf(": ");
            if (delimIndex > 0) {
                System.out.println(line.substring(0, delimIndex));
                System.out.println(line.substring(2+ delimIndex));
            }
        }
    }
    
    @Test
    @Ignore
    public void testScoreBoardParsing() {
//        final int length = 1000;
//        String scoreboard = generateScoreBoard(length);
//        ApacheStatus.Builder builder = ApacheStatus.newBuilder();
//
//        long start = System.currentTimeMillis();
//        for (int i = 0; i < 100; i++)
//        ApacheStatusParser.parseScoreboard(builder, scoreboard);
//        long duration1 = System.currentTimeMillis() - start;
//
//        start = System.currentTimeMillis();
//        for (int i = 0; i < 100; i++)
//        ApacheStatusParser.parseScoreboard2(builder, scoreboard);
//        long duration2 = System.currentTimeMillis() - start;
//
//        System.out.println(duration1);
//        System.out.println(duration2);

    }

    private static String generateScoreBoard(final int length) {
        final char[] chars = {'_', 'S', 'R', 'W', 'K', 'D', 'C', 'L', 'G', 'I', '.'};
        final StringBuilder sb = new StringBuilder(length);
        final Random r = new Random();
        for (int i = 0; i < length; i++) {
            sb.append(chars[r.nextInt(chars.length)]);
        }
        return sb.toString();
    }
    
}
