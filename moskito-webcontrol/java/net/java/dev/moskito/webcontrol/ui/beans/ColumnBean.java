package net.java.dev.moskito.webcontrol.ui.beans;

public class ColumnBean {

	private String name;
	private String key;

	public ColumnBean(String columnName, String columnKey) {
		this.name = columnName;
		this.key = columnKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
