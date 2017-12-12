package net.anotheria.moskito.core.util;

import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.producers.MBeanProducerConfig;
import net.anotheria.moskito.core.decorators.DecoratorRegistryFactory;
import net.anotheria.moskito.core.decorators.mbean.GeneralMBeanDecorator;
import net.anotheria.moskito.core.predefined.MBeanStats;
import net.anotheria.moskito.core.registry.IProducerRegistry;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.*;
import java.util.Collections;

/**
 * Factory for building MBean producers
 */
public class MBeanProducerFactory {

    private static final Logger log = LoggerFactory.getLogger(MBeanProducerFactory.class);

    /**
     * Reference to moskito producers registry to register producers
     */
    private static final IProducerRegistry producerRegistry
            = ProducerRegistryFactory.getProducerRegistryInstance();
    /**
     * Reference to MBean producers configuration
     */
    private static final MBeanProducerConfig conf
            = MoskitoConfigurationHolder.getConfiguration().getMbeanProducersConfig();

    /**
     * Checks, is mbean required to be registered as producer
     * @param mBean mbean to check requirements
     * @return true  - mbean need to be registered
     *         false - no
     */
    private static boolean isMBeanRequired(final ObjectInstance mBean) {

        final String domain = mBean.getObjectName().getDomain();
        final String className = mBean.getClassName();

        return !domain.startsWith("moskito.") // we have to skip all the moskito internal MBeans
                && conf.isMBeanRequired(domain, className);

    }

    /**
     * Helper to ensure that given string can be handled in web-view (replaces some invalid
     * characters).
     *
     * @param s the input string to normalize
     * @return the normalized string
     */
    public static String normalize(final String s) {
        if (s == null || s.trim().isEmpty()) {
            return "unspecific";
        }
        return s.replace(':', '/')
                .replace('=', '-')
                .replace(',', '|')
                .replace("\"", "")
                .replace('#', '-');
    }

    /**
     * Builds all mbean producer that required by mbean producers
     * configuration.
     */
    public static void buildProducers() {

        // Registering decorator for mbean stats
        DecoratorRegistryFactory.getDecoratorRegistry().addDecorator(
                MBeanStats.class,
                new GeneralMBeanDecorator()
        );

        for (MBeanServer server : MBeanServerFactory.findMBeanServer(null))
            for (final ObjectInstance mBean : server.queryMBeans(null, null))
                if(isMBeanRequired(mBean)) {

                    MBeanProducer producer = buildProducer(server, mBean);

                    if(producer != null && conf.isRegisterAutomatically())
                        producerRegistry.registerProducer(producer);

                    log.debug(
                            "Registered new producer for " +
                            mBean.getObjectName().getCanonicalName() + "mbean"
                    );
                }

    }

    /**
     * Builds producer from given mbean.
     * Producer be registered only if mbean contains at least one
     * readable by moskito attribute. Only primitive types of
     * attributes can be read: int, long, double, boolean and String.
     * Also registers this producer in producers registry if `isRegisterAutomatically`
     * property of mbean producers configuration set to true.
     *
     * @param server mbean server where mbean is stored
     * @param mBean mbean to register identifier
     */
    private static MBeanProducer buildProducer(MBeanServer server, ObjectInstance mBean) {

        final ObjectName mBeanName = mBean.getObjectName();
        final String canonicalName = mBeanName.getCanonicalName();

        final String producerId = normalize(canonicalName);
        final String subsystem = normalize(mBeanName.getDomain());

        try {

            MBeanStats stats = MBeanStatsFactory.createMBeanStats(
                    server,
                    mBeanName,
                    conf.isUpdateAutomatically(),
                    conf.getDelayBeforeFirstUpdate()
            );

            if(stats != null)
                return new MBeanProducer(producerId, "mbean", subsystem, Collections.singletonList(stats));
            else {

                log.info("Failed to create stats object from mbean named " + mBean +
                        " because no one attribute can not be parsed from that been");
                return null;

            }

        } catch (JMException e) {
            log.warn("Failed to create stats object from mbean named " + mBean, e);
            return null;
        }

    }

}
