package net.java.dev.moskito.core.helper;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import net.java.dev.moskito.core.stats.IIntervalListener;
import net.java.dev.moskito.core.stats.Interval;

import org.apache.log4j.Logger;

/**
 * Helper class to connect not stats producers to interval updates.
 * @author lrosenberg
 *
 */
public class IntervalListener implements IIntervalListener{
	/**
	 * Logger.
	 */
	private static Logger log = Logger.getLogger(IntervalListener.class);
	/**
	 * Target classes.
	 */
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
	
	public void addTieable(Tieable toAdd){
		updateables.add(toAdd);
	}

    public void removeTieable(Tieable toRemove){
        updateables.remove(toRemove);
    }
	
	public void addTieableAutoTieWrapper(AutoTieWrapper toAdd){
		updateables.add(toAdd);
	}

}
