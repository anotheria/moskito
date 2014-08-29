package net.anotheria.moskito.core.config.thresholds;

import net.anotheria.moskito.core.threshold.ThresholdStatus;
import org.configureme.annotations.Configure;
import org.configureme.annotations.SetAll;

import java.util.HashMap;
import java.util.Map;

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
	 * Status to react on.
	 */
	@Configure
	private String guardedStatus = ThresholdStatus.GREEN.name();

	private Map<String,String> properties = new HashMap<String, String>();

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getGuardedStatus() {
		return guardedStatus;
	}

	public void setGuardedStatus(String guardedStatus) {
		this.guardedStatus = guardedStatus;
	}

	@Override public String toString(){
		return "className: "+className+", guardedStatus: "+guardedStatus+" properties: "+properties;
	}

	@SetAll public void setProperty(String name, String value){
		properties.put(name, value);
	}

	public Map<String,String> getProperties(){
		return properties;
	}
}

