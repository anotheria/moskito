package net.java.dev.moskito.webui.bean;

import java.net.URLEncoder;

/**
 * This bean represent a single traced call in an list view. It is usually used to show single calls in a journey.
 * @author lrosenberg
 *
 */
public class TracedCallListItemBean {
	/**
	 * Name of the call. 
	 */
	private String name;
	/**
	 * Timestamp of the call execution.
	 */
	private String date;
	
	/**
	 * Number of executed steps in this call.
	 * @return
	 */
	private int containedSteps;
	
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	
	}

	@SuppressWarnings("deprecation")
	public String getNameEncoded(){
		return URLEncoder.encode(getName());
	}
	public int getContainedSteps() {
		return containedSteps;
	}
	public void setContainedSteps(int containedSteps) {
		this.containedSteps = containedSteps;
	}
	
	
}
