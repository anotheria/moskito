package net.java.dev.moskito.webui.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Dashboard object.
 *
 * @author dsilenko
 */
public class DashboardBean {
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

}
