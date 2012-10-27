package net.anotheria.moskito.core.config.thresholds;

import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

/**
 * Guard for a single threshold status in the threshold.
 *
 * @author lrosenberg
 * @since 25.10.12 10:29
 */
@ConfigureMe(allfields=true)
public class GuardConfig {
	/**
	 * Value that have to be reached for the status change.
	 */
	@Configure
	private String value;
	/**
	 * Direction in which the value has to be passed (UP/DOWN).
	 */
	@Configure
	private String direction;
	/**
	 * The resulting status.
	 */
	@Configure
	private String status;

	/**
	 * Creates a new guard config object.
	 */
	public GuardConfig(){

	}

	/**
	 * Creates a new guard config object and set all fields from constructor.
	 * @param aStatus resulting status.
	 * @param aDirection direction for the value.
	 * @param aValue the value.
	 */
	public GuardConfig(String aStatus, String aDirection, String aValue){
		status = aStatus;
		value  = aValue;
		direction = aDirection;
	}

	public String getDirection() {
		return direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus (String status) {
		this.status = status;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override public String toString(){
		return status +" if "+direction+" "+value;
	}
}
