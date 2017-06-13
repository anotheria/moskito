package net.anotheria.moskito.extensions.monitoring.metrics;

import net.anotheria.moskito.core.stats.StatValueTypes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class containing all metrics we gain from nginx stub status page.
 *
 * @author dzhmud
 */
public final class NginxMetrics extends GenericMetrics {

    /**
     * Get NginxMetrics by their names. Order of NginxMetrics in the resulting list
     * is constant and does not depend on order of metric names.
     * {@link #HANDLED} is added to the result even if its name is not passed.
     * @param metricsNames names of metrics.
     * @return list of metrics found.
     */
    public static List<NginxMetrics> findMetrics(List<String> metricsNames) {
        if (metricsNames == null || metricsNames.isEmpty()) {
            return Collections.singletonList(NginxMetrics.HANDLED);
        }
        final List<NginxMetrics> result = new ArrayList<>(metricsNames.size());
        for (NginxMetrics metric : getMetricsOfType(NginxMetrics.class)) {
            if (metricsNames.contains(metric.getCaption())) {
                result.add(metric);
                continue;
            }
            //we need 'handled' to monitor nginx restarts.
            if (metric == NginxMetrics.HANDLED) {
                result.add(metric);
            }
        }
        return result;
    }

    /**
     * Get all available NginxMetrics.
     * @return list containing all available NginxMetrics.
     */
    public static List<NginxMetrics> getAll() {
        return getMetricsOfType(NginxMetrics.class);
    }


    private NginxMetrics(StatValueTypes type, String valueName, String ... strings ) {
        this(type, false, valueName, strings);
    }

    private NginxMetrics(StatValueTypes type, boolean isRateValue, String valueName, String ... strings) {
        super(type, isRateValue, valueName, strings);
    }

    /** 'Active connections' metric. */
    public static final NginxMetrics
        ACTIVE = new NginxMetrics(StatValueTypes.COUNTER, "Active",
            "Active connections", "Active connections(sessions) at the moment of check."),
        /** 'Accepted connections' metric. */
        ACCEPTED = new NginxMetrics(StatValueTypes.COUNTER, "Accepted",
                "Accepted connections", "Accepted connections since nginx start."),
        /** Average amount of accepted connections per second.*/
        ACCEPTEDPERSECOND = new NginxMetrics(StatValueTypes.DIFFLONG, true, "Accepted/sec",
                "Accepted connections per second."),
        /** 'Handled connections' metric. */
        HANDLED = new NginxMetrics(StatValueTypes.COUNTER, "Handled",
                "Total number of handled connections."),
        /** Average amount of handled connections per second. */
        HANDLEDPERSECOND = new NginxMetrics(StatValueTypes.DIFFLONG, true,
                "Handled/sec", "Handled connections per second."),
        /** 'Dropped connections' metric. Calculated as difference between handled and accepted metrics.*/
        DROPPED = new NginxMetrics(StatValueTypes.COUNTER, "Dropped",
                "Total number of dropped connections."),
        /** Average number of dropped connections per second. */
        DROPPEDPERSECOND = new NginxMetrics(StatValueTypes.DIFFLONG, true, "Dropped/sec",
                "Average number of dropped connections per second."),
        /** Total number of requests metric.*/
        REQUESTS = new NginxMetrics(StatValueTypes.COUNTER, "Requests",
                "Total number of requests served."),
        /** Average number of requests served per second.*/
        REQUESTSPERSECOND = new NginxMetrics(StatValueTypes.DIFFLONG, true, "Requests/sec",
                "Average number of requests served per second"),
        /** The current number of connections where nginx is reading the request header.*/
        READING = new NginxMetrics(StatValueTypes.COUNTER, "Reading",
                "The current number of connections where nginx is reading the request header"),
        /** The current number of connections where nginx is writing the response back to the client. */
        WRITING = new NginxMetrics(StatValueTypes.COUNTER, "Writing",
                "The current number of connections where nginx is writing the response back to the client."),
        /** The current number of idle client connections waiting for a request.*/
        WAITING = new NginxMetrics(StatValueTypes.COUNTER, "Waiting",
                "The current number of idle client connections waiting for a request.");

    @Override
    public String toString() {
        return "NginxMetrics{" +
                "type=" + getType() +
                ", isRateMetric=" + isRateMetric() +
                ", caption='" + getCaption() + '\'' +
                ", shortExpl='" + getShortExplanation() + '\'' +
                ", explanation='" + getExplanation() + '\'' +
                '}';
    }

}
