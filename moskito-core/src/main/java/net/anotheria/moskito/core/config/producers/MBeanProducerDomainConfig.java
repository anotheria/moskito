package net.anotheria.moskito.core.config.producers;

import org.configureme.annotations.Configure;

/**
 * @author Michael König
 */
public class MBeanProducerDomainConfig {

    /**
     * the name of MBean domain to configure.
     */
    @Configure
    private String name;

    /**
     * A list of classes which will be registered as producer. NULL means all(!) MBeans in that
     * domain will be registered. Defaults to NULL.
     */
    @Configure
    private String[] classes;

    /**
     * @return {@link #classes}
     */
    public String[] getClasses() {
        return classes;
    }

    /**
     * @return {@link #name}
     */
    public String getName() {
        return name;
    }

    /**
     * @param classes
     */
    public void setClasses(final String[] classes) {
        this.classes = classes;
    }

    /**
     * @param domain
     *            the domain name to include
     */
    public void setName(final String domain) {
        this.name = domain;
    }

}
