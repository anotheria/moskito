package net.java.dev.moskito.webui.bean;

import java.util.ArrayList;
import java.util.List;

public class ProducerAndTypeBean {
	private String producerId;
	private String type;
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
