package net.java.dev.moskito.webcontrol.ui.beans;

import java.util.ArrayList;
import java.util.List;

public class ViewTable {

	private final String viewName;
	
	private List<String> rowNames;
	
	private List<OrderedSourceAttributesBean> values;
	
	public ViewTable(String viewName) {
		this.viewName = viewName;
	}

	public String getViewName() {
		return viewName;
	}

	public void setRowNames(List<String> rowNames) {
		this.rowNames = rowNames;
	}

	public List<String> getRowNames() {
		return rowNames;
	}

	public void setValues(List<OrderedSourceAttributesBean> values) {
		this.values = values;
	}

	public List<OrderedSourceAttributesBean> getValues() {
		return values;
	}
	
	public boolean addRowName(String rowName) throws Exception {
		if (rowNames == null) {
			rowNames = new ArrayList<String>();
		}
		return rowNames.add(rowName);
	}
	
	public boolean addValueBean(OrderedSourceAttributesBean bean) throws Exception {
		if (values == null) {
			values = new ArrayList<OrderedSourceAttributesBean>();
		}
		return values.add(bean);
	}
	
}
