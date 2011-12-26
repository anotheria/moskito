package net.java.dev.moskito.control.connector;

import net.java.dev.moskito.control.check.MoskitoControlCheck;
import net.java.dev.moskito.control.configuration.MoskitoConnectorConfig;
import net.java.dev.moskito.control.configuration.MoskitoConnectorType;


public abstract class MoskitoConnector<T extends MoskitoControlCheck> {
	
	private MoskitoConnectorConfig config;
	
	abstract MoskitoConnectorType obtainConnectorType();
	
	abstract T performMoskitoControlChecking();
	
}
