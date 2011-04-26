package net.java.dev.moskito.webui.bean;


/**
 * This bean holds the information about a configured accumulator.
 * It doesn't contain actual values. 
 * @author lrosenberg
 *
 */
public class AccumulatorInfoBean {
	private String id;
	private String name;
	private String path;
	private int numberOfValues;
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
}
