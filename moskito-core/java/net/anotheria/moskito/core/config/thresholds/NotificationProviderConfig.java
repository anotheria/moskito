package net.anotheria.moskito.core.config.thresholds;

import net.anotheria.moskito.core.treshold.ThresholdStatus;
import org.configureme.annotations.Configure;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 22.10.12 16:15
 */
public class NotificationProviderConfig {
	@Configure
	private String className;
	@Configure
	private String parameter = "";
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
