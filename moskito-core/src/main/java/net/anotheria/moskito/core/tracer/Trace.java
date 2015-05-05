package net.anotheria.moskito.core.tracer;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 05.05.15 17:54
 */
public class Trace {
	private String call;
	private long duration;
	private StackTraceElement[] elements;

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

	public StackTraceElement[] getElements() {
		return elements;
	}

	public void setElements(StackTraceElement[] elements) {
		this.elements = elements;
	}
}
