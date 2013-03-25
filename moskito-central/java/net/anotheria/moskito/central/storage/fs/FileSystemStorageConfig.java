package net.anotheria.moskito.central.storage.fs;

import org.configureme.annotations.AfterConfiguration;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 22.03.13 14:15
 */
@ConfigureMe
public class FileSystemStorageConfig {
	@Configure
	private String serializer;

	@Configure
	private String includeProducers = "*";

	@Configure
	private String excludeProducers = "";

	@Configure
	private String pattern = "/tmp/central/{host}/{component}/{producer}/{date}/{date}_{time}_{producer}.json";

	@Configure
	private String includeIntervals = "*";

	@Configure
	private String excludeIntervals = "";

	private IncludeExcludeList intervals;

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

	@AfterConfiguration
	public void afterConfiguration(){
		intervals = new IncludeExcludeList(includeIntervals, excludeIntervals);
		producers = new IncludeExcludeList(includeProducers, excludeProducers);
	}


	@Override public String toString(){
		return "Pat: "+getPattern()+
				", InclIntervals: "+getIncludeIntervals()+
				", ExclIntervals: "+getExcludeIntervals()+
				", Ser: "+getSerializer()+
				", InclProducers: "+getIncludeProducers()+
				", ExclProducers: "+getExcludeProducers();
	}

	public boolean include(String producerId, String intervalName){
		if (!intervals.include(intervalName))
			return false;
		return producers.include(producerId);
	}


}
