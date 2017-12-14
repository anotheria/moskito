package net.anotheria.moskito.webui.shared.api;

import net.anotheria.moskito.webui.journey.api.TagEntryAO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 11.06.17 20:26
 */
public class CaughtErrorAO implements Serializable {
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 550860880249286693L;

	private long timestamp;
	private String date;
	private String message;
	private List<StackTraceElement> elements;
	private List<TagEntryAO> tags;

	public CaughtErrorAO() {
		tags = new ArrayList<>();
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<StackTraceElement> getElements() {
		return elements;
	}

	public void setElements(List<StackTraceElement> elements) {
		this.elements = elements;
	}

	public List<TagEntryAO> getTags() {
		return tags;
	}

	public void setTags(List<TagEntryAO> tags) {
		this.tags = tags;
	}

	@Override
	public String toString(){
		return getDate()+", "+getMessage();
	}
}
