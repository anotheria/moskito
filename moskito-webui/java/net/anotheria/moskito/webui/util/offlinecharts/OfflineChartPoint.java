package net.anotheria.moskito.webui.util.offlinecharts;

import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 23.07.14 00:33
 */
public class OfflineChartPoint {
	/**
	 * Values for different chart lines. They are in the same order as the OfflineChartLineDefinition in OfflineChart.
	 */
	private List<String> values;
	/**
	 * Timestamp as string.
	 */
	private String timestampAsString;

	/**
	 * Timestamp of this point.
	 */
	private long timestamp;



}
