package net.anotheria.moskito.webui.journey.api;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * This bean stores a traced call.
 * @author lrosenberg
 *
 */
public class TracedCallAO implements Serializable {
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 3749038158601947821L;

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
	private List<TracedCallStepAO> elements;

	private List<TagEntryAO> tags;


	private List<TracedCallDuplicateStepsAO> duplicateSteps;
	
	public TracedCallAO(){
		elements = new ArrayList<>();
		tags = new ArrayList<>();
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
	public List<TracedCallStepAO> getElements() {
		return elements;
	}
	public void setElements(List<TracedCallStepAO> elements) {
		this.elements = elements;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public void addElement(TracedCallStepAO element){
		elements.add(element);
	}
	
	@Override public String toString(){
		return "Name:" + name+", date: "+date+", elements: "+elements;
	}
	public List<TracedCallDuplicateStepsAO> getDuplicateSteps() {
		return duplicateSteps;
	}

	public void setDuplicateSteps(List<TracedCallDuplicateStepsAO> duplicateSteps) {
		this.duplicateSteps = duplicateSteps;
	}

	public List<TagEntryAO> getTags() {
		return tags;
	}

	public void setTags(List<TagEntryAO> tags) {
		this.tags = tags;
	}
}
