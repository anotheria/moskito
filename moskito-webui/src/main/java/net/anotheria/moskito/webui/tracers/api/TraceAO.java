package net.anotheria.moskito.webui.tracers.api;

import java.io.Serializable;
import java.util.List;

/**
 * This class represents a single trace - a call through the tracer.
 *
 * @author lrosenberg
 * @since 05.05.15 00:43
 */
public class TraceAO implements Serializable {
	private String call;
	private List<StackTraceElement> elements;
	private long duration;

	public String getCall() {
		return call;
	}

	public void setCall(String call) {
		this.call = call;
	}

	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public List<StackTraceElement> getElements() {
		return elements;
	}

	public void setElements(List<StackTraceElement> elements) {
		this.elements = elements;
	}

	public int getElementCount(){
		return elements == null ? 0 : elements.size();
	}
}
