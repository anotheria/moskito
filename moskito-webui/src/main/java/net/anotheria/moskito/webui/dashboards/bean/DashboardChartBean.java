package net.anotheria.moskito.webui.dashboards.bean;

import net.anotheria.moskito.webui.accumulators.api.MultilineChartAO;
import net.anotheria.moskito.webui.dashboards.api.DashboardChartAO;

/**
 *
 */
public class DashboardChartBean {
	/**
	 * Caption of this chart.
	 */
	private String caption;
	/**
	 * Chart data for chart lines.
	 */
	private MultilineChartAO chart;
	/**
	 * Dashboards where this gauge can be added.
	 */
	private String dashboardsToAdd;

	public DashboardChartBean(DashboardChartAO dashboardChartAO, String dashboardsToAdd) {
		this.caption = dashboardChartAO.getCaption();
		this.chart = dashboardChartAO.getChart();
		this.dashboardsToAdd = dashboardsToAdd;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public MultilineChartAO getChart() {
		return chart;
	}

	public void setChart(MultilineChartAO chart) {
		this.chart = chart;
	}

	public String getDashboardsToAdd() {
		return dashboardsToAdd;
	}

	public void setDashboardsToAdd(String dashboardsToAdd) {
		this.dashboardsToAdd = dashboardsToAdd;
	}
}
