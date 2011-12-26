package net.java.dev.moskito.control.monitor.core.cluster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.anotheria.util.StringUtils;
import net.java.dev.moskito.control.configuration.MoskitoConnectorType;
import net.java.dev.moskito.control.connector.AccumulatorsConnector;
import net.java.dev.moskito.control.connector.JMXConnector;
import net.java.dev.moskito.control.connector.MoskitoConnector;
import net.java.dev.moskito.control.connector.ProducersConnector;
import net.java.dev.moskito.control.connector.ThresholdConnector;
import net.java.dev.moskito.control.monitor.core.connector.config.ConfigFileReader;
import net.java.dev.moskito.control.monitor.core.connector.config.MonitoredInstance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public enum Cluster {

	INSTANCE;
	
	private List<MonitoredInstance> instanceToMonitorList = new ArrayList<MonitoredInstance>();
	private List<InstanceGroup> monitoredGroups = new ArrayList<InstanceGroup>();

	public List<InstanceGroup> getMonitoredGroups() {
		return monitoredGroups;
	}

	Cluster() {
		configureInstances("monitored_instances_configuration.json");
	}

	private void splitInstancesToGroups(List<MonitoredInstance> instanceToMonitorList2, Set<String> groupNames) {
		for (String name : groupNames) {
			InstanceGroup group = new InstanceGroup(name);
			monitoredGroups.add(group);
			group.setMonitoredInstancesList(getInstancesByGroup(group));
		}
	}
	
	private List<MonitoredInstance> getInstancesByGroup(InstanceGroup group) {
		List<MonitoredInstance> list = new ArrayList<MonitoredInstance>();
		for (MonitoredInstance inst : instanceToMonitorList) {
			if (inst.getGroup().equals(group.getName())) {
				list.add(inst);
			}
		}
		return list;
	}

	private void configureInstances(String configFileName) {
		try {
			JSONArray instancesArray = ConfigFileReader.readConfigFile(configFileName, "instances");
			
			Set<String> groupNames = new HashSet<String>();
			
			for (int i = 0; i < instancesArray.length(); i++) {
				JSONObject jsonLine = instancesArray.getJSONObject(i);
				MonitoredInstance mi = initInstanceFromConfig(jsonLine);
				instanceToMonitorList.add(mi);
				groupNames.add(mi.getGroup());
				
			}
			
			System.out.println(" ---- Number of gorups is: "+groupNames.size() +". Threy are: "+groupNames);
			
			splitInstancesToGroups(instanceToMonitorList, groupNames);
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	private MonitoredInstance initInstanceFromConfig(JSONObject json) throws JSONException {
		MonitoredInstance instance = new MonitoredInstance();
		List<MoskitoConnector> configuredConnectors = new ArrayList<MoskitoConnector>();

		String instanceName = json.getString("name");
		String type = json.getString("type");
		String group = json.getString("group");
		String moskitoAgentUrl = json.getString("url");
		String connectors = json.getString("connectors").replaceAll(" ", "");
		
		List<String> cc = StringUtils.tokenize2list(connectors, ',');
		for (String s : cc) {
			MoskitoConnectorType cType = MoskitoConnectorType.getMoskitoConnectorByType(s);
			switch (cType) {
				case ThresholdsConnector:
					configuredConnectors.add(new ThresholdConnector());
					break;
				case ProducersConnector:
					configuredConnectors.add(new ProducersConnector());
					break;
				case AccumulatorsConnector:
					configuredConnectors.add(new AccumulatorsConnector());
					break;
				case JMXConnector:
					configuredConnectors.add(new JMXConnector());
					break;
			}
			System.out.println("Found type: "+ cType);
		}
		
		instance.setInstanceName(instanceName);
		instance.setType(type);
		instance.setGroup(group);
		instance.setMoskitoAgentUrl(moskitoAgentUrl); // move it to connector config??
		instance.setConnectors(configuredConnectors);
		
		return instance;
	}
	
	public List<MonitoredInstance> getInstanceToMonitorList() {
		return instanceToMonitorList;
	}

	public void setInstanceToMonitorList(List<MonitoredInstance> instanceToMonitorList) {
		this.instanceToMonitorList = instanceToMonitorList;
	}

	public static void main(String[] args) {

		List<MonitoredInstance> mi = Cluster.INSTANCE.instanceToMonitorList;
		System.out.println(mi);
	}
}
