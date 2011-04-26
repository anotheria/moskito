package net.java.dev.moskito.core.accumulation;

import net.java.dev.moskito.core.helper.TieableDefinition;

public class AccumulatorDefinition extends TieableDefinition{
	
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
