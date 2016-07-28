package net.anotheria.moskito.webui.gauges.api;

import net.anotheria.moskito.core.decorators.value.StatValueAO;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Represents a gauge with its values.
 *
 * @author lrosenberg
 * @since 23.03.15 21:40
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.PUBLIC_MEMBER)
public class GaugeAO implements Serializable{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Name of the gauge.
	 */
	private String name;
	/**
	 * Caption of the gauge box.
	 */
	private String caption;
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

	/**
	 * Zones associated with this gauge.
	 */
	private List<GaugeZoneAO> zones = new LinkedList<>();

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
		return current.getRawValue();
	}
	@XmlElement(name="min")
	public String getMinValue(){
		return min.getRawValue();
	}
	@XmlElement(name="max")
	public String getMaxValue(){
		return max.getRawValue();
	}

	@Override
	public String toString() {
		return "GaugeAO{" +
				"name="+name+
				", current=" + current +
				", min=" + min +
				", max=" + max +
				", zones= "+zones+
				'}';
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public List<GaugeZoneAO> getZones() {
		return zones;
	}

	public void setZones(List<GaugeZoneAO> zones) {
		this.zones = zones;
	}

	public void addZone(GaugeZoneAO zone){
		zones.add(zone);
	}

	public boolean getCustomZonesAvailable(){
		return zones!=null && !zones.isEmpty();
	}
}
