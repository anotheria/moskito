package net.java.dev.moskito.webui.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * This bean contains a list of values for one timestamp.
 * @author lrosenberg
 *
 */
public class AccumulatedValueBean {
	/**
	 * Values aka data for graphs.
	 */
	private List<String> values;
	/**
	 * Timestamp.
	 */
	private String timestamp;
	
	/**
	 * Iso timestamp
	 * 
	 */
	private String isoTimestamp;
	
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
	
	@Override public String toString(){
		StringBuilder ret = new StringBuilder("[");
		ret.append("\"").append(timestamp).append("\"");
		for (String s: values)
			ret.append(",").append(s);
		ret.append("]");
		return ret.toString();
	}
	
	public String getFirstValue(){
		return values.get(0);
	}

	public String getIsoTimestamp() {
		return isoTimestamp;
	}

	public void setIsoTimestamp(String isoTimestamp) {
		this.isoTimestamp = isoTimestamp;
	}
}
