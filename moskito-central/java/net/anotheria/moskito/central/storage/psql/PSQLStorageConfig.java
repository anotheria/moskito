package net.anotheria.moskito.central.storage.psql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.anotheria.moskito.central.storage.fs.IncludeExcludeWildcardList;

import org.configureme.annotations.AfterConfiguration;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

/**
 * PSQLStorage config class.
 * 
 * @author dagafonov
 * 
 */
@ConfigureMe
public class PSQLStorageConfig {

	/**
	 * 
	 */
	@Configure
	private String driver;

	/**
	 * 
	 */
	@Configure
	private String url;

	/**
	 * 
	 */
	@Configure
	private String userName;

	/**
	 * 
	 */
	@Configure
	private String password;

	/**
	 * 
	 */
	@Configure
	private String persistenceUnitName;

	/**
	 * 
	 */
	@Configure
	private PSQLStorageConfigEntry[] mappings;

	/**
	 * Restored config element.
	 */
	private List<PSQLStorageConfigElement> elements;

	public PSQLStorageConfigEntry[] getMappings() {
		return mappings;
	}

	public void setMappings(PSQLStorageConfigEntry[] mappings) {
		this.mappings = mappings;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPersistenceUnitName() {
		return persistenceUnitName;
	}

	public void setPersistenceUnitName(String persistenceUnitName) {
		this.persistenceUnitName = persistenceUnitName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "PSQLStorageConfig [driver=" + driver + ", url=" + url + ", userName=" + userName + ", password=" + password
				+ ", persistenceUnitName=" + persistenceUnitName + ", entries=" + Arrays.toString(mappings) + ", elements=" + elements + "]";
	}

	/**
	 * 
	 */
	@AfterConfiguration
	public void afterConfig() {
		List<PSQLStorageConfigElement> newElements = new ArrayList<PSQLStorageConfigElement>();
		if (mappings == null) {
			return;
		}
		for (PSQLStorageConfigEntry entry : mappings) {
			PSQLStorageConfigElement element = new PSQLStorageConfigElement(entry);
			newElements.add(element);
		}
		elements = newElements;
	}

	/**
	 * Runtime used element.
	 */
	private static class PSQLStorageConfigElement {

		/**
		 * Include/Exclude list with producers.
		 */
		private IncludeExcludeWildcardList producers;

		/**
		 * 
		 */
		private String statEntityClassName;

		public PSQLStorageConfigElement(PSQLStorageConfigEntry entry) {
			producers = new IncludeExcludeWildcardList(entry.getProducerName(), "");
			statEntityClassName = entry.getStatEntityClass();
		}

		public boolean include(String producer) {
			return producers.include(producer);
		}

		@Override
		public String toString() {
			return "PSQLStorageConfigElement [producers=" + producers + "]";
		}

		public String getStatEntityClassName() {
			return statEntityClassName;
		}

	}

	/**
	 * 
	 * @param producerId
	 * @return String
	 */
	public String getStatEntityClassName(String producerId) {
		List<PSQLStorageConfigElement> listCopy = elements;
		if (elements == null) {
			return null;
		}
		for (PSQLStorageConfigElement e : listCopy) {
			if (e.include(producerId)) {
				return e.getStatEntityClassName();
			}
		}
		return null;
	}

}
