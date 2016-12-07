package net.anotheria.moskito.webui.dashboards.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
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
@XmlAccessorType(XmlAccessType.FIELD)
public class DashboardDefinitionAO implements Serializable{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 2831684393429730423L;

	/**
	 * Name of the Dashboard.
	 */
	@XmlElement
	private String name;

	/**
	 * List with gauges names for this dashboard.
	 */
	@XmlElement
	private List<String> gauges = Collections.emptyList();

	/**
	 * List for threshold names for this dashboard.
	 */
	@XmlElement
	private List<String> thresholds = Collections.emptyList();

	/**
	 * Charts for this dashboard.
	 */
	@XmlElement
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
