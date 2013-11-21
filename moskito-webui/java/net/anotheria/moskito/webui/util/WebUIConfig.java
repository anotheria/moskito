package net.anotheria.moskito.webui.util;

import net.anotheria.moskito.webui.charts.ChartEngine;
import org.configureme.ConfigurationManager;
import org.configureme.annotations.ConfigureMe;

/**
 * Configuration class for web user interface config.
 */
@ConfigureMe(name="mskwebui", allfields=true)
public class WebUIConfig {
	/**
	 * Width of the producerChart.
	 */
	private int producerChartWidth = 1200;
	/**
	 * Height of the producerChart.
	 */
	private int producerChartHeight = 600;

	/**
	 * Default ChartEngine.
	 */
	private ChartEngine defaultChartEngine = ChartEngine.GOOGLE_CHART_API;

	public int getProducerChartWidth() {
		return producerChartWidth;
	}
	public void setProducerChartWidth(int producerChartWidth) {
		this.producerChartWidth = producerChartWidth;
	}
	public int getProducerChartHeight() {
		return producerChartHeight;
	}
	public void setProducerChartHeight(int producerChartHeight) {
		this.producerChartHeight = producerChartHeight;
	}

	public ChartEngine getDefaultChartEngine() {
		return defaultChartEngine;
	}

	public void setDefaultChartEngine(ChartEngine defaultChartEngine) {
		this.defaultChartEngine = defaultChartEngine;
	}

	/**
	 * Returns WebUIConfig instance.
	 * @return
	 */
	public static final WebUIConfig getInstance(){
		return WebUIConfigInstanceHolder.instance;
	}

	/**
	 * Singleton holder class.
	 */
	private static class WebUIConfigInstanceHolder{
		static WebUIConfig instance = new WebUIConfig();
		static{
			try{
				ConfigurationManager.INSTANCE.configure(instance);
			}catch(IllegalArgumentException e){;}//ignore
		}
	}


}
