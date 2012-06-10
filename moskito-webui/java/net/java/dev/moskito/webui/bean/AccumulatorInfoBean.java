package net.java.dev.moskito.webui.bean;

import net.anotheria.util.BasicComparable;
import net.anotheria.util.sorter.IComparable;


/**
 * This bean holds the information about a configured accumulator.
 * It doesn't contain actual values. 
 * @author lrosenberg
 *
 */
public class AccumulatorInfoBean implements IComparable{
	/**
	 * If of the accumulator.
	 */
	private String id;
	/**
	 * Name of the accumulator.
	 */
	private String name;
	/**
	 * Path to the stat values.
	 */
	private String path;
	/**
	 * Number of accumulated values.
	 */
	private int numberOfValues;
	/**
	 * The timestamp from last value.
	 */
	private String lastValueTimestamp;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public int getNumberOfValues() {
		return numberOfValues;
	}
	public void setNumberOfValues(int numberOfValues) {
		this.numberOfValues = numberOfValues;
	}
	public String getLastValueTimestamp() {
		return lastValueTimestamp;
	}
	public void setLastValueTimestamp(String lastValueTimestamp) {
		this.lastValueTimestamp = lastValueTimestamp;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	@Override
	public int compareTo(IComparable anotherObject, int method) {
		return BasicComparable.compareString(getName(), ((AccumulatorInfoBean)anotherObject).getName());
	}
}
