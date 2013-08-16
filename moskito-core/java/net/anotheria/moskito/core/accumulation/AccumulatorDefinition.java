package net.anotheria.moskito.core.accumulation;

import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.helper.TieableDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The definition of an Accumulator.
 * @author lrosenberg.
 *
 */
public class AccumulatorDefinition extends TieableDefinition {
	/**
	 * Number of values to store.
	 */
	private int accumulationAmount;

	/**
	 * Logger.
	 */
	private static Logger log = LoggerFactory.getLogger(AccumulatorDefinition.class);

	/**
	 * Creates a new AccumulatorDefinition.
	 */
	public AccumulatorDefinition(){
		try{
			accumulationAmount = MoskitoConfigurationHolder.getConfiguration().getAccumulatorsConfig().getAccumulationAmount();
		}catch(Exception e){
			accumulationAmount = 200;
			log.error("couldn't read default accumulation amount, set to "+accumulationAmount, e);

		}
	}


	/**
	 * Returns the max amount of accumulated items. The max amount of accumulated items is a bit larger than the desired amount to reduce the amount of sublist operations.
	 * @return
	 */
	public int getMaxAmountOfAccumulatedItems(){
		return getAccumulationAmount() + (getAccumulationAmount()/10);
	}

	public int getAccumulationAmount() {
		return accumulationAmount;
	}

	public void setAccumulationAmount(int accumulationAmount) {
		this.accumulationAmount = accumulationAmount;
	}

}
