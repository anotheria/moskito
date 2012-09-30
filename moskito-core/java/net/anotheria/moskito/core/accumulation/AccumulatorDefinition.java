package net.anotheria.moskito.core.accumulation;

import net.anotheria.moskito.core.helper.TieableDefinition;

/**
 * The definition of an Accumulator.
 * @author lrosenberg.
 *
 */
public class AccumulatorDefinition extends TieableDefinition {
	/**
	 * Number of values to store.
	 */
	private int accumulationAmount = 200;

	
	
	public int getMaxAmountOfAccumulatedItems(){
		return accumulationAmount + (accumulationAmount/10);
	}

	public int getAccumulationAmount() {
		return accumulationAmount;
	}

	public void setAccumulationAmount(int accumulationAmount) {
		this.accumulationAmount = accumulationAmount;
	}

}
