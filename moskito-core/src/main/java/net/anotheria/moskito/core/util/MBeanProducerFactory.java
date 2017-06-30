package net.anotheria.moskito.core.util;

import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.producers.MBeanProducerConfig;
import net.anotheria.moskito.core.config.producers.MBeanProducerDomainConfig;
import net.anotheria.moskito.core.predefined.MBeanStatsList;
import net.anotheria.moskito.core.producers.GenericStats;
import net.anotheria.moskito.core.registry.IProducerRegistry;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * A factory which creates one {@link SimpleStatsProducer} per plattform MBean.
 * 
 * @author Michael König
 */
public final class MBeanProducerFactory {

    /**
     * Logger.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(MBeanProducerFactory.class);

	/**
	 * Build a number of {@link SimpleStatsProducer}s, one for each single MBean
	 * which is currently registerd in actual JVM.
	 * 
	 * @return an iterable of {@link SimpleStatsProducer}s matching the current
	 *         moskito-configuration.
	 */
    public static Iterable<SimpleStatsProducer<GenericStats>> buildProducers() {

        final Collection<SimpleStatsProducer<GenericStats>> result = new ArrayList<>();

        for (final MBeanServer server : getServers()) {
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("handle MBean-Server: " + server.toString());
            }

            handleServer(result, server);
        }

        return result;
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
        return s.replace(':', '/').replace('=', '-').replace(',', '|').replace("\"", "").replace('#', '-');
    }

	/**
	 * @param mBean
	 *            {@link ObjectInstance}
	 * @param domainConfig
	 *            {@link MBeanProducerDomainConfig}
	 * @return TRUE if MBean is of a type that we want to inspect as producer.
	 */
    private static boolean checkClasses(final ObjectInstance mBean, final MBeanProducerDomainConfig domainConfig) {
        final String[] classes = domainConfig.getClasses();
        if (classes == null || classes.length == 0) {
            return true;
        }

        final String mBeanClass = mBean.getClassName();

        for (final String className : classes) {
            if (mBeanClass.equals(className)) {
                return true;
            }
        }

        return false;
    }

	/**
	 * @param mBean
	 *            {@link ObjectInstance}
	 * @return TRUE if MBean is in correct domain to inspect as producer.
	 */
    private static boolean checkDomain(final ObjectInstance mBean) {
        final ObjectName mBeanName = mBean.getObjectName();
        final String domain = mBeanName.getDomain();
        if (domain.startsWith("moskito.")) {
            // we have to skip all the moskito internal MBeans
            return false;
        }

        final MBeanProducerConfig conf = MoskitoConfigurationHolder.getConfiguration().getMbeanProducersConfig();
        final MBeanProducerDomainConfig[] domainConfigs = conf.getDomains();
        if (domainConfigs == null || domainConfigs.length == 0) {
            return true;
        }

        for (final MBeanProducerDomainConfig domainConfig : domainConfigs) {
            if (domainConfig.getName().equalsIgnoreCase(domain)) {
                return checkClasses(mBean, domainConfig);
            }
        }

        return false;
    }

    /**
     * @param server
     *            {@link MBeanServer}
     * @return all MBeans within given {@link MBeanServer}
     */
    private static Iterable<ObjectInstance> getObjectInstances(final MBeanServer server) {
        return server.queryMBeans(null, null);
    }

    /**
     * @return all registered {@link MBeanServer}s in this JVM are returned
     */
    private static Iterable<MBeanServer> getServers() {
        ManagementFactory.getPlatformMBeanServer();
        return MBeanServerFactory.findMBeanServer(null);
    }

    /**
     * @param result
     *            collection of {@link SimpleStatsProducer}s
     * @param server
     *            {@link MBeanServer}
     */
    private static void handleServer(final Collection<SimpleStatsProducer<GenericStats>> result,
            final MBeanServer server) {
        final IProducerRegistry producerRegistry = ProducerRegistryFactory.getProducerRegistryInstance();
        final MBeanProducerConfig conf = MoskitoConfigurationHolder.getConfiguration().getMbeanProducersConfig();

        for (final ObjectInstance mbean : getObjectInstances(server)) {
            if (!checkDomain(mbean)) {
                continue;
            }

            final ObjectName mBeanName = mbean.getObjectName();
            final String canonicalName = mBeanName.getCanonicalName();
            if (LOGGER.isDebugEnabled()) {
                LOGGER.debug("handle MBean: " + canonicalName);
            }

            final String producerId = normalize(canonicalName);
            final String subsystem = normalize(mBeanName.getDomain());

            final List<GenericStats> statsList = new MBeanStatsList(server, mBeanName, conf.isUpdateAutomatically(),
                    conf.getDelayBeforeFirstUpdate());
            final SimpleStatsProducer<GenericStats> p = new SimpleStatsProducer<GenericStats>(producerId, "MBean",
                    subsystem, statsList);
            result.add(p);

            if (conf.isRegisterAutomatically()) {
                producerRegistry.registerProducer(p);
            }
        }
    }

    /**
     * Hidden constructor - this is a utility class.
     */
    private MBeanProducerFactory() {
        super();
    }
}
