package net.anotheria.moskito.webui.util;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.configureme.ConfigurationManager;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

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

	/**
	 * Is authorization enabled in moskito-inspect
	 */
	private boolean authenticationEnabled = false;

	/**
	 * Authentication credentials (pair of username and password)
	 * for authorization in moskito-inspect
	 */
	private AuthCredentialsConfig[] authCredentials = new AuthCredentialsConfig[0];

	/**
	 * Authorization mechanism uses blowfish algorithm to encrypt user authorization cookie.
	 * This string is used as key for blowfish encryption.
	 */
	private String encryptionKey;

	/**
	 * List of remote instances. Remote instances are only active if mode is remote.
	 */
	private RemoteInstance[] remotes = new RemoteInstance[0];

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
		RemoteInstance[] oldRemotes = remotes;
		remotes = new RemoteInstance[oldRemotes.length+1];
		System.arraycopy(oldRemotes, 0, remotes, 0, oldRemotes.length);
		remotes[remotes.length-1] = newRemoteInstance;
	}

	public AuthCredentialsConfig[] getAuthCredentials() {
		return authCredentials;
	}

	public void setAuthCredentials(AuthCredentialsConfig[] authCredentials) {
		this.authCredentials = authCredentials;
	}

	public boolean isAuthenticationEnabled() {
		return authenticationEnabled;
	}

	public void setAuthenticationEnabled(boolean authenticationEnabled) {
		this.authenticationEnabled = authenticationEnabled;
	}

	public String getEncryptionKey() {
		return encryptionKey;
	}

	public void setEncryptionKey(String encryptionKey) {
		this.encryptionKey = encryptionKey;
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
}

