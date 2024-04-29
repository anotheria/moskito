package net.anotheria.moskito.webui.dashboards.api;

import net.anotheria.moskito.webui.accumulators.api.MultilineChartAO;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * A single chart on the dashboard.
 *
 * @author lrosenberg
 * @since 14.02.15 00:46
 */
@XmlRootElement (name = "Chart")
@XmlAccessorType(XmlAccessType.FIELD)
public class DashboardChartAO implements Serializable{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 630779541529421215L;

	/**
	 * Caption of this chart.
	 */
	@XmlElement
	private String caption;
	/**
	 * Chart data for chart lines.
	 */
	@XmlElement
	private MultilineChartAO chart;

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

	@Override
	public String toString() {
		return "DashboardChartAO{" +
				"caption='" + caption + '\'' +
				", chart=" + chart +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		DashboardChartAO that = (DashboardChartAO) o;
		return chart.equals(that.chart);

	}

	@Override
	public int hashCode() {
		return chart.hashCode();
	}
}
