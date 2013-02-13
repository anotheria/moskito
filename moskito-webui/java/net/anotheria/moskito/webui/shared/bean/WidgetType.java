package net.anotheria.moskito.webui.shared.bean;

/**
 * Enum for widget types.
 *
 * @author dsilenko
 */
public enum WidgetType {

	TABLE("Table"),
	THRESHOLD("Threshold"),
	CHART("Chart");

	private String type;

	private WidgetType(String aType) {
		type = aType;
	}

	public String getType() {
		return type;
	}

}
