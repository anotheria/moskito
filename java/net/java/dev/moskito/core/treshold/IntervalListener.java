package net.java.dev.moskito.core.treshold;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;

import net.java.dev.moskito.core.stats.IIntervalListener;
import net.java.dev.moskito.core.stats.Interval;

public class IntervalListener implements IIntervalListener{

	private static Logger log = Logger.getLogger(IntervalListener.class);
	
	private List<Threshold> thresholds = new CopyOnWriteArrayList<Threshold>();
	
	@Override
	public void intervalUpdated(Interval aCaller) {
		for (Threshold t : thresholds){
			try{
				t.update();
			}catch(Exception e){
				log.warn("Threshold update failed, skipping ", e);
			}
		}
	}
	
	public void addThreshold(Threshold toAdd){
		thresholds.add(toAdd);
	}

}
