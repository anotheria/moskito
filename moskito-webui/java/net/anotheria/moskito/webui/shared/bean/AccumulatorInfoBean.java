package net.anotheria.moskito.webui.shared.bean;

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

	/**
	 * The max number of value this accumulator will accumulate. The real number of values will be between 0 and maxNumberOfValues until the maxNumberOfValues
	 * is reached first time. Afterwards its between maxNumberOfValues and maxNumberOfValues*1.1.
	 */
	private int maxNumberOfValues;


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

	public int getMaxNumberOfValues() {
		return maxNumberOfValues;
	}

	public void setMaxNumberOfValues(int maxNumberOfValues) {
		this.maxNumberOfValues = maxNumberOfValues;
	}

	@Override
	public int compareTo(IComparable anotherObject, int method) {
		return BasicComparable.compareString(getName(), ((AccumulatorInfoBean)anotherObject).getName());
	}
}
