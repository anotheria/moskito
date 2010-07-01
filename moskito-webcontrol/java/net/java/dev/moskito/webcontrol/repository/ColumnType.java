package net.java.dev.moskito.webcontrol.repository;

public enum ColumnType {
	
	FIELD,
	FORMULA,
	UNKNOWN;
	
	public static ColumnType convert(String v) {
		for (ColumnType value : values()) {
			if (value.name().equalsIgnoreCase(v)) {
				return value;
			}
		}
		return UNKNOWN;
	}
	
}
