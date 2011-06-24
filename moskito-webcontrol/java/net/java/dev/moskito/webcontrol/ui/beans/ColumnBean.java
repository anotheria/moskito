package net.java.dev.moskito.webcontrol.ui.beans;

import net.java.dev.moskito.webcontrol.guards.Condition;

public class ColumnBean {

	private String name;
	private String key;
	private Condition color = Condition.GREEN;

	public ColumnBean(String columnName, String columnKey, Condition color) {
		this.name = columnName;
		this.key = columnKey;
		this.color = color;
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

	public void setColor(Condition color) {
	    this.color = color;
	}

	public Condition getColor() {
	    return color;
	}

}
