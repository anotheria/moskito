package net.anotheria.moskito.core.config.tracing;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.io.Serializable;

/**
 * Configuration for tracing.
 *
 * @author lrosenberg
 * @since 06.05.15 10:30
 */
@ConfigureMe (allfields = true)
public class TracingConfiguration implements Serializable{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1396769864705732095L;

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

	/**
	 * Shrinking strategy - how to remove obsolete traces.
	 */
	@Configure
	private ShrinkingStrategy shrinkingStrategy = ShrinkingStrategy.FIFO;

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

	@SuppressFBWarnings("EI_EXPOSE_REP")
	public String[] getTracers() {
		return tracers;
	}

	@SuppressFBWarnings("EI_EXPOSE_REP2")
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

	public ShrinkingStrategy getShrinkingStrategy() {
		return shrinkingStrategy;
	}

	public void setShrinkingStrategy(ShrinkingStrategy shrinkingStrategy) {
		this.shrinkingStrategy = shrinkingStrategy;
	}
}
