package net.anotheria.moskito.extension.mongodb;

import net.anotheria.moskito.core.plugins.AbstractMoskitoPlugin;

/**
 * MongoDb monitoring plugin initializer
 */
public class MongodbMonitoringPlugin extends AbstractMoskitoPlugin {

    @Override
    public void initialize() {
        MongodbMonitor.createMongodbMonitor();
    }

    @Override
    public void deInitialize() {
        MongodbMonitor.destroyMongodbMonitor();
    }
}
