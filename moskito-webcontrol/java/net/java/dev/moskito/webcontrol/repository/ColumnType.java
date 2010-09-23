package net.java.dev.moskito.webcontrol.repository;

public enum ColumnType {
	
	FIELD("field"),
	FORMULA("formula"),
	UNKNOWN("unknown");
	
	private String type;
	
	private ColumnType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return this.type;
	}
	
	public static ColumnType convert(String v) {
		for (ColumnType value : values()) {
			if (value.name().equalsIgnoreCase(v)) {
				return value;
			}
		}
		return UNKNOWN;
	}
	
}
