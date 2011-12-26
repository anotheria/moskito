package net.java.dev.moskito.control.connector;

import net.java.dev.moskito.control.check.ProducerCheck;
import net.java.dev.moskito.control.configuration.MoskitoConnectorConfig;
import net.java.dev.moskito.control.configuration.MoskitoConnectorType;


public class ProducersConnector extends MoskitoConnector<ProducerCheck> {

	private static MoskitoConnectorType connectorType = MoskitoConnectorType.ThresholdsConnector;
	
	public static MoskitoConnectorType getConnectorType() {
		return connectorType;
	}
	
	public ProducerCheck performMoskitoControlChecking() {
		// TODO Auto-generated method stub
		return null;
	}

	public void configureConnector(MoskitoConnectorConfig configuration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	MoskitoConnectorType obtainConnectorType() {
		return MoskitoConnectorType.ProducersConnector;
	}
	
}
