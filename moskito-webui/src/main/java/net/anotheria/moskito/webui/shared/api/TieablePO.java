package net.anotheria.moskito.webui.shared.api;

import net.anotheria.maf.bean.FormBean;

import java.io.Serializable;

/**
 * Base class for tieable parameter objects.
 *
 * @author lrosenberg
 * @since 13.02.13 17:34
 */
public class TieablePO implements FormBean, Serializable {
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -8623560321475285875L;

	/**
	 * Id of the producer.
	 */
	private String producerId;
	/**
	 * Name of the value for tiing.
	 */
	private String valueName;
	/**
	 * TimeUnit if applicable.
	 */
	private String unit;
	/**
	 * Name of the tieable.
	 */
	private String name;
	/**
	 * Interval.
	 */
	private String interval;
	/**
	 * Name of the stat.
	 */
	private String statName;

	public String getProducerId() {
		return producerId;
	}

	public void setProducerId(String producerId) {
		this.producerId = producerId;
	}

	public String getValueName() {
		return valueName;
	}

	public void setValueName(String valueName) {
		this.valueName = valueName;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	public String getStatName() {
		return statName;
	}

	public void setStatName(String statName) {
		this.statName = statName;
	}

}
