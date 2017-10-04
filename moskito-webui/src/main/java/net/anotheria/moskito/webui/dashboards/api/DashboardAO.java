package net.anotheria.moskito.webui.dashboards.api;

import net.anotheria.moskito.webui.gauges.api.GaugeAO;
import net.anotheria.moskito.webui.producers.api.ProducerAO;
import net.anotheria.moskito.webui.threshold.api.ThresholdStatusAO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
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
@XmlAccessorType(XmlAccessType.FIELD)
public class DashboardAO implements Serializable{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 6792256502007883327L;

	/**
	 * Dashboard name.
	 */
	@XmlElement
	private String name;

	/**
	 * Gauges.
	 */
	@XmlElement
	private List<GaugeAO> gauges;

	/**
	 * Thresholds.
	 */
	@XmlElement
	private List<ThresholdStatusAO> thresholds;

	/**
	 * Charts of this dashboard.
	 */
	@XmlElement
	private List<DashboardChartAO> charts;

	/**
	 * Producers of this dashboard.
	 */
	@XmlElement
	private List<ProducerAO> producers;


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

	public List<ProducerAO> getProducers() {
		return producers;
	}

	public void setProducers(List<ProducerAO> producers) {
		this.producers = producers;
	}

	@Override
	public String toString() {
		return "DashboardAO{" +
				"charts=" + charts +
				", name='" + name + '\'' +
				", gauges=" + gauges +
				", thresholds=" + thresholds +
				'}';
	}
}
