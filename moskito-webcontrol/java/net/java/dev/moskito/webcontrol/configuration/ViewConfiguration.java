package net.java.dev.moskito.webcontrol.configuration;

import java.util.List;

public class ViewConfiguration {
	private String name;
	private List<ViewField> fields;
	
	public ViewConfiguration(String aName){
		name = aName;
	}
	
	public String getName(){
		return name;
	}
	
	public List<ViewField> getFields(){
		return fields;
	}
	
	public void addField(ViewField aField){
		fields.add(aField);
	}
	
	public String toString(){
		return getName()+" "+getFields();
	}
}
