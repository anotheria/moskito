package net.anotheria.moskito.webui.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Dashboard object.
 *
 * @author dsilenko
 */
public class DashboardBean implements Serializable {

	private static final long serialVersionUID = -3814471914706674484L;
	private String name;
	private List<DashboardWidgetBean> widgets = new ArrayList<DashboardWidgetBean>();

	public DashboardBean(String aName) {
		name = aName;
	}

	public DashboardBean(String aName, List<DashboardWidgetBean> aWidgets) {
		name = aName;
		widgets = aWidgets;
	}

	public String getName() {
		return name;
	}

	public void setName(String aName) {
		name = aName;
	}

	public List<DashboardWidgetBean> getWidgets() {
		return widgets;
	}

	public void setWidgets(List<DashboardWidgetBean> aWidgets) {
		widgets = aWidgets;
	}

	@Override
	public String toString() {
		return "DashboardBean{" +
				"name='" + name + '\'' +
				", widgets=" + widgets +
				'}';
	}
}
