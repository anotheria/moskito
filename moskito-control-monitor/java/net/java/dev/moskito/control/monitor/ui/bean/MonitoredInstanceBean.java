package net.java.dev.moskito.control.monitor.ui.bean;

/**
 * 
 * Class that contains information about monitored application that will be
 * shown on UI.
 * 
 */
public class MonitoredInstanceBean {

	private String name; //hudson.anotheria.net
	private String group; //anotheria
	private String status; //green
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	
	
}
