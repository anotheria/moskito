package net.anotheria.moskito.webui.gauges.api;

import net.anotheria.moskito.webui.producers.api.StatValueAO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Represents a gauge with its values.
 *
 * @author lrosenberg
 * @since 23.03.15 21:40
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class GaugeAO {
	/**
	 * Name of the gauge.
	 */
	private String name;
	/**
	 * Min value.
	 */
	private StatValueAO min;
	/**
	 * Current value.
	 */
	private StatValueAO current;
	/**
	 * Max value.
	 */
	private StatValueAO max;
	/**
	 * Could all values be set?
	 */
	private boolean complete;

	public StatValueAO getCurrent() {
		return current;
	}

	public void setCurrent(StatValueAO current) {
		this.current = current;
	}

	public StatValueAO getMax() {
		return max;
	}

	public void setMax(StatValueAO max) {
		this.max = max;
	}

	public StatValueAO getMin() {
		return min;
	}

	public void setMin(StatValueAO min) {
		this.min = min;
	}

	@XmlElement(name="name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@XmlElement(name="complete")
	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	@XmlElement(name="current")
	public String getCurrentValue(){
		return current.getValue();
	}
	@XmlElement(name="min")
	public String getMinValue(){
		return min.getValue();
	}
	@XmlElement(name="max")
	public String getMaxValue(){
		return max.getValue();
	}

	@Override
	public String toString() {
		return "GaugeAO{" +
				"name="+name+
				", current=" + current +
				", min=" + min +
				", max=" + max +
				'}';
	}


}
