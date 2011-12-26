package net.java.dev.moskito.control.connector;

import net.java.dev.moskito.control.check.JMXCheck;
import net.java.dev.moskito.control.configuration.MoskitoConnectorType;

public class JMXConnector extends MoskitoConnector<JMXCheck> {

	private static MoskitoConnectorType connectorType = MoskitoConnectorType.ThresholdsConnector;
	
	public static MoskitoConnectorType getConnectorType() {
		return connectorType;
	}
	
	@Override
	JMXCheck performMoskitoControlChecking() {
		return null;
	}

	@Override
	MoskitoConnectorType obtainConnectorType() {
		return MoskitoConnectorType.JMXConnector;
	}
	
	

}
