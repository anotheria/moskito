package net.anotheria.moskito.core.config.producers;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.apache.commons.lang.ArrayUtils;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.io.Serializable;

/**
 * @author Michael König
 */
@ConfigureMe
public class MBeanProducerDomainConfig implements Serializable{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 3040141732977192109L;

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
    @SuppressFBWarnings("EI_EXPOSE_REP")
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
    @SuppressFBWarnings("EI_EXPOSE_REP2")
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
