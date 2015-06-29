package net.anotheria.moskito.webui.util.offlinecharts;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 23.07.14 00:56
 */
public final class OfflineChartGeneratorFactory {

	private static OfflineChartGenerator generator = new DummyOfflineChartGenerator();

	public static OfflineChartGenerator getGenerator(){
		return generator;
	}
}
