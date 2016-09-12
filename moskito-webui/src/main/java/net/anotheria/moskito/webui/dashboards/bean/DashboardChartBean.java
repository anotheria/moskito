package net.anotheria.moskito.webui.dashboards.bean;

import net.anotheria.moskito.webui.accumulators.api.MultilineChartAO;
import net.anotheria.moskito.webui.dashboards.api.DashboardChartAO;
import net.anotheria.util.StringUtils;

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
	 * Concatenated chart's names.
	 */
	private String chartNames;
	/**
	 * Dashboards where this gauge can be added.
	 */
	private String dashboardsToAdd;

	public DashboardChartBean(DashboardChartAO dashboardChartAO, String dashboardsToAdd) {
		this.caption = dashboardChartAO.getCaption();
		this.chart = dashboardChartAO.getChart();
		this.chartNames = StringUtils.concatenateTokens(dashboardChartAO.getChart().getNames(),",");
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

	public String getChartNames() {
		return chartNames;
	}

	public void setChartNames(String chartNames) {
		this.chartNames = chartNames;
	}

	public String getDashboardsToAdd() {
		return dashboardsToAdd;
	}

	public void setDashboardsToAdd(String dashboardsToAdd) {
		this.dashboardsToAdd = dashboardsToAdd;
	}
}
