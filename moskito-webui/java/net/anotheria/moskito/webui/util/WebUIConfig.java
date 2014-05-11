package net.anotheria.moskito.webui.util;

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

	/**
	 * List of remote instances. Remote instances are only active if mode is remote.
	 */
	private RemoteInstance[] remotes;

	/**
	 * Connectivity mode, available values are default or local.
	 */
	private ConnectivityMode connectivityMode = ConnectivityMode.LOCAL;


	/**
	 * Usage mode, default is shared.
	 */
	private UsageMode usageMode = UsageMode.SHARED;

	/**
	 * If true sends a tracking pixel to counter.moskito.org to track worldwide usage.
	 */
	private boolean trackUsage = true;

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

	public void addRemote(RemoteInstance newRemoteInstance) {
		RemoteInstance[] oldRemotes = remotes;
		remotes = new RemoteInstance[oldRemotes.length+1];
		System.arraycopy(oldRemotes, 0, remotes, 0, oldRemotes.length);
		remotes[remotes.length-1] = newRemoteInstance;
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

	public boolean isTrackUsage() {
		return trackUsage;
	}

	public void setTrackUsage(boolean trackUsage) {
		this.trackUsage = trackUsage;
	}

	public RemoteInstance[] getRemotes() {
		return remotes;
	}

	public void setRemotes(RemoteInstance[] remotes) {
		this.remotes = remotes;
	}

	public ConnectivityMode getConnectivityMode() {
		return connectivityMode;
	}

	public void setConnectivityMode(ConnectivityMode connectivityMode) {
		this.connectivityMode = connectivityMode;
	}

	public UsageMode getUsageMode() {
		return usageMode;
	}

	public void setUsageMode(UsageMode usageMode) {
		this.usageMode = usageMode;
	}

}
