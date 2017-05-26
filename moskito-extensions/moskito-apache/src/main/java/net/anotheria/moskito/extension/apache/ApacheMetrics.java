package net.anotheria.moskito.extension.apache;

import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.stats.DefaultIntervals;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.StatValueTypes;
import net.anotheria.moskito.core.stats.impl.IntervalRegistry;
import net.anotheria.moskito.core.stats.impl.StatValueFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Enum containing all metrics we gain from apache httpd server-status page.
 *
 * @author dzhmud
 */
public enum ApacheMetrics {

    /**Apache hostname.*/
    HOSTNAME(StatValueTypes.STRING, "Hostname", "Apache hostname.") {
        @Override String getStringValue(ApacheStatus status) {
            return status.getHostname();
        }
    },
    /**Total number of access requests.*/
    TOTAL_ACCESSES(StatValueTypes.DIFFLONG, "Accesses", "Total accesses.") {
        @Override long getLongValue(ApacheStatus status) {
            return status.getTotalAccesses();
        }
    },
    /**Total number of kilobytes served.*/
    TOTAL_KBYTES(StatValueTypes.DIFFLONG, "Total kB", "Total kilobytes served.") {
        @Override long getLongValue(ApacheStatus status) {
            return status.getTotalKbytes();
        }
    },


    /**Requests per second.*/
    REQUESTS_PER_SEC(StatValueTypes.DIFFLONG, true, "Req/sec", "Requests per second.", "//TODO ") {//TODO
        @Override long getLongValue(ApacheStatus status) {
//            return status.getRequestsPerSec();
            return status.getTotalAccesses();
        }
    },
    /**KBytes per second.*/
    KBYTES_PER_SEC(StatValueTypes.DIFFLONG, true, "KBytes/sec", "KBytes per second.", "//TODO ") {//TODO
        @Override long getLongValue(ApacheStatus status) {
//            return status.getBytesPerSec();
            return status.getTotalKbytes();
        }
    },
//    /**Bytes per request.*/
//    BYTES_PER_REQUEST(StatValueTypes.DIFFLONG, true, "Bytes/req", "Bytes per request.", "//TODO ") {//TODO
//        @Override double getDoubleValue(ApacheStatus status) {
//            return status.getBytesPerRequest();
//        }
//    },


    /**Number of busy workers.*/
    WORKERS_BUSY(StatValueTypes.INT, "Busy", "Busy workers.", "Number of busy workers/threads.") {
        @Override int getIntValue(ApacheStatus status) {
            return status.getWorkersBusy();
        }
    },
    /**Number of idle workers.*/
    WORKERS_IDLE(StatValueTypes.INT, "Idle", "Idle workers.", "Number of idle workers/threads.") {
        @Override int getIntValue(ApacheStatus status) {
            return status.getWorkersIdle();
        }
    },


    /**Server uptime in seconds.*/
    SERVER_UPTIME(StatValueTypes.LONG, "Uptime(sec)", "Server uptime in seconds.") {
        @Override long getLongValue(ApacheStatus status) {
            return status.getServerUptime();
        }
    },
    /**Server uptime.*/
    UPTIME(StatValueTypes.STRING, "Uptime", "Server uptime.", "Server uptime in human-readable form.") {
        @Override String getStringValue(ApacheStatus status) {
            return status.getUptime();
        }
    },


    /**CPU Load.*/
    CPU_LOAD(StatValueTypes.DOUBLE, "CPU load", "CPU Load.", "CPU Load, in percents.//TODO") {//TODO
        @Override double getDoubleValue(ApacheStatus status) {
            return status.getCpuLoad();
        }
    },
    /**CPU user load.*/
    CPU_USER(StatValueTypes.DOUBLE, "CPU user", "CPU user load.", "//TODO ") {//TODO
        @Override double getDoubleValue(ApacheStatus status) {
            return status.getCpuUser();
        }
    },
    /**CPU system load.*/
    CPU_SYSTEM(StatValueTypes.DOUBLE, "CPU system", "CPU system load.", "//TODO ") {//TODO
        @Override double getDoubleValue(ApacheStatus status) {
            return status.getCpuSystem();
        }
    },
    /**CPU of children user.*/
    CPU_CHILDREN_USER(StatValueTypes.DOUBLE, "CPU user(children).", "CPU of children user.", "//TODO ") {//TODO
        @Override double getDoubleValue(ApacheStatus status) {
            return status.getCpuChildrenUser();
        }
    },
    /**CPU of children system.*/
    CPU_CHILDREN_SYSTEM(StatValueTypes.DOUBLE, "CPU system(children).", "CPU of children system.", "//TODO ") {//TODO
        @Override double getDoubleValue(ApacheStatus status) {
            return status.getCpuChildrenSystem();
        }
    },


    /**Load average for the last minute.*/
    LOAD_1M(StatValueTypes.DOUBLE, "Load 1m", "Load average for the last minute.", "//TODO ") {//TODO
        @Override double getDoubleValue(ApacheStatus status) {
            return status.getLoad1();
        }
    },
    /**Load average for the last 5 minutes.*/
    LOAD_5M(StatValueTypes.DOUBLE, "Load 5m", "Load average for the last 5 minutes.", "//TODO ") {//TODO
        @Override double getDoubleValue(ApacheStatus status) {
            return status.getLoad5();
        }
    },
    /**Load average for the last 15 minutes.*/
    LOAD_15M(StatValueTypes.DOUBLE, "Load 15m", "Load average for the last 15 minutes.", "//TODO ") {//TODO
        @Override double getDoubleValue(ApacheStatus status) {
            return status.getLoad15();
        }
    },


    /**Total connections.*/
    CONNECTIONS_TOTAL(StatValueTypes.LONG, "Con(total)", "Total connections.") {
        @Override long getLongValue(ApacheStatus status) {
            return status.getConnectionsTotal();
        }
    },
    /**Async connection writing.*/
    CONNECTIONS_ASYNC_WRITING(StatValueTypes.LONG, "Con(writing)", "Async connection writing.") {
        @Override long getLongValue(ApacheStatus status) {
            return status.getConnectionsAsyncWriting();
        }
    },
    /**Async keeped alive connections.*/
    CONNECTIONS_ASYNC_KEEPALIVE(StatValueTypes.LONG, "Con(keepAlive)", "Async keeped alive connections.") {
        @Override long getLongValue(ApacheStatus status) {
            return status.getConnectionsAsyncKeepAlive();
        }
    },
    /**Async closed connections.*/
    CONNECTIONS_ASYNC_CLOSING(StatValueTypes.LONG, "Con(closing)", "Async closed connections.") {
        @Override long getLongValue(ApacheStatus status) {
            return status.getConnectionsAsyncClosing();
        }
    },


    /**Starting up.*/
    SCOREBOARD_STARTINGUP(StatValueTypes.INT, "SB S", "Starting up.") {
        @Override int getIntValue(ApacheStatus status) {
            return status.getScoreboardStartingUp();
        }
    },
    /**Reading requests.*/
    SCOREBOARD_READINGREQUEST(StatValueTypes.INT, "SB R", "Reading requests.") {
        @Override int getIntValue(ApacheStatus status) {
            return status.getScoreboardReadingRequest();
        }
    },
    /**Sending Reply.*/
    SCOREBOARD_SENDINGREPLY(StatValueTypes.INT, "SB W", "Sending reply(writing).") {
        @Override int getIntValue(ApacheStatus status) {
            return status.getScoreboardSendingReply();
        }
    },
    /**Keep alive.*/
    SCOREBOARD_KEEPALIVE(StatValueTypes.INT, "SB K", "Keep alive.") {
        @Override int getIntValue(ApacheStatus status) {
            return status.getScoreboardKeepalive();
        }
    },
    /**Logging.*/
    SCOREBOARD_LOGGING(StatValueTypes.INT, "SB L", "Logging.") {
        @Override int getIntValue(ApacheStatus status) {
            return status.getScoreboardLogging();
        }
    },
    /**Dns Lookups.*/
    SCOREBOARD_DNSLOOKUP(StatValueTypes.INT, "SB D", "Dns Lookups.") {
        @Override int getIntValue(ApacheStatus status) {
            return status.getScoreboardDnsLookup();
        }
    },
    /**Closing connections.*/
    SCOREBOARD_CLOSINGCONNECTION(StatValueTypes.INT, "SB C", "Closing connections.") {
        @Override int getIntValue(ApacheStatus status) {
            return status.getScoreboardClosingConnection();
        }
    },
    /**Gracefully finishing.*/
    SCOREBOARD_GRACEFULLYFINISHING(StatValueTypes.INT, "SB G", "Gracefully finishing.") {
        @Override int getIntValue(ApacheStatus status) {
            return status.getScoreboardGracefullyFinishing();
        }
    },
    /**Idle cleanups of workers.*/
    SCOREBOARD_IDLECLEANUP(StatValueTypes.INT, "SB I", "Idle cleanups of workers.") {
        @Override int getIntValue(ApacheStatus status) {
            return status.getScoreboardIdleCleanup();
        }
    },
    /**Open slots with no current process.*/
    SCOREBOARD_OPENSLOT(StatValueTypes.INT, "SB .", "Open slots with no current process.") {
        @Override int getIntValue(ApacheStatus status) {
            return status.getScoreboardOpenSlot();
        }
    },
    /**Waiting for connections.*/
    SCOREBOARD_WAITINGFORCONNECTION(StatValueTypes.INT, "SB _", "Waiting for connections.") {
        @Override int getIntValue(ApacheStatus status) {
            return status.getScoreboardWaitingForConnection();
        }
    },
    /**Total.*/
    SCOREBOARD_TOTAL(StatValueTypes.INT, "SB total", "Scoreboard total.") {
        @Override int getIntValue(ApacheStatus status) {
            return status.getScoreboardTotal();
        }
    };






    /** ValueName variable. Also used as caption by decorator. */
    public final String valueName;
    /** Short explanation and full explanation variables.*/
    private final String shortExpl, explanation;
    /** ValueType of current metric. */
    final StatValueTypes type;
    /** If true, value per second will be calculated. */
    private final boolean isRateValue;

    ApacheMetrics(StatValueTypes type, String valueName, String ... strings ) {
        this(type, false, valueName, strings);
    }

    ApacheMetrics(StatValueTypes type, boolean isRateValue, String valueName, String ... strings) {
        this.type = type;
        this.isRateValue = isRateValue;
        this.valueName = valueName;
        this.shortExpl = (strings == null || strings.length == 0) ? valueName : strings[0];
        this.explanation = (strings == null || strings.length < 2) ? shortExpl : strings[1];
    }

    StatValue createStatValue() {
//        if (type != StatValueTypes.DIFFLONG) {
            return StatValueFactory.createStatValue(type, valueName, Constants.getDefaultIntervals());
//        } else {
//            //TODO
//            //for DIFFLONG type use upgraded diffLong value holders that ignore first interval.
//            final IValueHolderFactory factory = SkipFirstDiffLongValueHolderFactory.INSTANCE;
//            final TypeAwareStatValue statValue = new TypeAwareStatValueImpl(valueName, type, factory);
//            for (Interval interval : Constants.getDefaultIntervals()) {
//                statValue.addInterval(interval);
//            }
//            return statValue;
//        }
    }

    long getLongValue(ApacheStatus status) {
        return 0;
    }
    int getIntValue(ApacheStatus status) {
        return 0;
    }
    double getDoubleValue(ApacheStatus status) {
        return 0;
    }
    String getStringValue(ApacheStatus status) {
        return "";
    }



    public boolean isDoubleValue() {
        return isRateValue || type == StatValueTypes.DOUBLE;
    }

    public boolean isStringValue() {
        return type == StatValueTypes.STRING;
    }

    public boolean isIntegerValue() {
        return type == StatValueTypes.INT;
    }

    /**
     * This method shall return the current value as String.
     * @return the current value
     */
    public String getValueAsString(StatValue statValue, String intervalName) {
        if (isRateValue) {
            return String.valueOf(getValueAsDouble(statValue, intervalName));
        }
        return statValue.getValueAsString(intervalName);
    }

    /**
     * This method shall return the current value as long.
     * @return the current value
     */
    public long getValueAsLong(StatValue statValue, String intervalName) {
        if (isRateValue) {
            return (long)(getValueAsDouble(statValue, intervalName));
        }
        return statValue.getValueAsLong(intervalName);
    }

    /**
     * This method shall return the current value as integer.
     * @return the current value
     */
    public int getValueAsInt(StatValue statValue, String intervalName) {
        if (isRateValue) {
            return (int)(getValueAsDouble(statValue, intervalName));
        }
        return statValue.getValueAsInt(intervalName);
    }

    /**
     * This method shall return the current value as double.
     * @return the current value
     */
    public double getValueAsDouble(StatValue statValue, String intervalName) {
        if (isRateValue) {
            //check for DEFAULT can be removed when same check removed from statValueImpl
            if( "default".equals(intervalName)) {
                intervalName = DefaultIntervals.ONE_MINUTE.getName();
            }
            double result = statValue.getValueAsLong(intervalName);
            long duration = IntervalRegistry.getInstance().getInterval(intervalName).getLength();
            if (duration > 0)
                result /= duration;
            return result;
        }
        return statValue.getValueAsDouble(intervalName);
    }

    /**
     * Get ApacheMetrics enum value by its name.
     * @return ApacheMetrics enum value if found.
     * @throws IllegalArgumentException if there is no ApacheMetrics value with provided name.
     */
    static ApacheMetrics getByValueName(String valueName) {
        for (ApacheMetrics metric : values()) {
            if (metric.valueName.equals(valueName))
                return metric;
        }
        throw new IllegalArgumentException("ApacheMetrics.getByValueName() should not be called with unknown value name.");
    }

    /**
     * Get captions of all available ApacheMetrics.
     * @return array of metric captions.
     */
    public static String[] getCaptions() {
        return extract(Extractor.CAPTIONS);
    }

    /**
     * Get short explanations of all available ApacheMetrics.
     * @return array of metric short explanations.
     */
    public static String[] getShortExplanations() {
        return extract(Extractor.SHORT_EXPLANATIONS);
    }

    /**
     * Get full explanations of all available ApacheMetrics.
     * @return array of metric full explanations.
     */
    public static String[] getExplanations() {
        return extract(Extractor.EXPLANATIONS);
    }

    private static String[] extract(Extractor e) {
        List<String> strings = new ArrayList<>(values().length);
        for (ApacheMetrics metric : values()) {
            strings.add(e.extract(metric));
        }
        return strings.toArray(new String[strings.size()]);
    }

    private interface Extractor {
        String extract(ApacheMetrics metric);

        Extractor CAPTIONS = new Extractor() {
            @Override public String extract(ApacheMetrics metric) {
                return metric.valueName;
            }
        };
        Extractor EXPLANATIONS = new Extractor() {
            @Override public String extract(ApacheMetrics metric) {
                return metric.explanation;
            }
        };
        Extractor SHORT_EXPLANATIONS = new Extractor() {
            @Override public String extract(ApacheMetrics metric) {
                return metric.shortExpl;
            }
        };
    }


}
