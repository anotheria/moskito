package net.anotheria.moskito.webui.dashboards.api;

import net.anotheria.moskito.webui.accumulators.api.MultilineChartAO;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

/**
 * A single chart on the dashboard.
 *
 * @author lrosenberg
 * @since 14.02.15 00:46
 */
@XmlRootElement (name = "Chart")
public class DashboardChartAO implements Serializable{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Caption of this chart.
	 */
	private String caption;
	/**
	 * Chart data for chart lines.
	 */
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
}
