package net.anotheria.moskito.core.config.producers;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.configureme.annotations.Configure;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author Michael König
 */
public class MBeanProducerConfig implements Serializable {

    /**
     * Indicates if the producers will be registered automatically. Defaults to FALSE.
     */
    @Configure
    private boolean registerAutomatically = false;

    /**
     * Indicates if the producer values will be updated automatically. Defaults to TRUE.
     */
    @Configure
    private boolean updateAutomatically = true;

    /**
     * Time in milliseconds before start the first producer values update. Defaults to 15 seconds.
     */
    @Configure
    private long delayBeforeFirstUpdate = 15000;

    /**
     * List of all Domains, NULL means that producers will be created for all(!) MBean domains.
     * Defaults to NULL.
     */
    @Configure
    private MBeanProducerDomainConfig[] domains;

    /**
     * @return {@link #delayBeforeFirstUpdate}
     */
    public long getDelayBeforeFirstUpdate() {
        return delayBeforeFirstUpdate;
    }

    /**
     * @return {@link #domains}
     */
    @SuppressFBWarnings("EI_EXPOSE_REP")
	public MBeanProducerDomainConfig[] getDomains() {
        return domains;
    }

    /**
     * @return {@link #registerAutomatically}
     */
    public boolean isRegisterAutomatically() {
        return registerAutomatically;
    }

    /**
     * @return {@link #updateAutomatically}
     */
    public boolean isUpdateAutomatically() {
        return updateAutomatically;
    }

    /**
     * @param milliseconds
     *            Time in milliseconds before start the first producer values update.
     */
    public void setDelayBeforeFirstUpdate(final long milliseconds) {
        this.delayBeforeFirstUpdate = milliseconds;
    }

    /**
     * @param domains
     *            {@link MBeanProducerDomainConfig}
     */
    @SuppressFBWarnings("EI_EXPOSE_REP2")
	public void setDomains(final MBeanProducerDomainConfig[] domains) {
        this.domains = domains;
    }

    /**
     * @param flag
     *            {@link #registerAutomatically}
     */
    public void setRegisterAutomatically(final boolean flag) {
        this.registerAutomatically = flag;
    }

    /**
     * @param flag
     *            {@link #updateAutomatically}
     */
    public void setUpdateAutomatically(final boolean flag) {
        this.updateAutomatically = flag;
    }

    /**
     * @{inheritDoc}
     */
    @Override
    public String toString() {
        return "MBeanProducerConfig{" + "registerAutomatically=" + registerAutomatically + ", updateAutomatically="
                + updateAutomatically + ", delayBeforeFirstUpdate=" + delayBeforeFirstUpdate + ", domains="
                + Arrays.toString(domains) + "}";
    }

}
