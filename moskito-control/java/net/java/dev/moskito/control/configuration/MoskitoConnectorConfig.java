package net.java.dev.moskito.control.configuration;

public class MoskitoConnectorConfig {

	//"name" : "Demo1",
	//"type" : "app",
	//"group": "testing",
	//"data" : "all"
	//"url"  : "http://localhost:8080/moskitodemo/moskitocontrol"
		
	private String monitoredInstanceName; 
	private MonitoredInstanceType type; 
	private String group;
	private MoskitoConnectorType[] data;
	private String monitoredInstanceUrl;
	
	public String getMonitoredInstanceName() {
		return monitoredInstanceName;
	}
	public void setMonitoredInstanceName(String monitoredInstanceName) {
		this.monitoredInstanceName = monitoredInstanceName;
	}
	public MonitoredInstanceType getType() {
		return type;
	}
	public void setType(MonitoredInstanceType type) {
		this.type = type;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public MoskitoConnectorType[] getData() {
		return data;
	}
	public void setData(MoskitoConnectorType[] data) {
		this.data = data;
	}
	public String getMonitoredInstanceUrl() {
		return monitoredInstanceUrl;
	}
	public void setMonitoredInstanceUrl(String monitoredInstanceUrl) {
		this.monitoredInstanceUrl = monitoredInstanceUrl;
	}
}
