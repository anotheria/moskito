package net.java.dev.moskito.webui.bean;

import java.util.ArrayList;
import java.util.List;

public class GraphDataBean {
	private String caption;
	private String jsVariableName;
	private List<GraphDataValueBean> values;
	
	public GraphDataBean(String aJsVariableName, String aCaption){
		caption = aCaption;
		jsVariableName = aJsVariableName;
		values = new ArrayList<GraphDataValueBean>();
	}
	
	public void addValue(GraphDataValueBean value){
		values.add(value);
	}
	
	public List<GraphDataValueBean> getValues(){
		return values;
	}
	
	public String getCaption(){
		return caption;
	}
	
	public String getJsVariableName(){
		return jsVariableName;
	}
	
	public String getJsArrayValue(){
		StringBuilder ret = new StringBuilder();
		ret.append('[');
		for (GraphDataValueBean value : values){
			if (ret.length()>1)
				ret.append(',');
			ret.append(value.getJsValue());
		}
		ret.append(']');
		return ret.toString();
	}
}
