package net.anotheria.moskito.webui.dashboards.api;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * This class represents configured and existing dashboard.
 *
 * @author lrosenberg
 * @since 15.04.15 09:13
 */
@XmlRootElement(name="DashboardDefinition")
public class DashboardDefinitionAO {
	/**
	 * Name of the Dashboard.
	 */
	private String name;
	private List<String> gauges = Collections.EMPTY_LIST;
	private List<String> thresholds = Collections.EMPTY_LIST;
	private List<DashboardChartDefinitionAO> charts = new LinkedList<DashboardChartDefinitionAO>();

	public List<String> getGauges() {
		return gauges;
	}



	public void setGauges(List<String> gauges) {
		this.gauges = gauges;
	}

	public List<String> getThresholds() {
		return thresholds;
	}

	public void setThresholds(List<String> thresholds) {
		this.thresholds = thresholds;
	}

	public void addChart(DashboardChartDefinitionAO chart){
		charts.add(chart);
	}

	public List<DashboardChartDefinitionAO> getCharts() {
		return charts;
	}

	public void setCharts(List<DashboardChartDefinitionAO> charts) {
		this.charts = charts;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
