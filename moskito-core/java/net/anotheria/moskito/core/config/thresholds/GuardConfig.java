package net.anotheria.moskito.core.config.thresholds;

import org.configureme.annotations.ConfigureMe;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 25.10.12 10:29
 */
@ConfigureMe(allfields=true)
public class GuardConfig {
	private String value;
	private String direction;
	private String status;

	public GuardConfig(){

	}

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
