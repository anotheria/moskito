package net.anotheria.moskito.core.config.tracing;

import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

/**
 * Configuration for tracing.
 *
 * @author lrosenberg
 * @since 06.05.15 10:30
 */
@ConfigureMe (allfields = true)
public class TracingConfiguration {
	/**
	 * Completely turn tracing on/off. Default is on.
	 */
	@Configure
	private boolean tracingEnabled = true;
	/**
	 * Should traces be logged too?
	 */
	@Configure
	private boolean loggingEnabled = false;

	/**
	 * Should traces be kept in memory for inspect.
	 */
	@Configure
	private boolean inspectEnabled = true;
	/**
	 * Maximum amount of traces. To reduce amount of list operations the system will allow this amount to be overloaded by 10%.
	 * For example if you limit amount of traces to 100, the system will collect 110 and then cut it down to 100.
	 */
	@Configure
	private int maxTraces;
	/**
	 * Predefined tracers. Simply a list of producerids.
	 */
	@Configure
	private String[] tracers;

	public boolean isInspectEnabled() {
		return inspectEnabled;
	}

	public void setInspectEnabled(boolean inspectEnabled) {
		this.inspectEnabled = inspectEnabled;
	}

	public boolean isLoggingEnabled() {
		return loggingEnabled;
	}

	public void setLoggingEnabled(boolean loggingEnabled) {
		this.loggingEnabled = loggingEnabled;
	}

	public int getMaxTraces() {
		return maxTraces;
	}

	public void setMaxTraces(int maxTraces) {
		this.maxTraces = maxTraces;
	}

	public String[] getTracers() {
		return tracers;
	}

	public void setTracers(String[] tracers) {
		this.tracers = tracers;
	}

	public boolean isTracingEnabled() {
		return tracingEnabled;
	}

	public void setTracingEnabled(boolean tracingEnabled) {
		this.tracingEnabled = tracingEnabled;
	}

	public int getToleratedTracesAmount(){
		return getMaxTraces() + getMaxTraces()/10;
	}
}
