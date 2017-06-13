package net.anotheria.moskito.extensions.monitoring;

import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.core.util.BuiltinUpdater;
import net.anotheria.moskito.extensions.monitoring.config.MonitoredInstance;
import net.anotheria.moskito.extensions.monitoring.config.MonitoringPluginConfig;
import net.anotheria.moskito.extensions.monitoring.fetcher.StatusFetcherFactory;
import net.anotheria.moskito.extensions.monitoring.parser.StatusData;
import net.anotheria.moskito.extensions.monitoring.parser.StatusParser;
import net.anotheria.moskito.extensions.monitoring.stats.GenericStats;
import net.anotheria.moskito.extensions.monitoring.stats.StatsFactory;
import net.anotheria.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Generic monitor that handles monitoring of several instances, as
 * configured in 'monitoring-plugin.json' file.
 *
 * @author dzhmud
 */
public final class GenericMonitor {

    /** List of all created GenericMonitor objects. Used by reconfiguration method.*/
    private static final List<GenericMonitor> MONITORS = new ArrayList<>();

    /** Monitors own logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericMonitor.class);

    /** Name of the configuration file used by this plugin instance. */
    private String configurationName;

    /** MonitoringPluginConfig instance. */
    private final MonitoringPluginConfig config;

    /** List of created producers. */
    private final List<GenericStatsProducer> producers = new CopyOnWriteArrayList<>();

    /** TimerTask that updates producers upon running.*/
    private TimerTask updateTask;


    static GenericMonitor initialize(String configurationName, MonitoringPluginConfig aConfig) {
        GenericMonitor genericMonitor = new GenericMonitor(configurationName, aConfig);
        MONITORS.add(genericMonitor);
        genericMonitor.setup();
        return genericMonitor;
    }

    /** New monitor instance should be created through static #initialize method. */
    private GenericMonitor(String configurationName, MonitoringPluginConfig aConfig) {
        this.config = aConfig;
        this.configurationName = configurationName;
    }

    private boolean checkPluginType() {
        String pluginType = config.getPluginType();
        if (StringUtils.isEmpty(pluginType)) {
            LOGGER.warn("Plugin type property is empty in '"+configurationName+"' config!");
        } else if (!PluginTypeRegistry.isRegistered(pluginType)) {
            LOGGER.warn("PluginTypeRegistry '"+pluginType+"' not registered!");
        } else {
            return true;
        }
        return false;
    }

    private boolean checkInstances() {
        MonitoredInstance[] instances = config.getMonitoredInstances();
        if (instances == null || instances.length == 0) {
            LOGGER.info(configurationName + " config has empty array of monitored instances!");
            return false;
        }
        return true;
    }

    private void setup() {
        if (checkPluginType() && checkInstances()) {
            final Set<String> monitorNames = new HashSet<>();
            for (MonitoredInstance instance : config.getMonitoredInstances()) {
                if (StringUtils.isEmpty(instance.getName())) {
                    LOGGER.warn("Found empty instance name in configuration: '" + configurationName + "', skipping until changed.");
                    continue;
                }
                //check for duplicate names, we should not register several producers with same producerId.
                if (!monitorNames.add(instance.getName())) {
                    LOGGER.warn("Found duplicate name '"+instance.getName()+"' in configuration: '" + configurationName + "', skipping this instance.");
                    continue;
                }
                //create and register GenericStatsProducer.
                GenericStatsProducer producer = new GenericStatsProducer(config, instance);
                producers.add(producer);
                ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(producer);
            }
            updateTask = new TimerTask() {
                @Override public void run() {
                    for (GenericStatsProducer producer : producers) {
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
     * turn this GenericMonitor back to running state.
     */
    private void stop() {
        //unregister and free all resources;
        if (updateTask != null)
            updateTask.cancel();
        for (GenericStatsProducer producer : producers) {
            producer.genericStats.destroy();
            ProducerRegistryFactory.getProducerRegistryInstance().unregisterProducer(producer);
        }
        producers.clear();
    }

    /**
     * Completely deinitialize this GenericMonitor.
     * It won't be usable anymore
     */
    void deInitialize() {
        stop();
        MONITORS.remove(this);
    }

    /** Called when MonitoringPluginConfig source changed at runtime. */
    public static void reconfigureMonitor(MonitoringPluginConfig config) {
        for (GenericMonitor monitor : MONITORS) {
            if (monitor.config == config) {
                monitor.stop();
                monitor.setup();
            }
        }
    }

    /**
     * Producer for GenericStats and its children.
     */
    private static class GenericStatsProducer implements IStatsProducer<GenericStats> {

        /** Configuration of the monitored instance. */
        private final MonitoredInstance monitoredInstance;

        /** GenericStats object that collects metrics. */
        private final GenericStats genericStats;

        /** Singleton list with GenericStats object. */
        private final List<GenericStats> stats;

        /** This producer ID.*/
        private final String producerId;

        /** Factory that creates status fetcher.*/
        private final StatusFetcherFactory statusFetcherFactory;

        /** Parser that will parse retrieved status.*/
        private final StatusParser<Object,StatusData> parser;

        GenericStatsProducer(MonitoringPluginConfig config, MonitoredInstance instance) {
            if (instance == null)
                throw new IllegalArgumentException("MonitoredInstance argument is null");
            this.monitoredInstance = instance;
            this.producerId = instance.getName();

            List<String> monitoredMetrics = null;
            if (config.getMetrics() != null && config.getMetrics().length > 0) {
                monitoredMetrics = Arrays.asList(config.getMetrics());
            }
            PluginTypeRegistry.PluginTypeConfiguration conf = PluginTypeRegistry.getConfiguration(config.getPluginType());
            this.statusFetcherFactory = conf.getStatusFetcherFactory();
            this.parser = conf.getParser();

            final StatsFactory statsFactory = conf.getStatsFactory();
            this.genericStats = monitoredMetrics == null ?
                    statsFactory.createStats(instance.getName()) :
                    statsFactory.createStats(instance.getName(), monitoredMetrics);
            this.stats = Collections.singletonList(genericStats);
        }

        @Override
        public List<GenericStats> getStats() {
            return stats;
        }

        @Override
        public String getProducerId() {
            return producerId;
        }

        @Override
        public String getCategory() {
            return "web";
        }

        @Override
        public String getSubsystem() {
            return "plugins";
        }

        /** Fetch new status data and update stats.*/
        private void update() {
            Object status = statusFetcherFactory.getStatusFetcher().fetchStatus(monitoredInstance);
            if (status != null) {
                StatusData newStatusData = parser.parse(status);
                if (newStatusData != null) {
                    genericStats.update(newStatusData);
                }
            }
        }

    }

}
