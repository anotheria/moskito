package net.java.dev.moskito.core.treshold;

import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;

/**
 * Wrapper to allow threshold to be tied to on demand producers. The problem with OnDemandProducers is that at the moment 
 * of threshold registration the stats you later may want to tie the threshold to doesn't exists. Therefore the AutoTieWrapper
 * listens to interval update events, and checks whether the appropriate stat object has been created. If so, it will tie the threshold
 * to the stats object and do nothing from than on.
 * @author lrosenberg
 *
 */
public class ThresholdAutoTieWrapper implements IntervalUpdateable{
	
	private Threshold threshold;
	private IStatsProducer producer;
	
	public ThresholdAutoTieWrapper(Threshold aThreshold, IStatsProducer aProducer){
		threshold = aThreshold;
		producer  = aProducer;
	}
	
	public void update(){
		if (threshold.isActivated()){
			return;
		}
		
		for (IStats s : producer.getStats()){
			if (s.getName().equals(threshold.getDefinition().getStatName())){
				threshold.tieToStats(s);
				threshold.update();
				return;
			}
		}
	}
}
