package net.anotheria.moskito.core.helper;

import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;

/**
 * Wrapper to allow tieables to be tied to on demand producers. The problem with OnDemandProducers is that at the moment 
 * of the registration the stats you later may want to tie to doesn't exists. Therefore the AutoTieWrapper
 * listens to interval update events, and checks whether the appropriate stat object has been created. If so, it will tie the target
 * to the stats object and do nothing from than on.
 * @author lrosenberg
 *
 */
public class AutoTieWrapper implements IntervalUpdateable{
	
	/**
	 * The target tieable object.
	 */
	private Tieable tieable;
	/**
	 * The stats producer object.
	 */
	private IStatsProducer<? extends IStats> producer;

	public AutoTieWrapper(Tieable aTieable, IStatsProducer<? extends IStats> aProducer){
		tieable = aTieable;
		producer  = aProducer;
	}
	/**
	 * Called whenever the appropriate interval has passed and the stats should be updated. 
	 */
	public void update(){
		if (tieable.isActivated()){
			return;
		}
		
		for (IStats s : producer.getStats()){
			if (s.getName().equals(tieable.getTargetStatName())){
				tieable.tieToStats(s);
				tieable.update();
				return;
			}
		}
	}
}
