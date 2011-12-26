package net.java.dev.moskito.control.configuration;


public enum MoskitoConnectorType {

	ThresholdsConnector("thresholdsconnector", "t"),
	ProducersConnector("producersconnector", "p"),
	AccumulatorsConnector("accumulatorsconnector", "a"),
	JMXConnector("jmxconnector", "jmx"),
	DefaultDummyConnector("defaultdummyconnector", "d"); //specify connector that will be used in case of wrong conf.
	
	private String typeName;
	private String shortAliasName;
	
	MoskitoConnectorType(String aTypeName, String aShortAliasName) {
		this.typeName = aTypeName;
		this.shortAliasName = aShortAliasName;
	}
	
	public static MoskitoConnectorType getMoskitoConnectorByType(String parameter) {
		for (MoskitoConnectorType type : values()) {
			if (type.getTypeName().equalsIgnoreCase(parameter) || type.getShortAliasName().equalsIgnoreCase(parameter)) 
				return type;
		}
		return DefaultDummyConnector;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getShortAliasName() {
		return shortAliasName;
	}

	public void setShortAliasName(String shortAliasName) {
		this.shortAliasName = shortAliasName;
	}
	
}
