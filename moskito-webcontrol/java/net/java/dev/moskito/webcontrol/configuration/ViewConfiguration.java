package net.java.dev.moskito.webcontrol.configuration;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ViewConfiguration {
	private String name;
	private String containerName;
	private List<ViewField> fields;

	public ViewConfiguration(String aName, String aContainerName) {
		name = aName;
		containerName = aContainerName;
	}

	public String getName() {
		return name;
	}

	public List<ViewField> getFields() {
		if (fields == null) {
			fields = new CopyOnWriteArrayList<ViewField>();
		}
		return fields;
	}

	public void addField(ViewField aField) {
		getFields().add(aField);
	}

	public String toString() {
		return getName() + " -> " + getContainerName() + " " + getFields();
	}

	public String getContainerName() {
		return containerName;
	}

	public void setContainerName(String containerName) {
		this.containerName = containerName;
	}
	
}
