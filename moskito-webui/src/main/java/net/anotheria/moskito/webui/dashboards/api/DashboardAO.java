package net.anotheria.moskito.webui.dashboards.api;

import net.anotheria.moskito.webui.gauges.api.GaugeAO;
import net.anotheria.moskito.webui.threshold.api.ThresholdStatusAO;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Represents a single Dashboard object.
 *
 * @author lrosenberg
 * @since 16.04.15 13:17
 */
@XmlRootElement(name="Dashboard")
public class DashboardAO implements Serializable{
	@XmlElement
	private String name;

	/**
	 * Gauges.
	 */
	@XmlElement
	private List<GaugeAO> gauges;

	@XmlElement
	private List<ThresholdStatusAO> thresholds;

	@XmlElement
	private List<DashboardChartAO> charts;

	public List<GaugeAO> getGauges() {
		return gauges;
	}

	public void setGauges(List<GaugeAO> gauges) {
		this.gauges = gauges;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ThresholdStatusAO> getThresholds() {
		return thresholds;
	}

	public void setThresholds(List<ThresholdStatusAO> thresholds) {
		this.thresholds = thresholds;
	}

	public List<DashboardChartAO> getCharts() {
		return charts;
	}

	public void setCharts(List<DashboardChartAO> charts) {
		this.charts = charts;
	}
}
