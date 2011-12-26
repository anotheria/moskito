package net.java.dev.moskito.control.monitor.core.connector.config;

import java.util.ArrayList;
import java.util.List;

import net.java.dev.moskito.control.check.AccumulatorsCheck;
import net.java.dev.moskito.control.check.JMXCheck;
import net.java.dev.moskito.control.check.MoskitoControlCheck;
import net.java.dev.moskito.control.check.ProducerCheck;
import net.java.dev.moskito.control.check.ThresholdCheck;
import net.java.dev.moskito.control.configuration.MoskitoConnectorType;
import net.java.dev.moskito.control.connector.AccumulatorsConnector;
import net.java.dev.moskito.control.connector.JMXConnector;
import net.java.dev.moskito.control.connector.MoskitoConnector;
import net.java.dev.moskito.control.connector.ProducersConnector;
import net.java.dev.moskito.control.connector.ThresholdConnector;

public class MonitoredInstance {

	private String instanceName;
	private String type;
	private String group;
	private String moskitoAgentUrl;
	
	private List<MoskitoConnector> connectors; //connectors that are using to monitor this configured instance

	public String getInstanceName() {
		return instanceName;
	}

	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getMoskitoAgentUrl() {
		return moskitoAgentUrl;
	}

	public void setMoskitoAgentUrl(String moskitoAgentUrl) {
		this.moskitoAgentUrl = moskitoAgentUrl;
	}

	public List<MoskitoConnector> getConnectors() {
		return connectors;
	}

	public void setConnectors(List<MoskitoConnector> connectors) {
		this.connectors = connectors;
	}
	
	public MoskitoControlMonitorStatusCheck performStatusCheck() {
		return new MoskitoControlMonitorStatusCheck();
	}
	
	
	public ThresholdCheck preformThresholdCheck() {
		//dummy!
		return new ThresholdCheck();
	}
	
	
	public ProducerCheck performProducerCheck() {
		return null;
	}
	
	public AccumulatorsCheck performAccumulatorsCheck() {
		return null;
	}
	
	public JMXCheck performJMXCheck() {
		return null;
	}
	
	public List<MoskitoControlCheck> getAllChecks() {
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public MoskitoConnector getConnectorByType(MoskitoConnectorType type) {
		switch (type) {
			case ThresholdsConnector:
			{
				for (MoskitoConnector mc : connectors) {
					
				}
			}

		}
		
		
		return null;
	}
	
	public static void main(String[] a) {
		MonitoredInstance i = new MonitoredInstance();
		List<MoskitoConnector> connectors = new ArrayList<MoskitoConnector>();
		MoskitoConnector<ThresholdCheck> th = new ThresholdConnector();
		MoskitoConnector<ProducerCheck> pr = new ProducersConnector();
		MoskitoConnector<AccumulatorsCheck> ac = new AccumulatorsConnector();
		MoskitoConnector<JMXCheck> jx = new JMXConnector();
		
		connectors.add(th);
		connectors.add(pr);
		connectors.add(ac);
		connectors.add(jx);
		
		i.setConnectors(connectors);
		
		for (MoskitoConnector mc : connectors) {
//			if (mc instanceof th.getClass()) {
//				System.out.println("This is TH c");
//			}
			System.out.println(mc.getClass());
		}
	}

	@Override
	public String toString() {
		return "MonitoredInstance [instanceName=" + instanceName + ", type="
				+ type + ", group=" + group + ", moskitoAgentUrl="
				+ moskitoAgentUrl + ", connectors=" + connectors + "]";
	}
	
	
}
