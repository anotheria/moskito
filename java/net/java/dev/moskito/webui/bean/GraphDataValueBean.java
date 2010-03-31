package net.java.dev.moskito.webui.bean;

public class GraphDataValueBean {
	private String name;
	private String value;
	
	public GraphDataValueBean(String aName, String aValue){
		name = aName;
		value = aValue;
	}
	
	@Override public String toString(){
		return getJsValue();
	}
	
	public String getJsValue(){
		return "['"+name+"',"+value+"]";
	}
}
