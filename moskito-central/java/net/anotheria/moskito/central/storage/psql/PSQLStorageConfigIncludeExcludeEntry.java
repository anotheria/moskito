package net.anotheria.moskito.central.storage.psql;

import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

/**
 * Part of the configuration of {@link PSQLStorage} for one producer/interval
 * combination.
 * 
 * @author dagafonov
 */
@ConfigureMe
public class PSQLStorageConfigIncludeExcludeEntry {
	/**
	 * Included interval names, comma separated or '*'.
	 */
	@Configure
	private String includedIntervals;

	/**
	 * Excluded interval names, comma separated.
	 */
	@Configure
	private String excludedIntervals;

	/**
	 * Included producer names, comma separated or '*'.
	 */
	@Configure
	private String includedProducers;

	/**
	 * Excluded producer names, comma separated.
	 */
	@Configure
	private String excludedProducers;

	public String getIncludedIntervals() {
		return includedIntervals;
	}

	public void setIncludedIntervals(String includedIntervals) {
		this.includedIntervals = includedIntervals;
	}

	public String getExcludedIntervals() {
		return excludedIntervals;
	}

	public void setExcludedIntervals(String excludedIntervals) {
		this.excludedIntervals = excludedIntervals;
	}

	public String getIncludedProducers() {
		return includedProducers;
	}

	public void setIncludedProducers(String includedProducers) {
		this.includedProducers = includedProducers;
	}

	public String getExcludedProducers() {
		return excludedProducers;
	}

	public void setExcludedProducers(String excludedProducers) {
		this.excludedProducers = excludedProducers;
	}

	@Override
	public String toString() {
		return "PSQLStorageConfigIncludeExcludeEntry [includedIntervals=" + includedIntervals + ", excludedIntervals=" + excludedIntervals
				+ ", includedProducers=" + includedProducers + ", excludedProducers=" + excludedProducers + "]";
	}
}
