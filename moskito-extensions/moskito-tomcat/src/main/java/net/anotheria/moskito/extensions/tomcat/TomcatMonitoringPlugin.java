package net.anotheria.moskito.extensions.tomcat;

import net.anotheria.moskito.core.plugins.AbstractMoskitoPlugin;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;

/**
 * Tomcat monitoring plugin.
 */
public class TomcatMonitoringPlugin extends AbstractMoskitoPlugin {

    /**
     *  Global Request Processor Producer instance.
     */
    private GlobalRequestProcessorProducer globalRequestProcessorProducer;

    @Override
    public void initialize() {
        globalRequestProcessorProducer = new GlobalRequestProcessorProducer();
        ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(globalRequestProcessorProducer);
    }

    @Override
    public void deInitialize() {
        ProducerRegistryFactory.getProducerRegistryInstance().unregisterProducer(globalRequestProcessorProducer);
    }
}
