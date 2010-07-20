package net.java.dev.moskito.webcontrol.ui.beans;

import java.util.ArrayList;
import java.util.List;

public class OrderedSourceAttributesBean {

	private final String sourceName;

	private boolean available = true;

	private List<AttributeBean> attributeValues = new ArrayList<AttributeBean>();

	public OrderedSourceAttributesBean(String sourceName) {
		this.sourceName = sourceName;
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setAttributeValues(List<AttributeBean> attributeValues) {
		this.attributeValues = attributeValues;
	}

	public List<AttributeBean> getAttributeValues() {
		return attributeValues;
	}

	public void addAttributeValue(AttributeBean attributeValue) {
		attributeValues.add(attributeValue);
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public boolean isAvailable() {
		return available;
	}

}
