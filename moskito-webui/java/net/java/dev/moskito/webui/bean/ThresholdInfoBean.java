package net.java.dev.moskito.webui.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Contains configuration information about a threshold. Used by js overlay.
 * @author lrosenberg
 *
 */
public class ThresholdInfoBean {
	/**
	 * Threshold id.
	 */
	private int id;
	/**
	 * Threshold name.
	 */
	private String name;
	/**
	 * Name of the associated producer.
	 */
	private String producerName;
	/**
	 * Name of the stat.
	 */
	private String statName;
	/**
	 * Name of the value.
	 */
	private String valueName;
	/**
	 * Name of the interval.
	 */
	private String intervalName;
	/**
	 * The description string for this threshold.
	 */
	private String descriptionString;
	/**
	 * The attached guards.
	 */
	private List<String> guards;
	
	public ThresholdInfoBean(){
		guards = new ArrayList<String>();
	}
	
	public void addGuard(String aGuard){
		guards.add(aGuard);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getProducerName() {
		return producerName;
	}

	public void setProducerName(String producerName) {
		this.producerName = producerName;
	}

	public String getStatName() {
		return statName;
	}

	public void setStatName(String statName) {
		this.statName = statName;
	}

	public String getValueName() {
		return valueName;
	}

	public void setValueName(String valueName) {
		this.valueName = valueName;
	}

	public String getIntervalName() {
		return intervalName;
	}

	public void setIntervalName(String intervalName) {
		this.intervalName = intervalName;
	}

	public String getDescriptionString() {
		return descriptionString;
	}

	public void setDescriptionString(String descriptionString) {
		this.descriptionString = descriptionString;
	}

	public List<String> getGuards() {
		return guards;
	}

	public void setGuards(List<String> guards) {
		this.guards = guards;
	}
}
