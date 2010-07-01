package net.java.dev.moskito.webcontrol.configuration;

public class SourceConfiguration {

	private String name;
	private String url;

	public SourceConfiguration(String aName, String anUrl) {
		name = aName;
		url = anUrl;
	}

	public String toString() {
		return name + " = " + url;
	}

	public String getName() {
		return name;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public boolean equals(Object o) {
		return o instanceof SourceConfiguration && name.equals(((SourceConfiguration) o).name);
	}
	
	@Override
	public int hashCode() {
		return name.hashCode();
	}

}
