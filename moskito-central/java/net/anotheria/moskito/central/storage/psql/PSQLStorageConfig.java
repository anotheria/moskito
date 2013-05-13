package net.anotheria.moskito.central.storage.psql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.anotheria.moskito.central.storage.fs.IncludeExcludeWildcardList;
import net.anotheria.moskito.central.storage.psql.entities.FilterStatEntity;
import net.anotheria.moskito.central.storage.psql.entities.HttpSessionStatisticsEntity;
import net.anotheria.moskito.central.storage.psql.entities.MemoryStatEntity;
import net.anotheria.moskito.central.storage.psql.entities.OSStatEntity;
import net.anotheria.moskito.central.storage.psql.entities.RuntimeStatEntity;
import net.anotheria.moskito.central.storage.psql.entities.ServiceStatsEntity;
import net.anotheria.moskito.central.storage.psql.entities.StatisticsEntity;
import net.anotheria.moskito.central.storage.psql.entities.ThreadCountStatEntity;
import net.anotheria.moskito.central.storage.psql.entities.ThreadStatesStatEntity;
import net.anotheria.moskito.core.predefined.ActionStats;
import net.anotheria.moskito.core.predefined.FilterStats;
import net.anotheria.moskito.core.predefined.MemoryStats;
import net.anotheria.moskito.core.predefined.OSStats;
import net.anotheria.moskito.core.predefined.RuntimeStats;
import net.anotheria.moskito.core.predefined.ServiceStats;
import net.anotheria.moskito.core.predefined.ServletStats;
import net.anotheria.moskito.core.predefined.ThreadCountStats;
import net.anotheria.moskito.core.predefined.ThreadStateStats;

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
	private String hibernateDialect;

	/**
	 * 
	 */
	@Configure
	private PSQLStorageConfigProducerNameToEntityClassMappingEntry[] mappings;

	/**
	 * Restored config element.
	 */
	private List<PSQLStorageConfigProducerNameToEntityClassMappingIncludeExcludeElement> elements;

	/**
	 * 
	 */
	@Configure
	private PSQLStorageConfigIncludeExcludeEntry[] includeExclude;

	/**
	 * 
	 */
	private List<PSQLStorageConfigIncludeExcludeElement> includeExcludeElements;

	/**
	 * 
	 */
	private static final Map<String, Class<? extends StatisticsEntity>> predefined = new HashMap<String, Class<? extends StatisticsEntity>>();
	static {

		/*
		 * already created entity mappings list.
		 */
		predefined.put(ActionStats.class.getName(), ServiceStatsEntity.class);// --
		predefined.put(ServiceStats.class.getName(), ServiceStatsEntity.class);// --
		predefined.put(ServletStats.class.getName(), ServiceStatsEntity.class);// --
		predefined.put(FilterStats.class.getName(), FilterStatEntity.class);// --

		predefined.put(MemoryStats.class.getName(), MemoryStatEntity.class);// --
		predefined.put(OSStats.class.getName(), OSStatEntity.class);// --
		predefined.put(RuntimeStats.class.getName(), RuntimeStatEntity.class);// --
		predefined.put(ThreadCountStats.class.getName(), ThreadCountStatEntity.class);// --
		predefined.put(ThreadStateStats.class.getName(), ThreadStatesStatEntity.class);// --
		predefined.put("net.anotheria.moskito.sql.stats.QueryStringStats", ServiceStatsEntity.class);
		predefined.put("net.anotheria.moskito.sql.stats.QueryStats", FilterStatEntity.class);
		predefined.put("net.anotheria.moskito.web.session.SessionCountStats", HttpSessionStatisticsEntity.class);// --

		/*
		 * not yet created mappings list. TODO
		 */

		// predefined.put(CacheStats.class.getName(), FilterStatEntity.class);
		// predefined.put(CounterStats.class.getName(), FilterStatEntity.class);
		// predefined.put(GuestBasicPremiumStats.class.getName(),
		// FilterStatEntity.class);
		// predefined.put(MaleFemaleStats.class.getName(),
		// FilterStatEntity.class);
		// predefined.put(GenericStats.class.getName(), FilterStatEntity.class);
		// predefined.put(QueueStats.class.getName(), FilterStatEntity.class);
		// predefined.put(QueuingSystemStats.class.getName(),
		// FilterStatEntity.class);
		// predefined.put(StorageStats.class.getName(), StorageStat.class);

	}

	public PSQLStorageConfigProducerNameToEntityClassMappingEntry[] getMappings() {
		return mappings;
	}

	public void setMappings(PSQLStorageConfigProducerNameToEntityClassMappingEntry[] mappings) {
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getHibernateDialect() {
		return hibernateDialect;
	}

	public void setHibernateDialect(String hibernateDialect) {
		this.hibernateDialect = hibernateDialect;
	}

	public PSQLStorageConfigIncludeExcludeEntry[] getIncludeExclude() {
		return includeExclude;
	}

	public void setIncludeExclude(PSQLStorageConfigIncludeExcludeEntry[] includeExclude) {
		this.includeExclude = includeExclude;
	}

	@Override
	public String toString() {
		return "PSQLStorageConfig [driver=" + driver + ", url=" + url + ", userName=" + userName + ", password=" + password + ", hibernateDialect="
				+ hibernateDialect + ", mappings=" + Arrays.toString(mappings) + ", elements=" + elements + ", includeExclude="
				+ Arrays.toString(includeExclude) + ", includeExcludeElements=" + includeExcludeElements + "]";
	}

	/**
	 * 
	 */
	@AfterConfiguration
	public void afterConfig() {
		List<PSQLStorageConfigProducerNameToEntityClassMappingIncludeExcludeElement> newElements = new ArrayList<PSQLStorageConfigProducerNameToEntityClassMappingIncludeExcludeElement>();
		if (mappings != null) {
			for (PSQLStorageConfigProducerNameToEntityClassMappingEntry entry : mappings) {
				PSQLStorageConfigProducerNameToEntityClassMappingIncludeExcludeElement element = new PSQLStorageConfigProducerNameToEntityClassMappingIncludeExcludeElement(
						entry);
				newElements.add(element);
			}
			elements = newElements;
		}
		List<PSQLStorageConfigIncludeExcludeElement> newIncludeExcludeElements = new ArrayList<PSQLStorageConfigIncludeExcludeElement>();
		if (includeExclude != null) {
			for (PSQLStorageConfigIncludeExcludeEntry entry : includeExclude) {
				PSQLStorageConfigIncludeExcludeElement element = new PSQLStorageConfigIncludeExcludeElement(entry);
				newIncludeExcludeElements.add(element);
			}
			includeExcludeElements = newIncludeExcludeElements;
		}
	}

	/**
	 * Runtime used element.
	 */
	private static class PSQLStorageConfigProducerNameToEntityClassMappingIncludeExcludeElement {

		/**
		 * Include/Exclude list with producers.
		 */
		private IncludeExcludeWildcardList producers;

		/**
		 * 
		 */
		private String statEntityClassName;

		public PSQLStorageConfigProducerNameToEntityClassMappingIncludeExcludeElement(PSQLStorageConfigProducerNameToEntityClassMappingEntry entry) {
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
	 * Runtime used element.
	 */
	private static class PSQLStorageConfigIncludeExcludeElement {

		/**
		 * Include/Exclude list with producers.
		 */
		private IncludeExcludeWildcardList producers;

		/**
		 * 
		 */
		private IncludeExcludeWildcardList intervals;

		public PSQLStorageConfigIncludeExcludeElement(PSQLStorageConfigIncludeExcludeEntry entry) {
			producers = new IncludeExcludeWildcardList(entry.getIncludedProducers(), entry.getExcludedProducers());
			intervals = new IncludeExcludeWildcardList(entry.getIncludedIntervals(), entry.getExcludedIntervals());
		}

		public boolean include(String producer, String interval) {
			return producers.include(producer) && intervals.include(interval);
		}

		@Override
		public String toString() {
			return "PSQLStorageConfigIncludeExcludeElement [producers=" + producers + ", intervals=" + intervals + "]";
		}

	}

	/**
	 * 
	 * @param statClassName
	 * @param producerId
	 * @return String Entity class name
	 */
	public Class<? extends StatisticsEntity> getStatEntityClassName(String statClassName, String producerId) {

		// searching in predefined, if returned null - then search by producerId

		Class<? extends StatisticsEntity> entityClass = predefined.get(statClassName);
		if (entityClass != null) {
			return entityClass;
		}

		List<PSQLStorageConfigProducerNameToEntityClassMappingIncludeExcludeElement> listCopy = elements;
		if (listCopy == null) {
			return null;
		}
		for (PSQLStorageConfigProducerNameToEntityClassMappingIncludeExcludeElement element : listCopy) {
			if (element.include(producerId)) {
				try {
					entityClass = (Class<? extends StatisticsEntity>) Class.forName(element.getStatEntityClassName());
					return entityClass;
				} catch (ClassNotFoundException ex) {
					// do nothing
				}
			}
		}

		return null;
	}

	/**
	 * 
	 * @param producerId
	 * @param interval
	 * @return boolean
	 */
	public boolean include(String producerId, String interval) {
		if (includeExcludeElements == null) {
			return false;
		}
		for (PSQLStorageConfigIncludeExcludeElement elem : includeExcludeElements) {
			if (elem.include(producerId, interval)) {
				return true;
			}
		}
		return false;
	}

}
