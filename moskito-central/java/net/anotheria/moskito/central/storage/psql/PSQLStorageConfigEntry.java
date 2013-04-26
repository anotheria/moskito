package net.anotheria.moskito.central.storage.psql;

import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

/**
 * 
 * @author dagafonov
 * 
 */
@ConfigureMe
public class PSQLStorageConfigEntry {

	/**
	 * Included producer names, comma separated or '*'.
	 */
	@Configure
	private String producerName;

	/**
	 * Entity class for mapping different statistics into DB.
	 */
	@Configure
	private String statEntityClass;

	public String getStatEntityClass() {
		return statEntityClass;
	}

	public void setStatEntityClass(String statEntityClass) {
		this.statEntityClass = statEntityClass;
	}

	public String getProducerName() {
		return producerName;
	}

	public void setProducerName(String producerName) {
		this.producerName = producerName;
	}

	@Override
	public String toString() {
		return "PSQLStorageConfigEntry [producerName=" + producerName + ", statEntityClass=" + statEntityClass + "]";
	}

}
