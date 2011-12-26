package net.java.dev.moskito.control.connector;

import net.java.dev.moskito.control.check.ThresholdCheck;
import net.java.dev.moskito.control.configuration.MoskitoConnectorConfig;
import net.java.dev.moskito.control.configuration.MoskitoConnectorType;


public class ThresholdConnector extends MoskitoConnector<ThresholdCheck> {

	private static MoskitoConnectorType connectorType = MoskitoConnectorType.ThresholdsConnector;
	
	public static MoskitoConnectorType getConnectorType() {
		return connectorType;
	}
	
	public void configureConnector(MoskitoConnectorConfig configuration) {
		
	}
	
	public ThresholdCheck performMoskitoControlChecking() {
		return null;
	}


	@Override
	MoskitoConnectorType obtainConnectorType() {
		return MoskitoConnectorType.ThresholdsConnector;
	}

	
}
