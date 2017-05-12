package net.anotheria.moskito.extension.nginx.config;

import com.google.gson.annotations.SerializedName;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import net.anotheria.moskito.extension.nginx.NginxMonitor;
import org.configureme.annotations.AfterReConfiguration;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Configuration bean for NginxMonitorPlugin.
 *
 * @author dzhmud
 */
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"})
@SuppressWarnings(value = "unused")
@ConfigureMe(name = "nginx-monitor")
public class NginxMonitorConfig implements Serializable {

    /**
     * Array of NginxMonitoredInstance configurations.
     */
    @Configure
    @SerializedName("@monitoredInstances")
    private NginxMonitoredInstance[] monitoredInstances;

    public NginxMonitoredInstance[] getMonitoredInstances() {
        return monitoredInstances;
    }

    public void setMonitoredInstances(NginxMonitoredInstance[] instances) {
        this.monitoredInstances = instances;
    }

    /**
     * On runtime reconfiguration, trigger reinitializing of producers/timers/etc.
     */
    @AfterReConfiguration
    public void afterReConfiguration() {
        NginxMonitor.reconfigureNginxMonitor(this);
    }

    @Override
    public String toString() {
        return "NginxMonitorConfig{" +
                "instances=" + Arrays.toString(monitoredInstances) +
                '}';
    }
}
