package net.anotheria.moskito.central.storage.fs;

import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

/**
 * Part of the configuration of CSVFileStorage for one producer/interval/stat combination.
 *
 * @author lrosenberg
 * @since 25.03.13 10:20
 */
@ConfigureMe
public class CSVFileStorageConfigEntry {
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

	/**
	 * Included stat names, comma separated or '*'.
	 */
	@Configure
	private String includedStats;

	/**
	 * Excluded stat names, comma separated.
	 */
	@Configure
	private String excludedStats;


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

	public String getIncludedStats() {
		return includedStats;
	}

	public void setIncludedStats(String includedStats) {
		this.includedStats = includedStats;
	}

	public String getExcludedStats() {
		return excludedStats;
	}

	public void setExcludedStats(String excludedStats) {
		this.excludedStats = excludedStats;
	}

	@Override
	public String toString() {
		return "CSVFileStorageConfigEntry{" +
				"includedIntervals='" + includedIntervals + '\'' +
				", excludedIntervals='" + excludedIntervals + '\'' +
				", includedProducers='" + includedProducers + '\'' +
				", excludedProducers='" + excludedProducers + '\'' +
				", includedStats='" + includedStats + '\'' +
				", excludedStats='" + excludedStats + '\'' +
				'}';
	}
}
