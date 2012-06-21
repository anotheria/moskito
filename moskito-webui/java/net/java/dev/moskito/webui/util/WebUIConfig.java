package net.java.dev.moskito.webui.util;

import org.configureme.ConfigurationManager;
import org.configureme.annotations.ConfigureMe;

@ConfigureMe(name="mskwebui", allfields=true)
public class WebUIConfig {
	private int producerChartWidth = 1200;
	private int producerChartHeight = 600;

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
	
	public static final WebUIConfig getInstance(){
		return WebUIConfigInstanceHolder.instance;
	}
	
	private static class WebUIConfigInstanceHolder{
		static WebUIConfig instance = new WebUIConfig();
		static{
			try{
				ConfigurationManager.INSTANCE.configure(instance);
			}catch(Exception e){;}//ignore
		}
	}
}
