package net.java.dev.moskito.webui.bean;

public class ThresholdAlertBean {
	private int id;
	private String name;
	private String timestamp;
	private String oldStatus;
	private String oldColorCode;
	private String oldValue;

	private String newStatus;
	private String newColorCode;
	private String newValue;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getOldStatus() {
		return oldStatus;
	}
	public void setOldStatus(String oldStatus) {
		this.oldStatus = oldStatus;
	}
	public String getOldColorCode() {
		return oldColorCode;
	}
	public void setOldColorCode(String oldColorCode) {
		this.oldColorCode = oldColorCode;
	}
	public String getOldValue() {
		return oldValue;
	}
	public void setOldValue(String oldValue) {
		this.oldValue = oldValue;
	}
	public String getNewStatus() {
		return newStatus;
	}
	public void setNewStatus(String newStatus) {
		this.newStatus = newStatus;
	}
	public String getNewColorCode() {
		return newColorCode;
	}
	public void setNewColorCode(String newColorCode) {
		this.newColorCode = newColorCode;
	}
	public String getNewValue() {
		return newValue;
	}
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}
	
}
