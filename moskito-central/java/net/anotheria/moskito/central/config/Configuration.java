package net.anotheria.moskito.central.config;

import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.util.Arrays;

/**
 * Configuration object for moskito central.
 *
 * @author lrosenberg
 * @since 15.03.13 23:11
 */
@ConfigureMe(name="moskito-central")
public class Configuration {
	/**
	 * Configured storages.
	 */
	@Configure
	private StorageConfigEntry[] storages;

	public StorageConfigEntry[] getStorages() {
		return storages;
	}

	public void setStorages(StorageConfigEntry[] storages) {
		this.storages = storages;
	}

	@Override public String toString(){
		return "{ storages: "+ Arrays.toString(storages)+" }";
	}

}
