package net.java.dev.moskito.webui.bean;

public class MonitoringSessionListItemBean {
	private String name;
	private String created;
	private String lastActivity;
	private boolean active;
	private int numberOfCalls;
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getCreated() {
		return created;
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getLastActivity() {
		return lastActivity;
	}
	public void setLastActivity(String lastActivity) {
		this.lastActivity = lastActivity;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getNumberOfCalls() {
		return numberOfCalls;
	}
	public void setNumberOfCalls(int numberOfCalls) {
		this.numberOfCalls = numberOfCalls;
	}
	
	public String toString(){
		return "Name: "+name+", "+"C: "+created+", LA: "+lastActivity+", NC: "+numberOfCalls+" A: "+active;
	}
}
