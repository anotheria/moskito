package net.anotheria.moskito.core.config;

import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.io.Serializable;

/**
 * This configuration provides possibilities to disable parts of MoSKito. Even if the authors of the framework
 * do not understand, why you would disable it, we decided to support it finally.
 * However, keep in mind, this doesn't remove the code alltogether, but reduces the impact.
 * For example AOP Pointcut will still be executed, but it will not measure anything. Since AOP style integration is
 * compile time weaving it can not be removed by a configuration switch.
 *
 * @author lrosenberg
 * @since 2019-03-12 22:23
 */
@ConfigureMe
public class KillSwitchConfiguration implements Serializable {

	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 5671582449272126029L;

	/**
	 * If true no metrics will be collected, if supported by the collector.
	 * Will be supported by AOP, CDI and Proxies in first turn.
	 */
	@Configure
	private boolean disableMetricCollection = false;

	public boolean isDisableMetricCollection() {
		return disableMetricCollection;
	}

	public void setDisableMetricCollection(boolean disableMetricCollection) {
		this.disableMetricCollection = disableMetricCollection;
	}

	public boolean disableMetricCollection(){
		return disableMetricCollection;
	}
}
