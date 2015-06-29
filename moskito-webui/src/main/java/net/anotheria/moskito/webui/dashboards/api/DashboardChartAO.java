package net.anotheria.moskito.webui.dashboards.api;

import net.anotheria.moskito.webui.accumulators.api.AccumulatedSingleGraphAO;

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
	 * Caption of this chart.
	 */
	private String caption;
	/**
	 * Chart data for chart lines.
	 */
	private AccumulatedSingleGraphAO chartData;

	/**
	 * Names of the lines.
	 */
	private String[] lineNames;

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public AccumulatedSingleGraphAO getChartData() {
		return chartData;
	}

	public void setChartData(AccumulatedSingleGraphAO chartData) {
		this.chartData = chartData;
	}

	public String[] getLineNames() {
		return lineNames;
	}

	public void setLineNames(String[] lineNames) {
		this.lineNames = lineNames;
	}

	@Override
	public String toString() {
		return "DashboardChartAO{" +
				"caption='" + caption + '\'' +
				", chartData=" + chartData +
				'}';
	}
}
