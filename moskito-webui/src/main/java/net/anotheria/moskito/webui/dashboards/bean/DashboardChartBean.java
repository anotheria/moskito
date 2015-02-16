package net.anotheria.moskito.webui.dashboards.bean;

import net.anotheria.moskito.webui.accumulators.api.AccumulatedSingleGraphAO;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 14.02.15 00:46
 */
public class DashboardChartBean {
	private String caption;
	private AccumulatedSingleGraphAO chartData;

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

	private String[] lineNames;

	public String[] getLineNames() {
		return lineNames;
	}

	public void setLineNames(String[] lineNames) {
		this.lineNames = lineNames;
	}

	@Override
	public String toString() {
		return "DashboardChartBean{" +
				"caption='" + caption + '\'' +
				", chartData=" + chartData +
				'}';
	}
}
