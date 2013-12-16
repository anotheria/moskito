package net.anotheria.moskito.core.config.accumulators;

import org.configureme.annotations.Configure;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 26.10.12 13:19
 */
public class AccumulatorsConfig {
	/**
	 * The default amount of accumulated values in the accumulator.
	 * This setting only applies for accumulators created AFTER the change.
	 * This setting is reconfigurable on the fly.
	 */
	@Configure private int accumulationAmount = 200;

	/**
	 * Configured Accumulators.
	 */
	@Configure private AccumulatorConfig[] accumulators;

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
}
