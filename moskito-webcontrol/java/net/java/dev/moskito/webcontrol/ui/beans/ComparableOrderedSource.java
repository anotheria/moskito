package net.java.dev.moskito.webcontrol.ui.beans;

import java.util.Comparator;

public class ComparableOrderedSource implements Comparator<OrderedSourceAttributesBean> {

	private int attrNumber;
	private int ordering;
	
	public ComparableOrderedSource(int attrNumber, int ordering) {
		this.attrNumber = attrNumber;
		this.ordering = ordering;
	}

	@Override
	public int compare(OrderedSourceAttributesBean o1, OrderedSourceAttributesBean o2) {
		return ordering * o1.getAttributeValues().get(attrNumber).getValue().compareTo(o2.getAttributeValues().get(attrNumber).getValue());
	}

}
