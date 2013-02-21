package net.anotheria.moskito.core.config.thresholds;

import net.anotheria.moskito.core.threshold.ThresholdStatus;
import org.configureme.annotations.Configure;

/**
 * This class configures a notification provider that can generate alerts in case of threshold status change.
 *
 * @author lrosenberg
 * @since 22.10.12 16:15
 */
public class NotificationProviderConfig {
	/**
	 * Name of the notification provider class.
	 */
	@Configure
	private String className;
	/**
	 * Parameter for the notification provider instance.
	 */
	@Configure
	private String parameter = "";
	/**
	 * Status to react on.
	 */
	@Configure
	private String guardedStatus = ThresholdStatus.GREEN.name();

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getGuardedStatus() {
		return guardedStatus;
	}

	public void setGuardedStatus(String guardedStatus) {
		this.guardedStatus = guardedStatus;
	}

	@Override public String toString(){
		return "className: "+className+", parameter: "+parameter+", guardedStatus: "+guardedStatus;
	}
}
