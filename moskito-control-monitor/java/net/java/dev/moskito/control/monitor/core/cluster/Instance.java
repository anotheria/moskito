package net.java.dev.moskito.control.monitor.core.cluster;

public class Instance {

	private String name;
	private InstanceGroup group;
	
	public Instance() {
		
	}
	
	public Instance(String name, InstanceGroup group) {
		this.name = name;
		this.group = group;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public InstanceGroup getGroup() {
		return group;
	}
	public void setGroup(InstanceGroup group) {
		this.group = group;
	}
	
	
}
