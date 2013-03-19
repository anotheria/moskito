package net.anotheria.moskito.central.config;

import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 15.03.13 23:21
 */
@ConfigureMe
public class StorageConfigEntry {
	@Configure
	private String name;

	@Configure
	private String clazz;

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

