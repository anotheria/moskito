package net.anotheria.moskito.core.config.accumulators;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.io.Serializable;
import java.util.Arrays;

/**
 * This is a configuration set which allows to choose multiple accumulators at once by clicking a single link.
 *
 * @author lrosenberg
 * @since 14.01.15 10:04
 */
@ConfigureMe(allfields = true)
@SuppressFBWarnings(value = {"EI_EXPOSE_REP2", "EI_EXPOSE_REP"}, justification = "This is the way configureme works, it provides beans for access")
public class AccumulatorSetConfig implements Serializable {
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Name of the set.
	 */
	@Configure
	private String name;

	/**
	 * Names of the accumulators that participate in this set.
	 */
	@Configure
	private String[] accumulatorNames;

	/**
	 * Mode of the selection (single charts or combined chart).
	 */
	@Configure
	private AccumulatorSetMode mode = AccumulatorSetMode.COMBINED;

	public String[]  getAccumulatorNames() {
		return accumulatorNames;
	}

	public void setAccumulatorNames(String[]  accumulatorNames) {
		this.accumulatorNames = accumulatorNames;
	}

	public AccumulatorSetMode getMode() {
		return mode;
	}

	public void setMode(AccumulatorSetMode mode) {
		this.mode = mode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString(){
		return "Name: "+getName()+", accumulators: "+ Arrays.toString(getAccumulatorNames());
	}
}
