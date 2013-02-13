package net.anotheria.moskito.webui.shared.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * This bean combines the if of the producer, its type and available stats. This is used to transfer available data to the frontend
 * for dynamic charts. A collection of this bean shows all available stats in the system.
 */
public class ProducerAndTypeBean {
	/**
	 * Id of the producer.
	 */
	private String producerId;
	/**
	 * Type of the producer. The type of the producer is actually the name of its implementing class.
	 */
	private String type;
	/**
	 * Names of the stats that are collected by this producer.
	 */
	private List<String> statNames;
	
	public ProducerAndTypeBean(String aProducerId, String aType){
		producerId = aProducerId;
		type = aType;
		statNames = new ArrayList<String>();
	}
	
	public String getProducerId() {
		return producerId;
	}
	public void setProducerId(String producerId) {
		this.producerId = producerId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public List<String> getStatNames() {
		return statNames;
	}

	public void setStatNames(List<String> statNames) {
		this.statNames = statNames;
	}
	
	public void addStatName(String aName){
		statNames.add(aName);
	}
}
