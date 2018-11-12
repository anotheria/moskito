package net.anotheria.moskito.webui.util;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.configureme.ConfigurationManager;
import org.configureme.annotations.ConfigureMe;
import org.configureme.annotations.DontConfigure;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Configuration class for web user interface config.
 */
@ConfigureMe(name="moskito-inspect", allfields=true)
@SuppressFBWarnings({"EI_EXPOSE_REP", "EI_EXPOSE_REP2"})
public class WebUIConfig {
	/**
	 * Width of the producerChart.
	 */
	private int producerChartWidth = 1200;
	/**
	 * Height of the producerChart.
	 */
	private int producerChartHeight = 600;

	private AuthConfig authentication = new AuthConfig();

	/**
	 * List of remote instances. Remote instances are only active if mode is remote.
	 */
	private RemoteInstance[] remotes = new RemoteInstance[0];

	/**
	 * Concurrent map of remote keys. Created from remote instances configured list.
	 */
	@DontConfigure
	private ConcurrentMap<String, Boolean> remotesKeys;

	/**
	 * Synchronized list of remote intances. Created from remote instances configured list.
	 */
	@DontConfigure
	private List<RemoteInstance> remotesSynced;

	/**
	 * Connectivity mode, available values are default or local.
	 */
	private ConnectivityMode connectivityMode = ConnectivityMode.LOCAL;

	/**
	 * ProducerFilterConfig allows to configure filters that will exclude producers from overview.
	 *
	 */
	private ProducerFilterConfig[] filters = new ProducerFilterConfig[0];

	/**
	 * Decorators.
	 */
	private DecoratorConfig[] decorators;


	/**
	 * Usage mode, default is shared.
	 */
	private UsageMode usageMode = UsageMode.SHARED;

	/**
	 * If true sends a tracking pixel to counter.moskito.org to track worldwide usage.
	 */
	private boolean trackUsage = true;

	/**
	 * If true, the online help link to olark is shown, if false it isn't.
	 */
	private boolean showOnlineHelp = true;

	/**
	 * Custom logo url
	 */
	private String customLogoUrl="";

	/**
	 * If true beta features be enabled
	 */
	private boolean betaMode = false;

	private boolean displayIphoneIcons = true;

	private ThresholdGraphColor[] thresholdGraphColors;

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

	/**
	 * Returns WebUIConfig instance.
	 * @return
	 */
	public static final WebUIConfig getInstance(){
		return WebUIConfigInstanceHolder.instance;
	}

	public void addRemote(RemoteInstance newRemoteInstance) {
		if (remotesKeys.putIfAbsent(newRemoteInstance.getSelectKey(), true) == null)
			remotesSynced.add(newRemoteInstance);
	}

	public AuthConfig getAuthentication() {
		return authentication;
	}

	public void setAuthentication(AuthConfig authentication) {
		this.authentication = authentication;
	}

	public boolean isBetaMode() {
		return betaMode;
	}

	public void setBetaMode(boolean betaMode) {
		this.betaMode = betaMode;
	}

	private WebUIConfig(){
		initRemotesCollections();
	}

	public boolean isDisplayIphoneIcons() {
		return displayIphoneIcons;
	}

	public void setDisplayIphoneIcons(boolean displayIphoneIcons) {
		this.displayIphoneIcons = displayIphoneIcons;
	}

	/**
	 * Singleton holder class.
	 */
	private static class WebUIConfigInstanceHolder{
		static WebUIConfig instance = new WebUIConfig();
		static{
			try{
				ConfigurationManager.INSTANCE.configure(instance);
			}catch(IllegalArgumentException e){}//ignore
		}
	}

	public boolean isTrackUsage() {
		return trackUsage;
	}

	public void setTrackUsage(boolean trackUsage) {
		this.trackUsage = trackUsage;
	}

	public RemoteInstance[] getRemotes() {
		return remotesSynced.toArray(new RemoteInstance[remotesSynced.size()]);
	}

	public void setRemotes(RemoteInstance[] remotes) {
		this.remotes = remotes;
		initRemotesCollections();
	}

	public void initRemotesCollections(){
		 remotesSynced = Collections.synchronizedList(new ArrayList<RemoteInstance>());
		 remotesKeys = new ConcurrentHashMap<>();
		 remotesSynced.addAll(Arrays.asList(remotes));
		 for (RemoteInstance remote: remotes){
			 remotesKeys.put(remote.getSelectKey(), true);
		 }
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

	public ProducerFilterConfig[] getFilters() {
		return filters;
	}

	public void setFilters(ProducerFilterConfig[] filters) {
		this.filters = filters;
	}

	public String getCustomLogoUrl() {
		return customLogoUrl;
	}

	public void setCustomLogoUrl(String customLogoUrl) {
		this.customLogoUrl = customLogoUrl;
	}

	public DecoratorConfig[] getDecorators() {
		return decorators;
	}

	public void setDecorators(DecoratorConfig[] decorators) {
		this.decorators = decorators;
	}

	public boolean isShowOnlineHelp() {
		return showOnlineHelp;
	}

	public void setShowOnlineHelp(boolean showOnlineHelp) {
		this.showOnlineHelp = showOnlineHelp;
	}

	public ThresholdGraphColor[] getThresholdGraphColors() {
		return thresholdGraphColors;
	}

	public void setThresholdGraphColors(ThresholdGraphColor[] thresholdGraphColors) {
		this.thresholdGraphColors = thresholdGraphColors;
	}
}

