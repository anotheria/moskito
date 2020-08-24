package net.anotheria.extensions.php.config;

import org.apache.commons.lang3.ArrayUtils;
import org.configureme.annotations.AfterReConfiguration;
import org.configureme.annotations.ConfigureMe;
import org.configureme.annotations.DontConfigure;

/**
 * Config for Moskito PHP plugin.
 *
 * Defines sets of mappers and connectors that be used by plugin
 */
@ConfigureMe(allfields = true)
public class MoskitoPHPConfig {

    @DontConfigure
    private ConfigChangedNotifier configChangedNotifier;

    private MapperConfig[] mappers = new MapperConfig[0];
    private ConnectorConfig[] connectors = new ConnectorConfig[0];

    public MapperConfig[] getMappers() {
        return (MapperConfig[]) ArrayUtils.addAll(getDefault(), mappers);
    }

    public void setMappers(MapperConfig[] mappers) {
        this.mappers = mappers;
    }

    public ConnectorConfig[] getConnectors() {
        return connectors;
    }

    public void setConnectors(ConnectorConfig[] connectors) {
        this.connectors = connectors;
    }

    /**
     * Defines builtin default mappers that required for agent proper work
     * @return array of builtin mappers configurations
     */
    public MapperConfig[] getDefault() {

        MapperConfig executionStatsMapper = new MapperConfig();
        executionStatsMapper.setMapperClass("net.anotheria.extensions.php.mappers.impl.ExecutionStatsMapper");
        executionStatsMapper.setMapperId("ExecutionStatsMapper");

        MapperConfig serviceStatsMapper = new MapperConfig();
        serviceStatsMapper.setMapperClass("net.anotheria.extensions.php.mappers.impl.ServiceStatsMapper");
        serviceStatsMapper.setMapperId("ServiceStatsMapper");

        MapperConfig counterStatsMapper = new MapperConfig();
        counterStatsMapper.setMapperClass("net.anotheria.extensions.php.mappers.impl.CounterStatsMapper");
        counterStatsMapper.setMapperId("CounterStatsMapper");

        return new MapperConfig[]{
                executionStatsMapper, serviceStatsMapper, counterStatsMapper
        };

    }

    @AfterReConfiguration
    public void afterReconfiguration() {

        if(this.configChangedNotifier != null)
            this.configChangedNotifier.notifyConfigChanged();

    }

    public void setConfigChangedNotifier(ConfigChangedNotifier configChangedNotifier) {
        this.configChangedNotifier = configChangedNotifier;
    }

}
