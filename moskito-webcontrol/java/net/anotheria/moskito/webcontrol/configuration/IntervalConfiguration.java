package net.anotheria.moskito.webcontrol.configuration;

public class IntervalConfiguration {

	private String name;
	private String containerName;
	
	public IntervalConfiguration(String name, String containerName) {
		this.name = name;
		this.containerName = containerName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContainerName() {
		return containerName;
	}

	public void setContainerName(String containerName) {
		this.containerName = containerName;
	}

}
