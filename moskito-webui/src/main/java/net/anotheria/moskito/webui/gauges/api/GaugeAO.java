package net.anotheria.moskito.webui.gauges.api;

import net.anotheria.moskito.core.decorators.value.StatValueAO;
import net.anotheria.moskito.webui.shared.resource.adapters.StatValueAOMarshallingAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
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
@XmlAccessorType(XmlAccessType.FIELD)
public class GaugeAO implements Serializable{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1554363798887359631L;

	/**
	 * Name of the gauge.
	 */
	@XmlElement
	private String name;

	/**
	 * Caption of the gauge box.
	 */
	@XmlElement
	private String caption;

	/**
	 * Min value.
	 */
	@XmlJavaTypeAdapter(StatValueAOMarshallingAdapter.class)
	@XmlElement
	private StatValueAO min;

	/**
	 * Current value.
	 */
	@XmlJavaTypeAdapter(StatValueAOMarshallingAdapter.class)
	@XmlElement
	private StatValueAO current;

	/**
	 * Max value.
	 */
	@XmlJavaTypeAdapter(StatValueAOMarshallingAdapter.class)
	@XmlElement
	private StatValueAO max;

	/**
	 * Could all values be set?
	 */
	@XmlElement
	private boolean complete;

	/**
	 * Zones associated with this gauge.
	 */
	private List<GaugeZoneAO> zones = new LinkedList<GaugeZoneAO>();

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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public String getCurrentValue(){
		return current.getRawValue();
	}

	public String getMinValue(){
		return min.getRawValue();
	}

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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		GaugeAO gaugeAO = (GaugeAO) o;

		return name.equals(gaugeAO.name);

	}

	@Override
	public int hashCode() {
		return name.hashCode();
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
		return zones!=null && zones.size()>0;
	}
}
