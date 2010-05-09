package net.java.dev.moskito.webui.bean;

/**
 * Represents an element in the chart data element.
 * @author lrosenberg.
 *
 */
public class ChartDataEntityBean {
	/**
	 * Id of the producer.
	 */
	private String producerId;
	/**
	 * Name of the stat object in the producer.
	 */
	private String statName;
	/**
	 * Name of the stat value (avg, TT, TR etc).
	 */
	private String statValueName;
	/**
	 * The value requested value of the tupel (producerid, statname, statvalue).
	 */
	private String statValue;
	
	public String getProducerId() {
		return producerId;
	}
	public void setProducerId(String producerId) {
		this.producerId = producerId;
	}
	public String getStatName() {
		return statName;
	}
	public void setStatName(String statName) {
		this.statName = statName;
	}
	public String getStatValueName() {
		return statValueName;
	}
	public void setStatValueName(String statValueName) {
		this.statValueName = statValueName;
	}
	public String getStatValue() {
		return statValue;
	}
	public void setStatValue(String statValue) {
		this.statValue = statValue;
	}
	
	@Override public String toString(){
		return "ProducerId: "+getProducerId()+", StatName: "+getStatName()+", ValueName: "+getStatValueName()+", Value: "+getStatValue();
	}
}
