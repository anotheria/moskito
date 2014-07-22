package net.anotheria.moskito.webui.util.offlinecharts;

import java.util.List;

/**
 * Represents a chart for generation.
 *
 * @author lrosenberg
 * @since 23.07.14 00:34
 */
public class OfflineChart {
	/**
	 * Caption of the chart.
	 */
	private String caption;

	/**
	 * Definition of chart lines.
	 */
	private List<OfflineChartLineDefinition> lineDefinitions;
	/**
	 * Points with values for all lines.
	 */
	private List<OfflineChartPoint> points;

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public List<OfflineChartLineDefinition> getLineDefinitions() {
		return lineDefinitions;
	}

	public void setLineDefinitions(List<OfflineChartLineDefinition> lineDefinitions) {
		this.lineDefinitions = lineDefinitions;
	}

	public List<OfflineChartPoint> getPoints() {
		return points;
	}

	public void setPoints(List<OfflineChartPoint> points) {
		this.points = points;
	}

	@Override public String toString(){
		return "Chart "+getCaption()+", LineDefinitions: "+getLineDefinitions()+", Points: "+getPoints();
	}
}
