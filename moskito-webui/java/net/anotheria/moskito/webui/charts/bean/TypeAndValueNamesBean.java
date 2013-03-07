package net.anotheria.moskito.webui.charts.bean;

import java.util.List;


/**
 * Contains list of values supported by a type (producer). Used for generic chat generation, to provide list
 * of available values per decorator to the frontend.
 */
public class TypeAndValueNamesBean {
	/**
	 * Name of the type.
	 */
	private String type;
	/**
	 * Supported value names.
	 */
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
