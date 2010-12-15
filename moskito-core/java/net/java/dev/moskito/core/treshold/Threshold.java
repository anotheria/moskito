package net.java.dev.moskito.core.treshold;

import net.java.dev.moskito.core.producers.IStats;


public class Threshold {
	private ThresholdStatus status;
	private ThresholdDefinition definition;

	private IStats stats;
	private String lastValue;
	
	public Threshold(ThresholdDefinition aDefinition, IStats aStats){
		definition = aDefinition;
		status = ThresholdStatus.GREEN;
		stats = aStats;
		lastValue = "none yet";
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
		lastValue = stats.getValueByNameAsString(definition.getValueName(), definition.getIntervalName(), definition.getTimeUnit());
	}

	
	 
}
