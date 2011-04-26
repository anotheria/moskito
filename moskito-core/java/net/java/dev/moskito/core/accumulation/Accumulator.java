package net.java.dev.moskito.core.accumulation;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import net.java.dev.moskito.core.helper.AbstractTieable;
import net.java.dev.moskito.core.helper.Tieable;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.treshold.AlertHistory;
import net.java.dev.moskito.core.treshold.ThresholdAlert;
import net.java.dev.moskito.core.treshold.ThresholdConditionGuard;
import net.java.dev.moskito.core.treshold.ThresholdStatus;

public class Accumulator extends AbstractTieable<AccumulatorDefinition> implements Tieable{
	private List<AccumulatedValue> values;
	/**
	 * Attached stats.
	 */
	private IStats stats;

	private String id;
	
	private static final AtomicInteger instanceCounter = new AtomicInteger(0);
	
	public Accumulator(AccumulatorDefinition aDefinition){
		super(aDefinition);
		values = new ArrayList<AccumulatedValue>();
		id = ""+instanceCounter.incrementAndGet();
	}
	
	public void addValue(AccumulatedValue value){
		values.add(value);
		if (values.size()>getDefinition().getMaxAmountOfAccumulatedItems()){
			values = values.subList(getDefinition().getMaxAmountOfAccumulatedItems()-getDefinition().getAccumulationAmount()+1, values.size());
		}
	}
	
	public void addValue(String aValue){
		addValue(new AccumulatedValue(aValue));
	}
	
	public List<AccumulatedValue> getValues(){
		return values;
	}

	@Override
	public void update() {
		if (!isActivated()){
			return;
		}
		
		String currentValue = stats.getValueByNameAsString(getDefinition().getValueName(), getDefinition().getIntervalName(), getDefinition().getTimeUnit());
		addValue(currentValue);
		
	}

	@Override
	public boolean isActivated(){
		return stats != null;
	}

	@Override
	public void tieToStats(IStats aStatsObject){
		stats = aStatsObject;
	}

	@Override public String toString(){
		return getId()+" "+getName()+" "+" Def: "+getDefinition()+" active: "+isActivated()+", Values: "+(values==null?"none":""+values.size());
	}
	
	public String getId(){
		return id;
	}
 

}
