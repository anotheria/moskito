package net.anotheria.moskito.core.util;

import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.producers.MBeanProducerConfig;
import net.anotheria.moskito.core.decorators.DecoratorRegistryFactory;
import net.anotheria.moskito.core.decorators.mbean.ListMBeanDecorator;
import net.anotheria.moskito.core.decorators.mbean.MBeanDecorator;
import net.anotheria.moskito.core.predefined.MBeanStats;
import net.anotheria.moskito.core.registry.IProducerRegistry;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;

import javax.management.*;
import java.lang.management.ManagementFactory;
import java.util.Collection;
import java.util.Collections;

public class NewMBeanProducerFactory {

    private static final IProducerRegistry producerRegistry
            = ProducerRegistryFactory.getProducerRegistryInstance();
    private static final MBeanProducerConfig conf
            = MoskitoConfigurationHolder.getConfiguration().getMbeanProducersConfig();

    /* UTIL METHODS. TODO : MAY SIMPLIFY THIS */

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
     * @param s
     *            the input string to normalize
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

    /* UTIL METHODS END */

    public static void buildProducers() {

        DecoratorRegistryFactory.getDecoratorRegistry().addDecorator(
                MBeanStats.class,
                new ListMBeanDecorator()
        );

        // TODO : FIND OUT WHY THIS METHOD IS CALLED
        ManagementFactory.getPlatformMBeanServer();
        for (MBeanServer server : MBeanServerFactory.findMBeanServer(null))
            for (final ObjectInstance mBean : server.queryMBeans(null, null))
                buildProducer(server, mBean);

    }



    private static void buildProducer(MBeanServer server, ObjectInstance mBean) {

        if(!isMBeanRequired(mBean))
            return;

        final ObjectName mBeanName = mBean.getObjectName();
        final String canonicalName = mBeanName.getCanonicalName();

        final String producerId = normalize(canonicalName);
        final String subsystem = normalize(mBeanName.getDomain());

        MBeanStats stats = null;

        try {
            stats = MBeanStats.createMBeanStats(
                    server,
                    mBeanName,
                    conf.isUpdateAutomatically(),
                    conf.getDelayBeforeFirstUpdate()
            );
        } catch (IntrospectionException | InstanceNotFoundException | ReflectionException e) {
            e.printStackTrace(); // TODO : log exception
        }

        if(stats != null) {

            producerRegistry.registerProducer(
                    new MBeanProducer(producerId, "mbean", subsystem, Collections.singletonList(stats))
            );

        }

    }

}
