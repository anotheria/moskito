package net.anotheria.moskito.webui.util.offlinecharts;

/**
 * OfflineChartGenerator is used to generate offline charts that can be sent via mail or used in multiple ways.
 *
 * @author lrosenberg
 * @since 23.07.14 00:32
 */
public interface OfflineChartGenerator {
	/**
	 * This method generates an accumulator chart consisting of multiple lines. Returns the binary data of the accumulator chart.
	 * @param chart chart to generate.
	 * @return
	 * @throws OfflineChartGeneratorException
	 */
	byte[] generateAccumulatorChart(OfflineChart chart) throws OfflineChartGeneratorException;
}
