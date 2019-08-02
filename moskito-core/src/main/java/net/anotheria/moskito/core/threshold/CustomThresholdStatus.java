package net.anotheria.moskito.core.threshold;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 2019-08-02 14:44
 */
public class CustomThresholdStatus {
	private ThresholdStatus status;
	private String currentValue;

	public CustomThresholdStatus(){
		
	}

	public CustomThresholdStatus(ThresholdStatus aStatus, String aCurrentValue){
		status = aStatus;
		currentValue = aCurrentValue;
	}

	public ThresholdStatus getStatus() {
		return status;
	}

	public void setStatus(ThresholdStatus status) {
		this.status = status;
	}

	public String getCurrentValue() {
		return currentValue;
	}

	public void setCurrentValue(String currentValue) {
		this.currentValue = currentValue;
	}

	@Override
	public String toString() {
		return "CustomThresholdStatus{" +
				"status=" + status +
				", currentValue='" + currentValue + '\'' +
				'}';
	}
}
