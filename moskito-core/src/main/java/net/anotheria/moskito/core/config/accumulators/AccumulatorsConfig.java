package net.anotheria.moskito.core.config.accumulators;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

import java.io.Serializable;
import java.util.Arrays;

/**
 * This class configures accumulators.
 *
 * @author lrosenberg
 * @since 26.10.12 13:19
 */
@SuppressFBWarnings(value = {"EI_EXPOSE_REP2", "EI_EXPOSE_REP"}, justification = "This is the way configureme works, it provides beans for access")
@ConfigureMe
public class AccumulatorsConfig implements Serializable {
	/**
	 * The default amount of accumulated values in the accumulator.
	 * This setting only applies for accumulators created AFTER the change.
	 * This setting is reconfigurable on the fly.
	 */
	@Configure private int accumulationAmount = 200;

	/**
	 * Configured accumulators.
	 */
	@Configure private AccumulatorConfig[] accumulators;

	/**
	 * Configured accumulator sets.
	 */
	@Configure private AccumulatorSetConfig[] accumulatorSets;


	public AccumulatorConfig[] getAccumulators() {
		return accumulators;
	}

	public void setAccumulators(AccumulatorConfig[] accumulators) {
		this.accumulators = accumulators;
	}

	public int getAccumulationAmount() {
		return accumulationAmount;
	}

	public void setAccumulationAmount(int accumulationAmount) {
		this.accumulationAmount = accumulationAmount;
	}

	public AccumulatorSetConfig[] getAccumulatorSets() {
		return accumulatorSets;
	}

	public void setAccumulatorSets(AccumulatorSetConfig[] accumulatorSets) {
		this.accumulatorSets = accumulatorSets;
	}

	@Override public String toString(){
		return "Amount: "+accumulationAmount+", accumulators: "+ Arrays.toString(accumulators) + ", accumulatorSets: "+Arrays.toString(accumulatorSets);
	}
}
