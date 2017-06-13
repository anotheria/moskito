package net.anotheria.moskito.extensions.monitoring.config;

import com.google.gson.annotations.SerializedName;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import net.anotheria.moskito.extensions.monitoring.GenericMonitor;
import net.anotheria.moskito.extensions.monitoring.PluginTypeRegistry;
import org.configureme.annotations.AfterReConfiguration;
import org.configureme.annotations.BeforeReConfiguration;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.util.Arrays;

/**
 * Configuration bean for GenericMonitoringPlugin.
 *
 * @author dzhmud
 */
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"})
@SuppressWarnings(value = "unused")
@ConfigureMe(name = "monitoring-plugin")
public class MonitoringPluginConfig {

    /**
     * Plugin type, as registered in {@link PluginTypeRegistry}.
     */
    @Configure
    private String pluginType;

    /**
     * Array of metric names to monitor.
     */
    @Configure
    @SerializedName("@metrics")
    private String[] metrics;

    /**
     * Array of MonitoredInstance configurations.
     */
    @Configure
    @SerializedName("@monitoredInstances")
    private MonitoredInstance[] monitoredInstances;

    public String getPluginType() {
        return pluginType;
    }

    public void setPluginType(String pluginType) {
        this.pluginType = pluginType;
    }

    public String[] getMetrics() {
        return metrics;
    }

    public void setMetrics(String[] metrics) {
        this.metrics = metrics;
    }

    public MonitoredInstance[] getMonitoredInstances() {
        return monitoredInstances;
    }

    public void setMonitoredInstances(MonitoredInstance[] instances) {
        this.monitoredInstances = instances;
    }

    /**
     * On runtime reconfiguration, reset fields because they are not updated if removed from config.
     */
    @BeforeReConfiguration
    public void beforeReConfiguration() {
        pluginType = null;
        metrics = null;
        monitoredInstances = null;
    }
    /**
     * On runtime reconfiguration, trigger reinitializing of producers/timers/etc.
     */
    @AfterReConfiguration
    public void afterReConfiguration() {
        GenericMonitor.reconfigureMonitor(this);
    }

    @Override
    public String toString() {
        return "MonitoringPluginConfig{" +
                "pluginType='" + pluginType + '\'' +
                ", metrics=" + Arrays.toString(metrics) +
                ", monitoredInstances=" + Arrays.toString(monitoredInstances) +
                '}';
    }

}
