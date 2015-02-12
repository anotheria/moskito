package net.anotheria.moskito.core.config.dashboards;

import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.util.Arrays;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 12.02.15 00:58
 */
@ConfigureMe
public class DashboardConfig {
	/**
	 * Name of the dashboard.
	 */
	@Configure
	private String name;

	/**
	 * Charts to be shown on this dashboard.
	 */
	@Configure
	private ChartConfig [] charts;

	/**
	 * Names of the thresholds that should on that dashboard.
	 */
	@Configure
	private String [] thresholds;

	/**
	 * Names of the gauges that should on that dashboard.
	 */
	@Configure
	private String [] gauges;

	@Override
	public String toString() {
		return "DashboardConfig{" +
				"charts=" + Arrays.toString(charts) +
				", name='" + name + '\'' +
				", thresholds=" + Arrays.toString(thresholds) +
				", gauges=" + Arrays.toString(gauges) +
				'}';
	}

	public String[] getGauges() {
		return gauges;
	}

	public void setGauges(String[] gauges) {
		this.gauges = gauges;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getThresholds() {
		return thresholds;
	}

	public void setThresholds(String[] thresholds) {
		this.thresholds = thresholds;
	}

	public ChartConfig[] getCharts() {
		return charts;
	}

	public void setCharts(ChartConfig[] charts) {
		this.charts = charts;
	}
}
