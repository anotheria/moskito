package net.anotheria.moskito.extensions.monitoring.metrics;

import net.anotheria.moskito.core.stats.StatValueTypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class containing all metrics we gain from apache httpd server-status page.
 *
 * @author dzhmud
 */
public final class ApacheMetrics extends GenericMetrics {

    /**
     * Get ApacheMetrics by their names. Order of ApacheMetrics in the resulting list
     * is constant and does not depend on order of metric names.
     * {@link #SERVER_UPTIME} is added to the result even if its name is not passed.
     * @param metricsNames names of metrics.
     * @return list of metrics found.
     */
    public static List<ApacheMetrics> findMetrics(List<String> metricsNames) {
        if (metricsNames == null || metricsNames.isEmpty()) {
            return Collections.singletonList(ApacheMetrics.SERVER_UPTIME);
        }
        final List<ApacheMetrics> result = new ArrayList<>(metricsNames.size());
        for (ApacheMetrics metric : getMetricsOfType(ApacheMetrics.class)) {
            if (metricsNames.contains(metric.getCaption())) {
                result.add(metric);
                continue;
            }
            //we need 'uptime' to monitor apache restarts.
            if (metric == ApacheMetrics.SERVER_UPTIME) {
                result.add(metric);
            }
        }
        return result;
    }

    /**
     * Get all available ApacheMetrics.
     * @return list containing all available ApacheMetrics.
     */
    public static List<ApacheMetrics> getAll() {
        return getMetricsOfType(ApacheMetrics.class);
    }


    private ApacheMetrics(StatValueTypes type, String valueName, String ... strings ) {
        this(type, false, valueName, strings);
    }

    private ApacheMetrics(StatValueTypes type, boolean isRateValue, String valueName, String ... strings) {
        super(type, isRateValue, valueName, strings);
    }

    /**Apache hostname.*/
    public static final ApacheMetrics HOSTNAME = new ApacheMetrics(StatValueTypes.STRING, "Hostname", "Apache hostname."),
    /**Total number of access requests.*/
    TOTAL_ACCESSES = new ApacheMetrics(StatValueTypes.DIFFLONG, "Accesses", "Total accesses."),
    /**Total number of kilobytes served.*/
    TOTAL_KBYTES = new ApacheMetrics(StatValueTypes.DIFFLONG, "Total kB", "Total kilobytes served."),

   /**Requests per second.*/
    REQUESTS_PER_SEC = new ApacheMetrics(StatValueTypes.DIFFLONG, true, "Req/sec",
           "Requests per second.",
           "Requests per second. Calculated from 'Total accesses' stat, as apache calculates it for the whole uptime."),
    /**KBytes per second.*/
    KBYTES_PER_SEC = new ApacheMetrics(StatValueTypes.DIFFLONG, true, "KBytes/sec",
            "KBytes per second.",
            "KBytes per second. Calculated from 'totalKbytes' stat, as apache calculates it for the whole uptime."),

    /**Number of busy workers.*/
    WORKERS_BUSY = new ApacheMetrics(StatValueTypes.INT, "Busy", "Busy workers.", "Number of busy workers/threads."),
    /**Number of idle workers.*/
    WORKERS_IDLE = new ApacheMetrics(StatValueTypes.INT, "Idle", "Idle workers.", "Number of idle workers/threads."),

    /**Server uptime in seconds.*/
    SERVER_UPTIME = new ApacheMetrics(StatValueTypes.LONG, "Uptime(sec)", "Server uptime in seconds."),
    /**Server uptime.*/
    UPTIME = new ApacheMetrics(StatValueTypes.STRING, "Uptime", "Server uptime.", "Server uptime in human-readable form."),

    /**CPU Load.*/
    CPU_LOAD = new ApacheMetrics(StatValueTypes.DOUBLE, "CPU load",
            "CPU Load.", "CPU Load, in percents. Total CPU time utilized by apache divided by uptime."),
    /**CPU user load.*/
    CPU_USER = new ApacheMetrics(StatValueTypes.DOUBLE, "CPU user",
            "CPU user load.", "CPU time utilized by apache at user level."),
    /**CPU system load.*/
    CPU_SYSTEM = new ApacheMetrics(StatValueTypes.DOUBLE, "CPU system",
            "CPU system load.", "CPU time utilized by apache at system(kernel) level."),
    /**CPU of children user.*/
    CPU_CHILDREN_USER = new ApacheMetrics(StatValueTypes.DOUBLE, "CPU user(children).",
            "CPU user load by child workers.", "CPU time utilized by apache child workers at user level."),
    /**CPU of children system.*/
    CPU_CHILDREN_SYSTEM = new ApacheMetrics(StatValueTypes.DOUBLE, "CPU system(children).",
            "CPU system load by child workers.", "CPU time utilized by apache child workers at system(kernel) level."),

    /**Load average for the last minute.*/
    LOAD_1M = new ApacheMetrics(StatValueTypes.DOUBLE, "Load 1m",
            "Load average for the last minute.", "Unix 'load-average' of the host system."),
    /**Load average for the last 5 minutes.*/
    LOAD_5M = new ApacheMetrics(StatValueTypes.DOUBLE, "Load 5m",
            "Load average for the last 5 minutes.", "Unix 'load-average' of the host system."),
    /**Load average for the last 15 minutes.*/
    LOAD_15M = new ApacheMetrics(StatValueTypes.DOUBLE, "Load 15m",
            "Load average for the last 15 minutes.", "Unix 'load-average' of the host system."),

    /**Total connections.*/
    CONNECTIONS_TOTAL = new ApacheMetrics(StatValueTypes.LONG, "Con(total)", "Total connections."),
    /**Async connection writing.*/
    CONNECTIONS_ASYNC_WRITING = new ApacheMetrics(StatValueTypes.LONG, "Con(writing)", "Async connection writing."),
    /**Async keeped alive connections.*/
    CONNECTIONS_ASYNC_KEEPALIVE = new ApacheMetrics(StatValueTypes.LONG, "Con(keepAlive)", "Async keeped alive connections."),
    /**Async closed connections.*/
    CONNECTIONS_ASYNC_CLOSING = new ApacheMetrics(StatValueTypes.LONG, "Con(closing)", "Async closed connections."),

    /**Starting up.*/
    SCOREBOARD_STARTINGUP = new ApacheMetrics(StatValueTypes.INT, "SB S",
            "Starting up.", "Number of busy workers that are starting up."),
    /**Reading requests.*/
    SCOREBOARD_READINGREQUEST = new ApacheMetrics(StatValueTypes.INT, "SB R",
            "Reading request.", "Number of busy workers that are reading request."),
    /**Sending Reply.*/
    SCOREBOARD_SENDINGREPLY = new ApacheMetrics(StatValueTypes.INT, "SB W",
            "Sending reply(writing).", "Number of busy workers that are sending reply(writing)."),
    /**Keep alive.*/
    SCOREBOARD_KEEPALIVE = new ApacheMetrics(StatValueTypes.INT, "SB K",
            "Keep alive.", "Number of busy workers that are in 'keep-alive' state."),
    /**Logging.*/
    SCOREBOARD_LOGGING = new ApacheMetrics(StatValueTypes.INT, "SB L",
            "Logging.", "Number of busy workers that are logging."),
    /**Dns Lookups.*/
    SCOREBOARD_DNSLOOKUP = new ApacheMetrics(StatValueTypes.INT, "SB D",
            "Dns Lookup.", "Number of busy workers that are doing DNS lookup."),
    /**Closing connections.*/
    SCOREBOARD_CLOSINGCONNECTION = new ApacheMetrics(StatValueTypes.INT, "SB C",
            "Closing connection.", "Number of busy workers that are closing connection."),
    /**Gracefully finishing.*/
    SCOREBOARD_GRACEFULLYFINISHING = new ApacheMetrics(StatValueTypes.INT, "SB G",
            "Gracefully finishing.", "Number of busy workers that are gracefully finishing(read about graceful restart.)."),
    /**Idle cleanups of workers.*/
    SCOREBOARD_IDLECLEANUP = new ApacheMetrics(StatValueTypes.INT, "SB I",
            "Idle cleanup of worker.", "Number of busy workers that are in 'idle cleanup' state."),
    /**Open slots with no current process.*/
    SCOREBOARD_OPENSLOT = new ApacheMetrics(StatValueTypes.INT, "SB .",
            "Open slot with no current process.",
            "Number of open slots with no worker running(but configuration allows it to be started)."),
    /**Waiting for connections.*/
    SCOREBOARD_WAITINGFORCONNECTION = new ApacheMetrics(StatValueTypes.INT, "SB _",
            "Waiting for connection.", "Number of currently idle(waiting for connections) workers."),
    /**Total.*/
    SCOREBOARD_TOTAL = new ApacheMetrics(StatValueTypes.INT, "SB total",
            "Scoreboard total.", "Maximum number of worker threads.");



    @Override
    public String toString() {
        return "ApacheMetrics{" +
                "type=" + getType() +
                ", isRateMetric=" + isRateMetric() +
                ", caption='" + getCaption() + '\'' +
                ", shortExpl='" + getShortExplanation() + '\'' +
                ", explanation='" + getExplanation() + '\'' +
                '}';
    }

}
