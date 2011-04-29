package net.java.dev.moskito.core.treshold;

import java.util.List;

import net.java.dev.moskito.core.dynamic.OnDemandStatsProducer;
import net.java.dev.moskito.core.helper.TieableDefinition;
import net.java.dev.moskito.core.helper.TieableRepository;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;

public class ThresholdRepository extends TieableRepository<Threshold> {
	
	private static ThresholdRepository INSTANCE = new ThresholdRepository();
	
	
	
	private ThresholdRepository(){
	}
	
	public static ThresholdRepository getInstance(){ return INSTANCE; }

	protected boolean tie(Threshold threshold, IStatsProducer producer){
		ThresholdDefinition definition = threshold.getDefinition();
		IStats target = null;
		for (IStats s : producer.getStats()){
			if (s.getName().equals(definition.getStatName())){
				target = s;
				break;
			}
		}
		
		if (target==null){
			if (producer instanceof OnDemandStatsProducer){
				addToAutoTie(threshold, producer);
			}else{
				throw new IllegalArgumentException("StatObject not found "+definition.getStatName()+" in "+definition);
			}
		}

		threshold.tieToStats(target);
		
		//if (definition.getIntervalName()!=null){
		//	Interval interval = IntervalRegistry.getInstance().getInterval(definition.getIntervalName());
		//}
		
		return true;
		
	}
	

	
	public Threshold createThreshold(ThresholdDefinition definition){
		return createTieable(definition);
	}

	public ThresholdStatus getWorstStatus(){
		ThresholdStatus ret = ThresholdStatus.GREEN;
		for (Threshold t : getThresholds()){
			if (t.getStatus().overrules(ret))
				ret = t.getStatus();
		}
		return ret;
	}
	
	public List<Threshold> getThresholds(){
		return getTieables();
	}
	
	protected Threshold create(TieableDefinition def){
		return new Threshold((ThresholdDefinition)def);
	}
	
	
}
