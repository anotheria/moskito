package net.java.dev.moskito.control.connector;

import net.java.dev.moskito.control.check.AccumulatorsCheck;
import net.java.dev.moskito.control.configuration.MoskitoConnectorConfig;
import net.java.dev.moskito.control.configuration.MoskitoConnectorType;


public class AccumulatorsConnector extends MoskitoConnector<AccumulatorsCheck> {

	private static MoskitoConnectorType connectorType = MoskitoConnectorType.ThresholdsConnector;
	
	public static MoskitoConnectorType getConnectorType() {
		return connectorType;
	}
	
	public void configureConnector(MoskitoConnectorConfig configuration) {
	}
	
	public AccumulatorsCheck performMoskitoControlChecking() {
		return null;
	}
	
	@Override
	MoskitoConnectorType obtainConnectorType() {
		return MoskitoConnectorType.AccumulatorsConnector;
	}
	
}
