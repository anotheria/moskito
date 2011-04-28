package net.java.dev.moskito.webui.bean;

import java.util.HashMap;
import java.util.Map;

import net.anotheria.util.BasicComparable;
import net.anotheria.util.NumberUtils;
import net.anotheria.util.sorter.IComparable;

//this bean is used to consolidate different accumulators
public class AccumulatedValuesBean implements IComparable{
	private long timestamp;
	private Map<String, String> values;
	
	public AccumulatedValuesBean(long aTimestamp){
		timestamp = aTimestamp;
		values = new HashMap<String, String>();
	}
	
	public String toString(){
		return getTime() + ": "+values;
	}
	
	public void setValue(String name, String value){
		values.put(name, value);
	}
	
	public String getTime(){
		return NumberUtils.makeTimeString(timestamp);
	}

	@Override
	public int compareTo(IComparable anotherObject, int method) {
		return BasicComparable.compareLong(timestamp, ((AccumulatedValuesBean)anotherObject).timestamp);
	}
	
	public String getValue(String name){
		return values.get(name);
	}
}
