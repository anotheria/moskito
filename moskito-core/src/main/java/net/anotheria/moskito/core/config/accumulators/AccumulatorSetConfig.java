package net.anotheria.moskito.core.config.accumulators;

import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.util.Arrays;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 14.01.15 10:04
 */
@ConfigureMe(allfields = true)
public class AccumulatorSetConfig {
	@Configure
	private String name;

	@Configure
	private String[] accumulatorNames;

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
