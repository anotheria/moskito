package net.java.dev.moskito.control.check;

import net.java.dev.moskito.core.treshold.ThresholdStatus;

public class ThresholdCheck extends MoskitoControlCheck {

	private String monitoredInstanceName;
	private ThresholdStatus status;
	private long checkedTime;

	public ThresholdStatus getStatus() {
		return status;
	}

	public void setStatus(ThresholdStatus status) {
		this.status = status;
	}

	public long getCheckedTime() {
		return checkedTime;
	}

	public void setCheckedTime(long checkedTime) {
		this.checkedTime = checkedTime;
	}

	public String getMonitoredInstanceName() {
		return monitoredInstanceName;
	}

	public void setMonitoredInstanceName(String monitoredInstanceName) {
		this.monitoredInstanceName = monitoredInstanceName;
	}
	
	public static ThresholdCheck getDummyCheck() {
		ThresholdCheck dummy = new ThresholdCheck();
		
		return dummy; 
	}
}
