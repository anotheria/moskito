package net.java.dev.moskito.webui.bean;

public class ProducerAndTypeBean {
	private String producerId;
	private String type;
	
	public ProducerAndTypeBean(String aProducerId, String aType){
		producerId = aProducerId;
		type = aType;
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
}
