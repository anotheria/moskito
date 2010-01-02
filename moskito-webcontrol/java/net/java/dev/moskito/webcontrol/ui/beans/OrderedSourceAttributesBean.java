package net.java.dev.moskito.webcontrol.ui.beans;

import java.util.ArrayList;
import java.util.List;

public class OrderedSourceAttributesBean {

	private final String sourceName;
	
	private boolean available = true;
	
	private List<String> attributeValues = new ArrayList<String>();

	public OrderedSourceAttributesBean(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setAttributeValues(List<String> attributeValues) {
		this.attributeValues = attributeValues;
	}

	public List<String> getAttributeValues() {
		return attributeValues;
	}
	
	public void addAttributeValue(String attributeValue) {
		attributeValues.add(attributeValue);
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public boolean isAvailable() {
		return available;
	}
	
}
