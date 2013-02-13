package net.anotheria.moskito.webui.accumulators.api;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * This bean contains a list of values for one timestamp.
 * @author lrosenberg
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AccumulatedValueAO {
	/**
	 * Values aka data for graphs.
	 */
	@XmlElement
	private List<String> values;
	/**
	 * Timestamp.
	 */
	@XmlElement
	private String timestamp;
	
	/**
	 * Iso timestamp
	 * 
	 */
	@XmlElement
	private String isoTimestamp;
	
	public AccumulatedValueAO(String aTimestamp){
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
