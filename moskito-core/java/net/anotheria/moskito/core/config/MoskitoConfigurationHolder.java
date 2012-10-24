package net.anotheria.moskito.core.config;

import net.anotheria.moskito.core.config.thresholds.NotificationProviderConfig;
import net.anotheria.moskito.core.treshold.alerts.notificationprovider.LogFileNotificationProvider;
import org.configureme.ConfigurationManager;

import java.util.logging.Logger;

/**
 * This class is configuration holder object for the MoskitoConfiguration. Currently the reconfiguration option of
 *
 * @author lrosenberg
 * @since 2.0
 */
public enum MoskitoConfigurationHolder {
	INSTANCE;

	private final Logger log;

	private volatile MoskitoConfiguration configuration;

	private MoskitoConfigurationHolder(){
		log = Logger.getLogger(getClass().getName());
		configuration = createDefaultConfiguration();
		try{
			//now let configuration override some config options.
			ConfigurationManager.INSTANCE.configure(configuration);
		}catch(IllegalArgumentException e){
			log.info("MoSKito configuration not found, working with default configuration, more details under http://confluence.opensource.anotheria.net/display/MSK/Config");
		}
	}

	/**
	 * Creates default configuration in case there is no moskito.json supplied with the project.
	 * We prefer to have all default configuration at one place to have a better overview, instead of having the default values in the objects themself.
	 * @return
	 */
	private static MoskitoConfiguration createDefaultConfiguration(){
		MoskitoConfiguration config = new MoskitoConfiguration();

		config.getThresholdsAlertsConfig().getAlertHistoryConfig().setMaxNumberOfItems(200);
		config.getThresholdsAlertsConfig().getAlertHistoryConfig().setToleratedNumberOfItems(220);

		NotificationProviderConfig[] providers = new NotificationProviderConfig[1];
		providers[0] = new NotificationProviderConfig();
		providers[0].setClassName(LogFileNotificationProvider.class.getName());
		providers[0].setParameter("MoskitoAlert");

		//The default size for the threadpool for alert dispatching. This threadpool is needed to prevent app from being blocked by a slow alert notification processor.
		//Default value is 1. Increase it if you have many alerts and many notification providers.
		config.getThresholdsAlertsConfig().setDispatcherThreadPoolSize(1);

		System.out.println("Config: "+config);

		return config;
	}

	/**
	 * Returns current configuration.
	 * @return
	 */
	public static MoskitoConfiguration getConfiguration(){
		return INSTANCE.configuration;
	}

	/**
	 * This method allows to set configuration from outside and is solely for testing purposes (junit). Do not use otherwise please.
	 * @param configuration
	 */
	public void setConfiguration(MoskitoConfiguration configuration){
		this.configuration = configuration;
	}

	/**
	 * Used for junit tests.
	 */
	public static void resetConfiguration(){
		INSTANCE.configuration = createDefaultConfiguration();
	}



}
