package net.anotheria.moskito.webui.accumulators.api;

import net.anotheria.util.BasicComparable;
import net.anotheria.util.sorter.IComparable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * This bean contains a list of values for one timestamp.
 * This is the main data part of a chart.
 * @author lrosenberg
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AccumulatedValueAO implements Serializable, IComparable<AccumulatedValueAO>{
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


	/**
	 *
	 */
	@XmlElement
	private long numericTimestamp;
	
	public AccumulatedValueAO(String aTimestamp){
		timestamp = aTimestamp;
		values = new ArrayList<String>();
	}

	public void addValue(String value) {
		values.add(value);
	}

	public void addValues(List<String> someValues){
		values.addAll(someValues);
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	@Override public String toString(){
		//backwards compatibility.
		return getJSONWithStringTimestamp();
	}

	public String getJSONWithNumericTimestamp(){
		StringBuilder ret = new StringBuilder("[");
		ret.append(numericTimestamp);
		for (String s: values)
			ret.append(',').append(s);
		ret.append(']');
		return ret.toString();
	}

	public String getJSONWithStringTimestamp(){
		StringBuilder ret = new StringBuilder("[");
		ret.append('"').append(timestamp).append('"');
		for (String s: values)
			ret.append(',').append(s);
		ret.append(']');
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

	public long getNumericTimestamp() {
		return numericTimestamp;
	}

	public void setNumericTimestamp(long numericTimestamp) {
		this.numericTimestamp = numericTimestamp;
	}

	@Override
	public int compareTo(IComparable<? extends AccumulatedValueAO> iComparable, int i) {
		return BasicComparable.compareLong(getNumericTimestamp(), ((AccumulatedValueAO)iComparable).getNumericTimestamp());
	}
}
