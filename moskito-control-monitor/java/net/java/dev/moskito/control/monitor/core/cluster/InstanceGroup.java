package net.java.dev.moskito.control.monitor.core.cluster;

import java.util.ArrayList;
import java.util.List;

import net.java.dev.moskito.control.monitor.core.connector.config.MonitoredInstance;

public class InstanceGroup {

	private String name;
	private List<MonitoredInstance> monitoredInstancesList = new ArrayList<MonitoredInstance>();
	
	public List<MonitoredInstance> getMonitoredInstancesList() {
		return monitoredInstancesList;
	}

	public InstanceGroup(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

 	public void setMonitoredInstancesList(List<MonitoredInstance> instancesByGroup) {
		this.monitoredInstancesList = instancesByGroup;
	}
}
