package net.java.dev.moskito.webui.bean;

import java.util.List;

public class TypeAndValueNamesBean {
	private String type;
	private List<String> valueNames;
	
	public TypeAndValueNamesBean(String aType, List<String> someValueNames){
		type = aType;
		valueNames = someValueNames;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public List<String> getValueNames() {
		return valueNames;
	}
	public void setValueNames(List<String> valueNames) {
		this.valueNames = valueNames;
	}
}
