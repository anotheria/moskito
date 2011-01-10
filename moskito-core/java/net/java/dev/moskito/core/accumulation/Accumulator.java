package net.java.dev.moskito.core.accumulation;

import java.util.ArrayList;
import java.util.List;

public class Accumulator {
	private AccumulatorDefinition definition;
	
	private List<AccumulatedValue> values;
	
	public Accumulator(AccumulatorDefinition aDefinition){
		definition = aDefinition;
		values = new ArrayList<AccumulatedValue>();
	}
	
	public void addValue(AccumulatedValue value){
		values.add(value);
		if (values.size()>definition.getMaxAmountOfAccumulatedItems()){
			values = values.subList(definition.getMaxAmountOfAccumulatedItems()-definition.getAccumulationAmount()+1, values.size());
		}
	}
	
	public void addValue(String aValue){
		addValue(new AccumulatedValue(aValue));
	}
	
	public List<AccumulatedValue> getValues(){
		return values;
	}
}
