package net.anotheria.moskito.extensions.monitoring;

import net.anotheria.moskito.extensions.monitoring.fetcher.StatusFetcherFactory;
import net.anotheria.moskito.extensions.monitoring.fetcher.StatusFetcher;
import net.anotheria.moskito.extensions.monitoring.parser.StatusData;
import net.anotheria.moskito.extensions.monitoring.parser.StatusParser;
import net.anotheria.moskito.extensions.monitoring.stats.StatsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Registry that maps plugin type(apache/nginx/etc) to corresponding factories.
 *
 * @author dzhmud
 */
public final class PluginTypeRegistry {

    private PluginTypeRegistry() {}

    /** Internal mapping container - ConcurrentMap.*/
    private static final ConcurrentMap<String, PluginTypeConfiguration> registry = new ConcurrentHashMap<>();

    /**
     * Register plugin type and associate given factories with it.
     * @param pluginType plugin type: "apache"/"nginx"/etc..
     * @param statsFactory factory that creates corresponding stats.
     * @param statusFetcherFactory factory that creates {@link StatusFetcher} for the given plugin type.
     * @param parser parser instance that can parse status for the given plugin type.
     * @param <T> type of object that StatusFetcher produces and StatusParser parses.
     */
    public static <T> void registerPluginType(String pluginType, StatsFactory statsFactory, StatusFetcherFactory<T> statusFetcherFactory, StatusParser<T,StatusData> parser) {
        PluginTypeConfiguration conf = new PluginTypeConfiguration<>(statsFactory, statusFetcherFactory, parser);
        PluginTypeConfiguration previous = registry.putIfAbsent(pluginType, conf);
        if (previous != null) {
            getLogger().warn("Registry already have mapping for plugin type '"+pluginType+"'!");
        }
    }

    /**
     * Get {@link PluginTypeConfiguration} for the given plugin type.
     * @param pluginType type of the plugin.
     * @return PluginTypeConfiguration containing all needed factories.
     * @throws IllegalArgumentException if plugin of type 'pluginType' was not registered.
     */
    static PluginTypeConfiguration getConfiguration(String pluginType) {
        PluginTypeConfiguration conf = registry.get(pluginType);
        if (conf == null)
            throw new IllegalArgumentException("PluginType '"+pluginType+"' not registered!");
        return conf;
    }

    /**
     * Check if plugin of given type is registered.
     * @param pluginType plugin type to check.
     * @return {@code true} if plugin type with given name is registered, false otherwise.
     */
    public static boolean isRegistered(String pluginType) {
        return registry.containsKey(pluginType);
    }

    private static Logger getLogger() {
        return LoggerFactory.getLogger(PluginTypeRegistry.class);
    }

    /**
     * Composition bean containing all needed factories for single plugin type.
     * @param <T> type of intermediate status container between StatusFetcher and StatusParser.
     */
    public static class PluginTypeConfiguration<T> {
        /** Factory that creates stats.*/
        private final StatsFactory statsFactory;
        /** Factory that creates {@link StatusFetcher}. */
        private final StatusFetcherFactory<T> statusFetcherFactory;
        /** Parser instance.*/
        private final StatusParser<T,StatusData> parser;

        /**
         * Create PluginTypeConfiguration from the given params.
         * @param statsFactory factory that creates stats.
         * @param statusFetcherFactory factory that creates {@link StatusFetcher}.
         * @param parser parser instance.
         */
        public PluginTypeConfiguration(StatsFactory statsFactory, StatusFetcherFactory<T> statusFetcherFactory, StatusParser<T, StatusData> parser) {
            this.statsFactory = statsFactory;
            this.statusFetcherFactory = statusFetcherFactory;
            this.parser = parser;
        }

        /**
         * Get {@link StatsFactory} bound to this configuration.
         * @return StatsFactory instance.
         */
        public StatsFactory getStatsFactory() {
            return statsFactory;
        }

        /**
         * Get {@link StatusFetcherFactory} bound to this configuration.
         * @return StatusFetcherFactory instance.
         */
        public StatusFetcherFactory<T> getStatusFetcherFactory() {
            return statusFetcherFactory;
        }

        /**
         * Get {@link StatusParser} bound to this configuration.
         * @return StatusParser instance.
         */
        public StatusParser<T, StatusData> getParser() {
            return parser;
        }
    }

}
