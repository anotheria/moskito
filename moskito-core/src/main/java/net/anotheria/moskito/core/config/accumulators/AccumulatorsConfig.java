package net.anotheria.moskito.core.config.accumulators;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import net.anotheria.util.StringUtils;
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
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -2579727416537790538L;
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

	/**
	 * Configured accumulators colors.
	 */
	@Configure
	private AccumulatorGraphColor[] accumulatorsColors;

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

	public AccumulatorGraphColor[] getAccumulatorsColors() {
		return accumulatorsColors;
	}

	public void setAccumulatorsColors(AccumulatorGraphColor[] accumulatorsColors) {
		this.accumulatorsColors = accumulatorsColors;
	}

	/**
	 * Returns accumulator color by given accumulator name.
	 *
	 * @param accumulatorName name of the accumulator
	 * @return accumulator color or {@code null} if accumulator with given name was not found
	 */
	public String getAccumulatorColor(final String accumulatorName) {
		if (StringUtils.isEmpty(accumulatorName))
			throw new IllegalArgumentException("accumulatorName is null");
		if (accumulatorsColors == null || accumulatorsColors.length == 0)
			return null;

		for (AccumulatorGraphColor accumulator : accumulatorsColors) {
			if (accumulatorName.equals(accumulator.getName()))
				return accumulator.getColor();
		}

		return null;
	}

	@Override public String toString(){
		return "Amount: " + accumulationAmount + ", " +
				"accumulators: " + Arrays.toString(accumulators) +
				", accumulatorSets: " + Arrays.toString(accumulatorSets) +
				", accumulatorsColors: " + Arrays.toString(accumulatorsColors);
	}
}
