package net.java.dev.moskito.webui.bean;

public class AccumulatedValueBean {
	private String value;
	private String timestamp;
	
	public AccumulatedValueBean(String aValue, String aTimestamp){
		value = aValue;
		timestamp = aTimestamp;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
}
