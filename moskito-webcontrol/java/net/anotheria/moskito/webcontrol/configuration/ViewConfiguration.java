package net.anotheria.moskito.webcontrol.configuration;

import java.util.ArrayList;
import java.util.List;

public class ViewConfiguration {

	private String name;
	private List<ViewField> fields;

	public ViewConfiguration(String aName) {
		name = aName;
	}
	
	public void setName(String aName) {
		name = aName;
	}

	public String getName() {
		return name;
	}

	public List<ViewField> getFields() {
		if (fields == null) {
			fields = new ArrayList<ViewField>();
		}
		return fields;
	}

	public void addField(ViewField aField) {
		getFields().add(aField);
	}

	public String toString() {
		return getName() + " -> " + getFields();
	}
	
	public void clear() {
		fields.clear();
	}

}
