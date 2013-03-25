package net.anotheria.moskito.central.storage.fs;

import org.configureme.annotations.AfterConfiguration;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 25.03.13 10:00
 */
@ConfigureMe
public class CSVFileStorageConfig {
	@Configure private CSVFileStorageConfigEntry[] entries;

	@Configure
	private String pattern;

	private List<CSVFileStorageConfigElement> elements;

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

	@Configure
	private String includeIntervals = "*";

	@Configure
	private String excludeIntervals = "";

	private IncludeExcludeList intervals;


	public CSVFileStorageConfigEntry[] getEntries() {
		return entries;
	}

	public void setEntries(CSVFileStorageConfigEntry[] entries) {
		this.entries = entries;
	}

	@Override public String toString(){
		return "Pattern: "+getPattern()+", Entries: "+ Arrays.toString(entries)+
				", InclIntervals: "+includeIntervals+
				", ExclIntervals: "+excludeIntervals;

	}

	public boolean include(String producer, String stat, String interval){
		List<CSVFileStorageConfigElement> listCopy = elements;
		if (listCopy==null)
			return false;
		for (CSVFileStorageConfigElement e : listCopy){
			if (e.include(producer, stat, interval))
				return true;

		}
		return false;
	}

	@AfterConfiguration
	public void afterConfig(){
		IncludeExcludeList intervals = new IncludeExcludeList(includeIntervals, excludeIntervals);
		List<CSVFileStorageConfigElement> newElements = new ArrayList<CSVFileStorageConfigElement>();
		for (CSVFileStorageConfigEntry entry : entries){
			CSVFileStorageConfigElement element = new CSVFileStorageConfigElement(entry, intervals);
			newElements.add(element);
		}
		System.out.println("NewElementsList "+newElements);
		elements = newElements;

	}

	private static class CSVFileStorageConfigElement{

		private IncludeExcludeList intervals;
		private IncludeExcludeList producers;
		private IncludeExcludeList stats;

		public CSVFileStorageConfigElement(CSVFileStorageConfigEntry entry, IncludeExcludeList outerIntervals){
			stats = new IncludeExcludeList(entry.getIncludedStats(), entry.getExcludedStats());
			producers = new IncludeExcludeList(entry.getIncludedProducers(), entry.getExcludedProducers());
			if ((entry.getIncludedIntervals()==null || entry.getIncludedIntervals().length()==0) &&
				(entry.getExcludedIntervals()==null || entry.getExcludedIntervals().length()==0)){
				intervals = outerIntervals;
			}else{
				intervals = new IncludeExcludeList(entry.getIncludedIntervals(), entry.getExcludedIntervals());
			}
		}

		public boolean include(String producer, String stat, String interval){
			return producers.include(producer) &&
					stats.include(stat) && intervals.include(interval);
		}

		@Override
		public String toString() {
			return "CSVFileStorageConfigElement{" +
					"intervals=" + intervals +
					", producers=" + producers +
					", stats=" + stats +
					'}';
		}
	}
}
