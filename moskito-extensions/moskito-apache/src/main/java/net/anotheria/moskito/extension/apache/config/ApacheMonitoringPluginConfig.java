package net.anotheria.moskito.extension.apache.config;

import com.google.gson.annotations.SerializedName;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import net.anotheria.moskito.extension.apache.ApacheMonitor;
import org.configureme.annotations.AfterReConfiguration;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Configuration bean for ApacheMonitoringPlugin.
 *
 * @author dzhmud
 */
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"})
@SuppressWarnings(value = "unused")
@ConfigureMe(name = "apache-monitoring-plugin")
public class ApacheMonitoringPluginConfig implements Serializable {

    /**
     * Array of Apache metrics to monitor.
     */
    @Configure
    @SerializedName("@metrics")
    private String[] metrics;

    /**
     * Array of ApacheMonitoredInstance configurations.
     */
    @Configure
    @SerializedName("@monitoredInstances")
    private ApacheMonitoredInstance[] monitoredInstances;

    public String[] getMetrics() {
        return metrics;
    }

    public void setMetrics(String[] metrics) {
        this.metrics = metrics;
    }

    public ApacheMonitoredInstance[] getMonitoredInstances() {
        return monitoredInstances;
    }

    public void setMonitoredInstances(ApacheMonitoredInstance[] instances) {
        this.monitoredInstances = instances;
    }

    /**
     * On runtime reconfiguration, trigger reinitializing of producers/timers/etc.
     */
    @AfterReConfiguration
    public void afterReConfiguration() {
        ApacheMonitor.reconfigureApacheMonitor(this);
    }

    @Override
    public String toString() {
        return "ApacheMonitoringPluginConfig{" +
                (metrics == null ? "" : "metrics=" + Arrays.toString(metrics)) +
                "instances=" + Arrays.toString(monitoredInstances) +
                '}';
    }

}
