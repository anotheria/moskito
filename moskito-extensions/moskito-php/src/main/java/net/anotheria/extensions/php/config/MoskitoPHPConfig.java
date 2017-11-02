package net.anotheria.extensions.php.config;

import org.configureme.annotations.ConfigureMe;

/**
 * Config for Moskito PHP plugin.
 *
 * Defines sets of mappers and connectors that be used by plugin
 */
@ConfigureMe(allfields = true)
public class MoskitoPHPConfig {

    private MapperConfig[] mappers = new MapperConfig[0];
    private ConnectorConfig[] connectors = new ConnectorConfig[0];

    public MapperConfig[] getMappers() {
        return mappers;
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

}
