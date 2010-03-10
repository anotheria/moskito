package net.java.dev.moskito.webui.bean;

import java.io.Serializable;

public class ProducerBeanVisibilityType implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4923203080678542926L;
	
	public static String SHOW = "SHOW";
	public static String HIDE = "HIDE";
	
	
	private String visibility;
	
	public ProducerBeanVisibilityType(){
		this("SHOW");
	}
	
	public ProducerBeanVisibilityType(String visibilityParam) {
		this.visibility = visibilityParam;
	}

	public String getVisibility() {
		return visibility;
	}

	public void setVisibility(String visibility) {
		this.visibility = visibility;
	}
	
	
}
