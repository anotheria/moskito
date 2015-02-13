package net.anotheria.moskito.webui.dashboards.bean;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 12.02.15 20:00
 */
public class DashboardThresholdBean {
	private String name;
	private String color;
	private String value;

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "DashboardThresholdBean{" +
				"color='" + color + '\'' +
				", name='" + name + '\'' +
				", value='" + value + '\'' +
				'}';
	}
}
