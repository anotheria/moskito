package net.java.dev.moskito.webui.bean;

import java.util.ArrayList;
import java.util.List;
/**
 * This bean stores traced calls.
 * @author lrosenberg
 *
 */
public class TracedCallBean {
	/**
	 * Name of the traced call.
	 */
	private String name;
	/**
	 * Creation timestamp.
	 */
	private long created;
	/**
	 * Date of the trace
	 */
	private String date;
	/**
	 * Elements of the traced call.
	 */
	private List<TraceStepBean> elements;
	
	public TracedCallBean(){
		elements = new ArrayList<TraceStepBean>();
	}
	
	public long getCreated() {
		return created;
	}
	public void setCreated(long created) {
		this.created = created;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public List<TraceStepBean> getElements() {
		return elements;
	}
	public void setElements(List<TraceStepBean> elements) {
		this.elements = elements;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void addElement(TraceStepBean element){
		elements.add(element);
	}
	
	@Override public String toString(){
		return "Name:" + name+", date: "+date+", elements: "+elements;
	}
}
