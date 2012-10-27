package net.anotheria.moskito.core.accumulation;

import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.helper.TieableDefinition;
import org.apache.log4j.Logger;

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

	private static Logger log = Logger.getLogger(AccumulatorDefinition.class);

	public AccumulatorDefinition(){
		try{
			accumulationAmount = MoskitoConfigurationHolder.getConfiguration().getAccumulatorsConfig().getAccumulationAmount();
		}catch(Exception e){
			accumulationAmount = 200;
			log.error("couldn't read default accumulation amount, set to "+accumulationAmount, e);

		}
	}
	
	
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
