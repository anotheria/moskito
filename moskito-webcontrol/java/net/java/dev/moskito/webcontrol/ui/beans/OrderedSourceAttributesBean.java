package net.java.dev.moskito.webcontrol.ui.beans;

import java.util.ArrayList;
import java.util.List;

import net.java.dev.moskito.webcontrol.guards.Condition;

public class OrderedSourceAttributesBean {

	private final String sourceName;

	private boolean available = true;

	private List<AttributeBean> attributeValues;

	public OrderedSourceAttributesBean(String sourceName) {
		this.sourceName = sourceName;
		AttributeBean source = new AttributeBean();
		source.setValue(sourceName);
		source.setColor(Condition.DEFAULT.getColor());
		addAttributeValue(source);
	}

	public String getSourceName() {
		return sourceName;
	}

	public void setAttributeValues(List<AttributeBean> attributeValues) {
		for (AttributeBean attrV : attributeValues) {
			addAttributeValue(attrV);
		}
	}

	public List<AttributeBean> getAttributeValues() {
		if (attributeValues == null) {
			attributeValues = new ArrayList<AttributeBean>();
		}
		return attributeValues;
	}

	public void addAttributeValue(AttributeBean attributeValue) {
		getAttributeValues().add(attributeValue);
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public boolean isAvailable() {
		return available;
	}

}
