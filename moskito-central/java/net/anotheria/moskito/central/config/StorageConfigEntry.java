package net.anotheria.moskito.central.config;

import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

/**
 * This part of the configuration defines a single storage.
 *
 * @author lrosenberg
 * @since 15.03.13 23:21
 */
@ConfigureMe
public class StorageConfigEntry {
	/**
	 * Name of the storage.
	 */
	@Configure
	private String name;

	/**
	 * Class that implements Storage and has to be loaded.
	 */
	@Configure
	private String clazz;

	/**
	 * Name of the configuration for the Storage instance.
	 */
	@Configure
	private String configName;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	@Override public String toString(){
		return "Name: "+getName()+", Class: "+getClazz()+", Config: "+getConfigName();
	}
}

