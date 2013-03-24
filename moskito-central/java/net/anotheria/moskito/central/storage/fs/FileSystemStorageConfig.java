package net.anotheria.moskito.central.storage.fs;

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
	private String pattern = "/tmp/central/{host}/{component}/{producer}/{date}/{date}_{time}_{producer}.json";

	@Configure
	private String serializer;

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public String getSerializer() {
		return serializer;
	}

	public void setSerializer(String serializer) {
		this.serializer = serializer;
	}

	@Override public String toString(){
		return "Pattern: "+getPattern()+", Ser: "+getSerializer();
	}
}
