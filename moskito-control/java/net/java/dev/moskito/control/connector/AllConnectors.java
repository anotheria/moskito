package net.java.dev.moskito.control.connector;

import net.java.dev.moskito.control.configuration.MoskitoConnectorType;

//TODO: translate to enum. ???
public class AllConnectors {

	private MoskitoConnector t = new ThresholdConnector();
	private MoskitoConnector p = new ProducersConnector();
	private MoskitoConnector a = new AccumulatorsConnector();
	private MoskitoConnector j = new JMXConnector();
	
	public AllConnectors() {
		
	}
	
	public MoskitoConnector getConnectorByType(MoskitoConnectorType type) {
		switch(type) {
			case ThresholdsConnector :
				return t;
			case ProducersConnector :
				return p;
			case AccumulatorsConnector:
				return a;
			case JMXConnector :
				return j;
			default:
				return t;
		}
	}
}
