package net.java.dev.moskito.webcontrol.repository;

public enum AttributeType {
	LONG,
	INT,
	DOUBLE,
	STRING,
	NOT_FOUND;
	
	public static AttributeType convert(String attributeType) {
		if ("int".equalsIgnoreCase(attributeType)) {
			return INT;
		}
		if ("long".equalsIgnoreCase(attributeType)) {
			return LONG;
		}
		if ("string".equalsIgnoreCase(attributeType)) {
			return STRING;
		}
		if ("double".equalsIgnoreCase(attributeType)) {
			return DOUBLE;
		}
		return NOT_FOUND;
	}
}
