package net.anotheria.moskito.extension.nginx;

import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.stats.DefaultIntervals;
import net.anotheria.moskito.core.stats.IValueHolderFactory;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.StatValueTypes;
import net.anotheria.moskito.core.stats.TypeAwareStatValue;
import net.anotheria.moskito.core.stats.impl.IntervalRegistry;
import net.anotheria.moskito.core.stats.impl.SkipFirstDiffLongValueHolderFactory;
import net.anotheria.moskito.core.stats.impl.StatValueFactory;
import net.anotheria.moskito.core.stats.impl.TypeAwareStatValueImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Enum containing all metrics we gain from nginx stub status page.
 */
public enum NginxMetrics {
    /** 'Active connections' metric. */
    ACTIVE(StatValueTypes.COUNTER, "Active", "Active connections", "Active connections(sessions) at the moment of check.") {
        long getValue(NginxStatus status){
            return status.getActive();
        }
    },
    /** 'Accepted connections' metric. */
    ACCEPTED(StatValueTypes.COUNTER, "Accepted", "Accepted connections", "Accepted connections since nginx start.") {
        long getValue(NginxStatus status){
            return status.getAccepted();
        }
    },
    /** Average amount of accepted connections per second.*/
    ACCEPTEDPERSECOND(StatValueTypes.DIFFLONG, true, "Accepted/sec", "Accepted connections per second.") {
        long getValue(NginxStatus status) {
            return status.getAccepted();
        }
    },
    /** 'Handled connections' metric. */
    HANDLED(StatValueTypes.COUNTER, "Handled", "Total number of handled connections.") {
        long getValue(NginxStatus status){
            return status.getHandled();
        }
    },
    /** Average amount of handled connections per second. */
    HANDLEDPERSECOND(StatValueTypes.DIFFLONG, true, "Handled/sec", "Handled connections per second.") {
        long getValue(NginxStatus status){
            return status.getHandled();
        }
    },
    /** 'Dropped connections' metric. Calculated as difference between handled and accepted metrics.*/
    DROPPED(StatValueTypes.COUNTER, "Dropped", "Total number of dropped connections.") {
        long getValue(NginxStatus status){
            return status.getHandled() - status.getAccepted();
        }
    },
    /** Average number of dropped connections per second. */
    DROPPEDPERSECOND(StatValueTypes.DIFFLONG, true, "Dropped/sec", "Average number of dropped connections per second.") {
        long getValue(NginxStatus status){
            return status.getHandled() - status.getAccepted();
        }
    },
    /** Total number of requests metric.*/
    REQUESTS(StatValueTypes.COUNTER, "Requests", "Total number of requests served.") {
        long getValue(NginxStatus status){
            return status.getRequests();
        }
    },
    /** Average number of requests served per second.*/
    REQUESTSPERSECOND(StatValueTypes.DIFFLONG, true, "Requests/sec", "Average number of requests served per second") {
        long getValue(NginxStatus status){
            return status.getRequests();
        }
    },
    /** The current number of connections where nginx is reading the request header.*/
    READING(StatValueTypes.COUNTER, "Reading", "The current number of connections where nginx is reading the request header") {
        long getValue(NginxStatus status){
            return status.getReading();
        }
    },
    /** The current number of connections where nginx is writing the response back to the client. */
    WRITING(StatValueTypes.COUNTER, "Writing", "The current number of connections where nginx is writing the response back to the client.") {
        long getValue(NginxStatus status){
            return status.getWriting();
        }
    },
    /** The current number of idle client connections waiting for a request.*/
    WAITING(StatValueTypes.COUNTER, "Waiting", "The current number of idle client connections waiting for a request.") {
        long getValue(NginxStatus status){
            return status.getWaiting();
        }
    };

    /** ValueName variable. Also used as caption by decorator. */
    public final String valueName;
    /** Short explanation and full explanation variables.*/
    private final String shortExpl, explanation;
    /** ValueType of current metric. */
    private final StatValueTypes type;
    /** If true, value per second will be calculated. */
    private final boolean isRateValue;

    NginxMetrics(StatValueTypes type, String valueName, String ... strings ) {
        this(type, false, valueName, strings);
    }

    NginxMetrics(StatValueTypes type, boolean isRateValue, String valueName, String ... strings) {
        this.type = type;
        this.isRateValue = isRateValue;
        this.valueName = valueName;
        this.shortExpl = (strings == null || strings.length == 0) ? valueName : strings[0];
        this.explanation = (strings == null || strings.length < 2) ? shortExpl : strings[1];
    }

    StatValue createStatValue() {
        if (type != StatValueTypes.DIFFLONG) {
            return StatValueFactory.createStatValue(type, valueName, Constants.getDefaultIntervals());
        } else {
            //for DIFFLONG type use upgraded diffLong value holders that ignore first interval.
            final IValueHolderFactory factory = SkipFirstDiffLongValueHolderFactory.INSTANCE;
            final TypeAwareStatValue statValue = new TypeAwareStatValueImpl(valueName, type, factory);
            for (Interval interval : Constants.getDefaultIntervals()) {
                statValue.addInterval(interval);
            }
            return statValue;
        }
    }

    abstract long getValue(NginxStatus status);

    public boolean isDoubleValue() {
        return isRateValue || type == StatValueTypes.DOUBLE;
    }

    public StatValueTypes getType() {
        return type;
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
     * Get NginxMetrics enum value by its name.
     * @return NginxMetrics enum value if found.
     * @throws IllegalArgumentException if there is no NginxMetrics value with provided name.
     */
    static NginxMetrics getByValueName(String valueName) {
        for (NginxMetrics metric : values()) {
            if (metric.valueName.equals(valueName))
                return metric;
        }
        throw new IllegalArgumentException("NginxMetrics.getByValueName() should not be called with unknown value.");
    }

    /**
     * Extract specified String values from all available NginxMetrics.
     * @param type type of strings to extract.
     * @return array of corresponding strings.
     */
    public static String[] getStrings(NginxMetrics.Strings type) {
        List<String> strings = new ArrayList<>(values().length);
        for (NginxMetrics metric : values()) {
            switch(type) {
                case VALUENAME: strings.add(metric.valueName); break;
                case EXPLANATION: strings.add(metric.explanation); break;
                case SHORTEXPLANATION: strings.add(metric.shortExpl); break;
                default: strings.add(metric.valueName);
            }
        }
        return strings.toArray(new String[strings.size()]);
    }

    /**
     * @see #getStrings
     */
    public enum Strings {
        /** Enum value for extracting value names from NginxMetrics.*/
        VALUENAME,
        /** Enum value for extracting short explanations from NginxMetrics.*/
        SHORTEXPLANATION,
        /** Enum value for extracting full explanations from NginxMetrics.*/
        EXPLANATION
    }

}
