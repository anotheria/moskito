package net.anotheria.moskito.core.treshold.alerts;

import net.anotheria.moskito.core.config.MoskitoConfiguration;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.thresholds.NotificationProviderConfig;
import net.anotheria.moskito.core.treshold.ThresholdStatus;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 22.10.12 11:22
 */
public enum AlertDispatcher {
	/**
	 * Singleton instance.
	 */
	INSTANCE;

	private List<NotificationProviderWrapper> providers;

	private ExecutorService changeExecutor;

	private final AtomicInteger threadCounter = new AtomicInteger(0);

	private static final Logger log = Logger.getLogger(AlertDispatcher.class);

	private AlertDispatcher(){
		reset();
	}

	/**
	 * Resets and effectively restarts the dispatcher. This method is only for unit tests.
	 */
	void reset(){
		MoskitoConfiguration config = MoskitoConfigurationHolder.INSTANCE.getConfiguration();
		if (changeExecutor!=null){
			try{
				changeExecutor.shutdownNow();
			}catch(Exception ignored){}
		}
		changeExecutor = Executors.newFixedThreadPool(config.getThresholdsAlertsConfig().getDispatcherThreadPoolSize(), new ThreadFactory() {
			@Override
			public Thread newThread(Runnable r) {
				Thread t = new Thread(r, "AlertDispatcherThread-"+threadCounter.getAndIncrement());
				t.setDaemon(true);
				return t;
			}
		});

		//prepare providers
		providers = new CopyOnWriteArrayList<NotificationProviderWrapper>();
		for (NotificationProviderConfig providerDef : config.getThresholdsAlertsConfig().getNotificationProviders()){
			try{
				NotificationProvider provider = (NotificationProvider)Class.forName(providerDef.getClassName()).newInstance();
				provider.configure(providerDef.getParameter());
				providers.add(new NotificationProviderWrapper(provider, ThresholdStatus.valueOf(providerDef.getGuardedStatus())));
			}catch(Exception any){
				if (providerDef==null){
					Logger.getLogger(AlertDispatcher.class).error("Couldn't instantiate notification notificationprovider of class due to nullity of config" + providerDef + ", check configuration.", any);
				}else{
					Logger.getLogger(AlertDispatcher.class).error("Couldn't instantiate notification notificationprovider of class " + providerDef.getClassName() + ", check configuration.", any);
				}

			}

		}

	}

	/**
	 * Dispatch alert to the notification providers via
	 * @param alert
	 */
	public void dispatchAlert(final ThresholdAlert alert){
		//first add alert to the history.
		//TODO this is maybe a little hacky, since we simply transfered AlertHistory from
		//Threshold to the dispatcher. Maybe it is better way to handle it like a preconfigured notification provider.
		AlertHistory.INSTANCE.addAlert(alert);
		//now dispatch alert
		changeExecutor.execute(new Runnable(){
			public void run(){
				for (NotificationProviderWrapper wrapper : providers){
					try{
						if (alert.getNewStatus().overrulesOrEqual(wrapper.getStatus()) || alert.getOldStatus().overrulesOrEqual(wrapper.getStatus())){
							wrapper.getProvider().onNewAlert(alert);
						}
					}catch(Exception e){
						log.error("Couldn't deliver notification over notificationprovider "+wrapper.getProvider()+", due" ,e);
					}
				}
			}
		});
	}

	private static class NotificationProviderWrapper {
		private ThresholdStatus status;
		private NotificationProvider provider;

		NotificationProviderWrapper(NotificationProvider aProvider, ThresholdStatus aStatus){
			provider = aProvider;
			status = aStatus;
		}

		public ThresholdStatus getStatus() {
			return status;
		}

		public NotificationProvider getProvider() {
			return provider;
		}

		@Override public String toString(){
			return status+" "+provider;
		}
	}
}
