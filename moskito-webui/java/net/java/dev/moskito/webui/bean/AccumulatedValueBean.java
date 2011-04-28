package net.java.dev.moskito.webui.bean;

import java.util.ArrayList;
import java.util.List;

public class AccumulatedValueBean {
	private List<String> values;
	private String timestamp;
	
	public AccumulatedValueBean(String aTimestamp){
		timestamp = aTimestamp;
		values = new ArrayList<String>();
	}

	public void addValue(String value) {
		values.add(value);
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	public String toString(){
		StringBuilder ret = new StringBuilder("[");
		ret.append("\"").append(timestamp).append("\"");
		for (String s: values)
			ret.append(",").append(s);
		ret.append("]");
		return ret.toString();
	}
}
