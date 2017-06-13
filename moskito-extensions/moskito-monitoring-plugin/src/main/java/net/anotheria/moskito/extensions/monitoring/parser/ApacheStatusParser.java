package net.anotheria.moskito.extensions.monitoring.parser;

import net.anotheria.moskito.extensions.monitoring.metrics.ApacheMetrics;
import net.anotheria.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

import static net.anotheria.util.StringUtils.charCount;

/**
 * Parser for Apache httpd server status page.
 *
 * @author dzhmud
 */
public final class ApacheStatusParser implements StatusParser<String, StatusData> {

    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ApacheStatusParser.class);

    /** Singleton instance. */
    public static final ApacheStatusParser INSTANCE = new ApacheStatusParser();

    private ApacheStatusParser() {}

    /**
     * Parse given status and create StatusData object from it.
     * @param status apache status.
     * @return new StatusData filled with parsed values.
     */
    public StatusData<ApacheMetrics> parse(String status) {
        if (StringUtils.isEmpty(status))
            throw new IllegalArgumentException("Empty status to parse!");

        final StatusData<ApacheMetrics> result = new StatusData<>();
        String[] lines = StringUtils.tokenize(status, '\n');
        //extract hostname separately
        result.put(ApacheMetrics.HOSTNAME, lines[0]);
        lines = Arrays.copyOfRange(lines, 1, lines.length);

        for (String line : lines) {
            line = line.trim();
            final int delimIndex = line.indexOf(": ");
            if (delimIndex <= 0) {
                LOGGER.warn("ApacheStatusParser: failed to parse status line: '" + line + "'.");
            } else {
                final String metric = line.substring(0, delimIndex);
                final String value  = line.substring(2+ delimIndex);
                switch (metric) {
                    case "ServerVersion": break;
                    case "ServerMPM": break;
                    case "Server Built": break;
                    case "CurrentTime": break;
                    case "RestartTime": break;
                    case "ParentServerConfigGeneration": break;
                    case "ParentServerMPMGeneration": break;
                    case "ServerUptimeSeconds": result.put(ApacheMetrics.SERVER_UPTIME, value); break;
                    case "ServerUptime": result.put(ApacheMetrics.UPTIME, value); break;
                    case "Load1": result.put(ApacheMetrics.LOAD_1M, value); break;
                    case "Load5": result.put(ApacheMetrics.LOAD_5M, value); break;
                    case "Load15": result.put(ApacheMetrics.LOAD_15M, value); break;
                    case "Total Accesses":
                        result.put(ApacheMetrics.TOTAL_ACCESSES, value);
                        result.put(ApacheMetrics.REQUESTS_PER_SEC, value);
                        break;
                    case "Total kBytes":
                        result.put(ApacheMetrics.TOTAL_KBYTES, value);
                        result.put(ApacheMetrics.KBYTES_PER_SEC, value);
                        break;
                    case "CPUUser": result.put(ApacheMetrics.CPU_USER, value); break;
                    case "CPUSystem": result.put(ApacheMetrics.CPU_SYSTEM, value); break;
                    case "CPUChildrenUser": result.put(ApacheMetrics.CPU_CHILDREN_USER, value); break;
                    case "CPUChildrenSystem": result.put(ApacheMetrics.CPU_CHILDREN_SYSTEM, value); break;
                    case "CPULoad": result.put(ApacheMetrics.CPU_LOAD, value); break;
                    case "Uptime": break;
                    case "ReqPerSec": break;
                    case "BytesPerSec": break;
                    case "BytesPerReq": break;
                    case "BusyWorkers": result.put(ApacheMetrics.WORKERS_BUSY, value); break;
                    case "IdleWorkers": result.put(ApacheMetrics.WORKERS_IDLE, value); break;
                    case "ConnsTotal": result.put(ApacheMetrics.CONNECTIONS_TOTAL, value); break;
                    case "ConnsAsyncWriting": result.put(ApacheMetrics.CONNECTIONS_ASYNC_WRITING, value); break;
                    case "ConnsAsyncKeepAlive": result.put(ApacheMetrics.CONNECTIONS_ASYNC_KEEPALIVE, value); break;
                    case "ConnsAsyncClosing": result.put(ApacheMetrics.CONNECTIONS_ASYNC_CLOSING, value); break;
                    case "Scoreboard": parseScoreboard(result, value); break;
                    default: LOGGER.warn("ApacheStatusParser: found unhandled status line: '" + line + "'.");
                }
            }
        }
        return result;
    }

    /**
     * Parse scoreboard line.
     * Scoreboard Key:
     *   "_" Waiting for Connection, "S" Starting up, "R" Reading Request,
     *   "W" Sending Reply, "K" Keepalive (read), "D" DNS Lookup,
     *   "C" Closing connection, "L" Logging, "G" Gracefully finishing,
     *   "I" Idle cleanup of worker, "." Open slot with no current process
     */
    private static void parseScoreboard(final StatusData<ApacheMetrics> statusData, final String scoreboard) {
        statusData.put(ApacheMetrics.SCOREBOARD_WAITINGFORCONNECTION, charCount(scoreboard, '_'));
        statusData.put(ApacheMetrics.SCOREBOARD_STARTINGUP, charCount(scoreboard, 'S'));
        statusData.put(ApacheMetrics.SCOREBOARD_READINGREQUEST, charCount(scoreboard, 'R'));
        statusData.put(ApacheMetrics.SCOREBOARD_SENDINGREPLY, charCount(scoreboard, 'W'));
        statusData.put(ApacheMetrics.SCOREBOARD_KEEPALIVE, charCount(scoreboard, 'K'));
        statusData.put(ApacheMetrics.SCOREBOARD_DNSLOOKUP, charCount(scoreboard, 'D'));
        statusData.put(ApacheMetrics.SCOREBOARD_CLOSINGCONNECTION, charCount(scoreboard, 'C'));
        statusData.put(ApacheMetrics.SCOREBOARD_LOGGING, charCount(scoreboard, 'L'));
        statusData.put(ApacheMetrics.SCOREBOARD_GRACEFULLYFINISHING, charCount(scoreboard, 'G'));
        statusData.put(ApacheMetrics.SCOREBOARD_IDLECLEANUP, charCount(scoreboard, 'I'));
        statusData.put(ApacheMetrics.SCOREBOARD_OPENSLOT, charCount(scoreboard, '.'));
        statusData.put(ApacheMetrics.SCOREBOARD_TOTAL, scoreboard.length());
    }

}
