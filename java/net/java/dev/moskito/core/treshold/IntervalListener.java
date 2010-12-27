package net.java.dev.moskito.core.treshold;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;

import net.java.dev.moskito.core.stats.IIntervalListener;
import net.java.dev.moskito.core.stats.Interval;

public class IntervalListener implements IIntervalListener{

	private static Logger log = Logger.getLogger(IntervalListener.class);
	
	private List<IntervalUpdateable> updateables = new CopyOnWriteArrayList<IntervalUpdateable>();
	
	@Override
	public void intervalUpdated(Interval aCaller) {
		for (IntervalUpdateable u : updateables){
			try{
				u.update();
			}catch(Exception e){
				log.warn("IntervalUpdateable update failed, skipping ", e);
			}
		}
	}
	
	public void addThreshold(Threshold toAdd){
		updateables.add(toAdd);
	}
	
	public void addThresholdAutoTieWrapper(ThresholdAutoTieWrapper toAdd){
		updateables.add(toAdd);
	}

}
