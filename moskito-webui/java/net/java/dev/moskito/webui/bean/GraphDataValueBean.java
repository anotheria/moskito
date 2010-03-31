package net.java.dev.moskito.webui.bean;

public class GraphDataValueBean {
	private String name;
	private String value;
	
	public GraphDataValueBean(String aName, String aValue){
		name = aName;
		value = aValue;
	}
	
	public String getName(){
		return name;
	}
	
	public String getValue(){
		return value;
	}
	
	@Override public String toString(){
		return getJsValue();
	}
	
	public String getJsValue(){
		return "['"+name+"',"+value+"]";
	}
}
