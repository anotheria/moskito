package net.java.dev.moskito.core.treshold;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import net.java.dev.moskito.core.producers.IStats;


public class Threshold {
	
	private static Logger log = Logger.getLogger(Threshold.class);
	
	private ThresholdStatus status;
	private ThresholdDefinition definition;
	private List<ThresholdConditionGuard> guards;

	private IStats stats;
	private String lastValue;
	
	public Threshold(ThresholdDefinition aDefinition, IStats aStats){
		definition = aDefinition;
		status = ThresholdStatus.OFF;
		stats = aStats;
		lastValue = "none yet";
		guards = new ArrayList<ThresholdConditionGuard>();
	}
	
	public void addGuard(ThresholdConditionGuard guard){
		guards.add(guard);
	}

	public ThresholdStatus getStatus() {
		return status;
	}

	public ThresholdDefinition getDefinition() {
		return definition;
	}

	public IStats getStats() {
		return stats;
	}

	public String getLastValue() {
		return lastValue;
	}
	
	public void update(){
		System.out.println("=== Started update with "+status);
		String previousValue = lastValue;
		lastValue = stats.getValueByNameAsString(definition.getValueName(), definition.getIntervalName(), definition.getTimeUnit());
		ThresholdStatus futureStatus = status == ThresholdStatus.OFF ? ThresholdStatus.OFF : ThresholdStatus.GREEN;
		for (ThresholdConditionGuard guard : guards){
			try{
				ThresholdStatus newStatus = guard.getNewStatusOnUpdate(previousValue, lastValue, status, this);
				if (newStatus.overrules(futureStatus)){
					futureStatus = newStatus;
				}
			}catch(Exception e){
				log.warn("Error in ThresholdConditionGuard: "+guard+" in getNewStatusOnUpdate("+previousValue+", "+lastValue+", "+status+", "+this, e);
			}
		}
		
		//TODO generate alert.
		status = futureStatus;
		System.out.println("=== Finished update with "+status);
	}

	public String getName(){
		return getDefinition().getName();
	}
	 
}
