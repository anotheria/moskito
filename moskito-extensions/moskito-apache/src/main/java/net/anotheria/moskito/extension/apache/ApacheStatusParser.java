package net.anotheria.moskito.extension.apache;

import net.anotheria.util.StringUtils;
import org.slf4j.Logger;

import java.util.Arrays;

import static java.lang.Long.parseLong;
import static java.lang.Integer.parseInt;
import static java.lang.Double.parseDouble;
import static net.anotheria.util.StringUtils.charCount;


/**
 * Parser for Apache httpd server status page.
 *
 * @author dzhmud
 */
public final class ApacheStatusParser {

    private static final Logger LOGGER = ApacheMonitoringPlugin.LOGGER;

    private ApacheStatusParser() {}

    /**
     * Parse given status and create ApacheStatus object from it.
     * @param status apache status.
     * @return new ApacheStatus filled with parsed values.
     */
    public static ApacheStatus parse(String status) {
        if (StringUtils.isEmpty(status))
            throw new IllegalArgumentException("Empty status to parse!");

        final ApacheStatus.Builder builder = ApacheStatus.newBuilder();
        String[] lines = StringUtils.tokenize(status, '\n');
        //extract hostname separately
        builder.setHostname(lines[0]);
        lines = Arrays.copyOfRange(lines, 1, lines.length);

        for (String line : lines) {
            line = line.trim();
            final int delimIndex = line.indexOf(": ");
            if (delimIndex <= 0) {
                LOGGER.warn("ApacheStatusParser: failed to parse status line: '" + line + "'.");
            } else {
                final String metric = line.substring(0, delimIndex);
                final String value  = line.substring(2+ delimIndex);
                try {
                    switch (metric) {
                        case "ServerVersion": break;
                        case "ServerMPM": break;
                        case "Server Built": break;
                        case "CurrentTime": break;
                        case "RestartTime": break;
                        case "ParentServerConfigGeneration": break;
                        case "ParentServerMPMGeneration": break;
                        case "ServerUptimeSeconds": builder.setServerUptime(parseLong(value)); break;
                        case "ServerUptime": builder.setUptime(value); break;
                        case "Load1": builder.setLoad1(parseDouble(value)); break;
                        case "Load5": builder.setLoad5(parseDouble(value)); break;
                        case "Load15": builder.setLoad15(parseDouble(value)); break;
                        case "Total Accesses": builder.setTotalAccesses(parseLong(value)); break;
                        case "Total kBytes": builder.setTotalKbytes(parseLong(value)); break;
                        case "CPUUser": builder.setCpuUser(parseDouble(value)); break;
                        case "CPUSystem": builder.setCpuSystem(parseDouble(value)); break;
                        case "CPUChildrenUser": builder.setCpuChildrenUser(parseDouble(value)); break;
                        case "CPUChildrenSystem": builder.setCpuChildrenSystem(parseDouble(value)); break;
                        case "CPULoad": builder.setCpuLoad(parseDouble(value)); break;
                        case "Uptime": break;
                        case "ReqPerSec": builder.setRequestsPerSec(parseDouble(value)); break;
                        case "BytesPerSec": builder.setBytesPerSec(parseDouble(value)); break;
                        case "BytesPerReq": builder.setBytesPerRequest(parseDouble(value)); break;
                        case "BusyWorkers": builder.setWorkersBusy(parseInt(value)); break;
                        case "IdleWorkers": builder.setWorkersIdle(parseInt(value)); break;
                        case "ConnsTotal": builder.setConnectionsTotal(parseLong(value)); break;
                        case "ConnsAsyncWriting": builder.setConnectionsAsyncWriting(parseLong(value)); break;
                        case "ConnsAsyncKeepAlive": builder.setConnectionsAsyncKeepAlive(parseLong(value)); break;
                        case "ConnsAsyncClosing": builder.setConnectionsAsyncClosing(parseLong(value)); break;
                        case "Scoreboard": parseScoreboard(builder, value); break;
                        default: LOGGER.warn("ApacheStatusParser: found unhandled status line: '" + line + "'.");
                    }
                } catch (NumberFormatException e) {
                    LOGGER.error("ApacheStatusParser: failed to parse value '"+value+"'!", e);
                }
            }
        }
        return builder.createApacheStatus();
    }

    /**
     * Parse scoreboard line.
     * Scoreboard Key:
     *   "_" Waiting for Connection, "S" Starting up, "R" Reading Request,
     *   "W" Sending Reply, "K" Keepalive (read), "D" DNS Lookup,
     *   "C" Closing connection, "L" Logging, "G" Gracefully finishing,
     *   "I" Idle cleanup of worker, "." Open slot with no current process
     */
    private static void parseScoreboard(ApacheStatus.Builder builder, String scoreboard) {
        builder
                .setScoreboardWaitingForConnection(charCount(scoreboard, '_'))
                .setScoreboardStartingUp(charCount(scoreboard, 'S'))
                .setScoreboardReadingRequest(charCount(scoreboard, 'R'))
                .setScoreboardSendingReply(charCount(scoreboard, 'W'))
                .setScoreboardKeepalive(charCount(scoreboard, 'K'))
                .setScoreboardDnsLookup(charCount(scoreboard, 'D'))
                .setScoreboardClosingConnection(charCount(scoreboard, 'C'))
                .setScoreboardLogging(charCount(scoreboard, 'L'))
                .setScoreboardGracefullyFinishing(charCount(scoreboard, 'G'))
                .setScoreboardIdleCleanup(charCount(scoreboard, 'I'))
                .setScoreboardOpenSlot(charCount(scoreboard, '.'))
                .setScoreboardTotal(scoreboard.length());
    }

}
