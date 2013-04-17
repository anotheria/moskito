package net.anotheria.moskito.central.storage.fs;

import org.configureme.annotations.AfterConfiguration;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

/**
 * FileSystemStorageConfig class.
 * 
 * @author lrosenberg
 * @since 22.03.13 14:15
 */
@ConfigureMe
public class FileSystemStorageConfig {

	/**
	 * Serializer name.
	 */
	@Configure
	private String serializer;

	/**
	 * excludeProducers.
	 */
	@Configure
	private String includeProducers = "*";

	/**
	 * excludeProducers.
	 */
	@Configure
	private String excludeProducers = "";

	/**
	 * Pattern.
	 */
	@Configure
	private String pattern = "/tmp/central/{host}/{component}/{producer}/{date}/{date}_{time}_{producer}.json";

	/**
	 * includeIntervals.
	 */
	@Configure
	private String includeIntervals = "*";

	/**
	 * excludeIntervals.
	 */
	@Configure
	private String excludeIntervals = "";

	/**
	 * intervals.
	 */
	private IncludeExcludeList intervals;

	/**
	 * producers.
	 */
	private IncludeExcludeList producers;

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getIncludeIntervals() {
		return includeIntervals;
	}

	public void setIncludeIntervals(String includeIntervals) {
		this.includeIntervals = includeIntervals;
	}

	public String getExcludeIntervals() {
		return excludeIntervals;
	}

	public void setExcludeIntervals(String excludeIntervals) {
		this.excludeIntervals = excludeIntervals;
	}

	public String getSerializer() {
		return serializer;
	}

	public void setSerializer(String serializer) {
		this.serializer = serializer;
	}

	public String getIncludeProducers() {
		return includeProducers;
	}

	public void setIncludeProducers(String includeProducers) {
		this.includeProducers = includeProducers;
	}

	public String getExcludeProducers() {
		return excludeProducers;
	}

	public void setExcludeProducers(String excludeProducers) {
		this.excludeProducers = excludeProducers;
	}

	/**
	 * afterConfiguration.
	 */
	@AfterConfiguration
	public void afterConfiguration() {
		intervals = new IncludeExcludeList(includeIntervals, excludeIntervals);
		producers = new IncludeExcludeList(includeProducers, excludeProducers);
	}

	@Override
	public String toString() {
		return "Pat: " + getPattern() + ", InclIntervals: " + getIncludeIntervals() + ", ExclIntervals: " + getExcludeIntervals() + ", Ser: "
				+ getSerializer() + ", InclProducers: " + getIncludeProducers() + ", ExclProducers: " + getExcludeProducers();
	}

	/**
	 * Checks on availability producerId and interval.
	 * 
	 * @param producerId
	 * @param intervalName
	 * @return boolean.
	 */
	public boolean include(String producerId, String intervalName) {
		if (!intervals.include(intervalName))
			return false;
		return producers.include(producerId);
	}

}
