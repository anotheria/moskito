package net.anotheria.moskito.central;

import net.anotheria.moskito.central.config.Configuration;
import net.anotheria.moskito.central.config.StorageConfigEntry;
import net.anotheria.moskito.central.storage.Storage;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Main class of the central.
 *
 * @author lrosenberg
 * @since 15.03.13 23:10
 */
public class Central {

	/**
	 * Configured and therefore active storages.
	 */
	private ConcurrentMap<String, Storage> storages = new ConcurrentHashMap<String, Storage>();
	/**
	 * List used for faster iteration for snapshot delivery.
	 */
	private List<Storage> cachedList = new CopyOnWriteArrayList<Storage>();

	/**
	 * Configuration.
	 */
	private Configuration configuration;

	/**
	 * Log.
	 */
	private static Logger log = Logger.getLogger(Central.class);

	/**
	 * Returns the singleton instance.
	 * @return
	 */
	public static Central getInstance(){
		return CentralInstanceHolder.instance;
	}

	/**
	 * Returns a specially configured instance. For unit tests.
	 * @param config
	 * @return
	 */
	public static Central getConfiguredInstance(Configuration config){
		Central instance = new Central();
		instance.setConfiguration(config);
		instance.setup();
		return instance;
	}

	/**
	 * This method should only be called once at a time. Therefore we do not synchronize it, cause it should only be
	 * called on instantiation or in a test.
	 */
	private void setup(){
		storages.clear();
		cachedList.clear();
		for (StorageConfigEntry storageConfigEntry : configuration.getStorages()){
			try{
				System.out.println("trying "+storageConfigEntry);
				Storage storage = Storage.class.cast(Class.forName(storageConfigEntry.getClazz()).newInstance());
				try{
					storage.configure(storageConfigEntry.getConfigName());
					storages.put(storageConfigEntry.getName(), storage);
					cachedList.add(storage);
				}catch(Exception e){
					log.warn("Storage "+storage+" for "+storageConfigEntry+" couldn't be configured properly.");
				}

			}catch(ClassNotFoundException cnf){
				log.warn("Couldn't instantiate StorageConfigEntry "+storageConfigEntry+" due ",cnf);
			} catch (InstantiationException e) {
				log.warn("Couldn't instantiate StorageConfigEntry "+storageConfigEntry+" due ",e);
			} catch (IllegalAccessException e) {
				log.warn("Couldn't instantiate StorageConfigEntry "+storageConfigEntry+" due ",e);
			}

		}
	}


	public void processIncomingSnapshot(Snapshot snapshot){
		for (Storage s : cachedList){
			try{
				s.processSnapshot(snapshot);
			}catch(Exception any){
				log.warn("Exception caught during snapshot processing in storage "+s+", snapshot: "+snapshot, any);
			}
		}
	}

	private void setConfiguration(Configuration aConfiguration){
		configuration = aConfiguration;
	}


	private static class CentralInstanceHolder{
		static final Central instance = new Central();
	}


}
