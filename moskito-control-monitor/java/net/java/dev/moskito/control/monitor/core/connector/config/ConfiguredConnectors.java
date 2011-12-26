package net.java.dev.moskito.control.monitor.core.connector.config;


import java.util.ArrayList;
import java.util.List;

import net.anotheria.util.StringUtils;

import org.json.JSONArray;
import org.json.JSONObject;

@Deprecated
public class ConfiguredConnectors {
	
	private List<MonitoredInstance> moskitoConnectorsList = new ArrayList<MonitoredInstance>();

	public ConfiguredConnectors() {
		
		initConnectors("moskito_connector_config.json");
	}
	
	private void initConnectors(String connectorsFile) {
		
		try {
			JSONArray permArray = ConfigFileReader.readConfigFile(connectorsFile, "connectors");
			for (int i = 0; i < permArray.length(); i++) {
				JSONObject conn = permArray.getJSONObject(i);
				
				
				
				System.out.println("Parsing Object: "+conn);
				
				// here we have array with connector definitions
				String connectorDefinitions = conn.getString("connectorTypes");
				System.out.println("Connectors="+connectorDefinitions);
				
				List<String> connectors = StringUtils.tokenize2list(connectorDefinitions, ',');
				System.out.println("conn List="+connectors);
				
				
				
			}
		} catch (Exception e) {
			throw new RuntimeException("can`t parse "+connectorsFile, e);
		}
	}

	public static void main(String[] args) {
		ConfiguredConnectors conf = new ConfiguredConnectors();
		System.out.println();
	}
}
