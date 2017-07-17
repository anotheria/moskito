package net.anotheria.moskito.extension.apache;

import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.core.util.BuiltinUpdater;
import net.anotheria.moskito.extension.apache.config.ApacheMonitoredInstance;
import net.anotheria.moskito.extension.apache.config.ApacheMonitoringPluginConfig;
import net.anotheria.util.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Apache monitor that handles monitoring of several apache instances, as
 * configured in 'apache-monitoring-plugin.json' file.
 *
 * @author dzhmud
 */
public final class ApacheMonitor {

    /** Use shared plugin logger. */
    private static final Logger LOGGER = ApacheMonitoringPlugin.LOGGER;

    /** List of all created ApacheMonitor objects. Used by reconfiguration method.*/
    private static final List<ApacheMonitor> MONITORS = new ArrayList<>();

    /** ApacheMonitorConfig instance. */
    private final ApacheMonitoringPluginConfig config;

    /** List of created producers. */
    private final List<ApacheStatsProducer> producers = new CopyOnWriteArrayList<>();

    /** TimerTask that updates producers upon running.*/
    private TimerTask updateTask;


    static ApacheMonitor initialize(ApacheMonitoringPluginConfig aConfig) {
        ApacheMonitor apacheMonitor = new ApacheMonitor(aConfig);
        MONITORS.add(apacheMonitor);
        apacheMonitor.setup();
        return apacheMonitor;
    }


    private ApacheMonitor(ApacheMonitoringPluginConfig aConfig) {
        this.config = aConfig;
    }

    private void setup() {
        ApacheMonitoredInstance[] instances = config.getMonitoredInstances();
        if (instances == null || instances.length == 0) {
            LOGGER.info("apache monitor has empty array of monitored instances!");
        } else {
            final Set<String> monitorNames = new HashSet<>();
            for (ApacheMonitoredInstance instance : instances) {
                if (StringUtils.isEmpty(instance.getName())) {
                    LOGGER.warn("Found empty apache name, skipping until changed.");
                    continue;
                }
                //check for duplicate names, we should not register several producers with same producerId.
                if (!monitorNames.add(instance.getName())) {
                    LOGGER.warn("Found duplicate name in configuration: '" + instance.getName() + "', skipping.");
                    continue;
                }
                //create and register ApacheStatsProducer.
                ApacheStatsProducer producer = new ApacheStatsProducer(instance);
                producers.add(producer);
                ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(producer);
            }
            updateTask = new TimerTask() {
                @Override public void run() {
                    for (ApacheStatsProducer producer : producers) {
                        producer.update();
                    }
                }
            };
            BuiltinUpdater.addTask(updateTask);
        }
    }

    /**
     * Cancel updater task and unregister producers.
     * However, call of {@link #setup()} method will recreate everything and
     * turn this ApacheMonitor back to running state.
     */
    private void stop() {
        //unregister and free all resources;
        if (updateTask != null)
            updateTask.cancel();
        for (ApacheStatsProducer producer : producers) {
            producer.apacheStats.destroy();
            ProducerRegistryFactory.getProducerRegistryInstance().unregisterProducer(producer);
        }
        producers.clear();
    }

    /**
     * Completely deinitialize this ApacheMonitor.
     * It won't be usable anymore
     */
    void deInitialize() {
        stop();
        MONITORS.remove(this);
    }

    /** Called when ApacheMonitorConfig source changed at runtime. */
    public static void reconfigureApacheMonitor(ApacheMonitoringPluginConfig config) {
        for (ApacheMonitor monitor : MONITORS) {
            if (monitor.config == config) {
                monitor.stop();
                monitor.setup();
            }
        }
    }

    /** Producer for ApacheStats. */
    private static class ApacheStatsProducer implements IStatsProducer<ApacheStats> {

        /** Configuration of the monitored apache instance. */
        private final ApacheMonitoredInstance apache;

        /** ApacheStats object that collects metrics. */
        private final ApacheStats apacheStats;

        /** Singleton list with ApacheStats object. */
        private final List<ApacheStats> stats;

        /** This producer ID.*/
        private final String producerId;

        ApacheStatsProducer(ApacheMonitoredInstance instance) {
            if (instance == null)
                throw new IllegalArgumentException("ApacheMonitoredInstance argument is null");
            this.apache = instance;
            this.producerId = instance.getName();
            this.apacheStats = new ApacheStats(instance.getName());
            this.stats = Collections.singletonList(apacheStats);
        }

        @Override
        public List<ApacheStats> getStats() {
            return stats;
        }

        @Override
        public String getProducerId() {
            return producerId;
        }

        @Override
        public String getCategory() {
            return "Monitor";
        }

        @Override
        public String getSubsystem() {
            return "proxy";
        }

        private void update() {
            ApacheStatus apacheStatus = fetchApacheStatus();
            if (apacheStatus != null)
                apacheStats.update(apacheStatus);
        }

        private ApacheStatus fetchApacheStatus() {
            ApacheStatus apacheStatus = null;
            final UsernamePasswordCredentials credentials = StringUtils.isEmpty(apache.getUsername()) ? null :
                    new UsernamePasswordCredentials(apache.getUsername(), apache.getPassword());
            try {
                final HttpResponse response = HttpHelper.getHttpResponse(apache.getLocation(), credentials);
                final String content = HttpHelper.getResponseContent(response);
                if (HttpHelper.isScOk(response)) {
                    apacheStatus = ApacheStatusParser.parse(content);
                } else {
                    LOGGER.warn("Response status code is not 200! Apache location = "
                            + apache.getLocation() + ", response=" + content);
                }
            } catch (IOException e) {
                LOGGER.warn("Failed to fetch content of URL ["+apache.getLocation()+"].", e);
            }
            return apacheStatus;
        }

    }

}
