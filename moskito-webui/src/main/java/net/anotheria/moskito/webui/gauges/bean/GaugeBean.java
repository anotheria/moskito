package net.anotheria.moskito.webui.gauges.bean;

import net.anotheria.moskito.core.decorators.value.StatValueAO;
import net.anotheria.moskito.webui.gauges.api.GaugeAO;
import net.anotheria.moskito.webui.gauges.api.GaugeZoneAO;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.LinkedList;
import java.util.List;

/**
 * TODO comment this class
 *
 */
public class GaugeBean {

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
	private List<GaugeZoneAO> zones = new LinkedList<GaugeZoneAO>();
	/**
	 * Dashboards where this gauge can be added.
	 */
	private String dashboardsToAdd;

	public GaugeBean(GaugeAO gaugeAO, String dashboardsToAdd){
		this.name = gaugeAO.getName();
		this.caption = gaugeAO.getCaption();
		this.min = gaugeAO.getMin();
		this.current = gaugeAO.getCurrent();
		this.max = gaugeAO.getMax();
		this.complete = gaugeAO.isComplete();
		this.zones = gaugeAO.getZones();
		this.dashboardsToAdd = dashboardsToAdd;
	}

	public String getName() {
		return name;
	}

	public String getCaption() {
		return caption;
	}

	public String getDashboardsToAdd() {
		return dashboardsToAdd;
	}

	public StatValueAO getMin() {
		return min;
	}

	public StatValueAO getCurrent() {
		return current;
	}

	public StatValueAO getMax() {
		return max;
	}

	public boolean isComplete() {
		return complete;
	}

	public List<GaugeZoneAO> getZones() {
		return zones;
	}

	public boolean getCustomZonesAvailable(){
		return zones!=null && zones.size()>0;
	}

	public String getUrlParameter(){
		try{
			return URLEncoder.encode(name, "UTF-8");
		}catch(UnsupportedEncodingException e){
			//ignore
			return name;
		}
	}
}
