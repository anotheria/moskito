package net.anotheria.moskito.webui.shared.api;

import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 11.06.17 20:26
 */
public class CaughtErrorAO {
	private long timestamp;
	private String date;
	private String message;
	private List<StackTraceElement> elements;
	private String tagLine;

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

	public String getTagLine() {
		return tagLine;
	}

	public void setTagLine(String tagLine) {
		this.tagLine = tagLine;
	}

	@Override
	public String toString(){
		return getDate()+", "+getMessage();
	}
}
