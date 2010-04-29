package net.java.dev.moskito.webui.bean;

/**
 * A single name value pair in a graph.
 * @author lrosenberg.
 *
 */
public class GraphDataValueBean {
	/**
	 * Name.
	 */
	private String name;
	/**
	 * Value.
	 */
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
