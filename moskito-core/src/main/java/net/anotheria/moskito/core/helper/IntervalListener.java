package net.anotheria.moskito.core.helper;

import net.anotheria.moskito.core.stats.IIntervalListener;
import net.anotheria.moskito.core.stats.Interval;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Helper class to connect not stats producers to interval updates.
 * @author lrosenberg
 *
 */
public class IntervalListener implements IIntervalListener {
	/**
	 * Logger.
	 */
	private static Logger log = LoggerFactory.getLogger(IntervalListener.class);
	/**
	 * Target classes.
	 */
	private Collection<IntervalUpdateable> updateables = new CopyOnWriteArrayList<>();
	
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
	
	public void addTieable(IntervalUpdateable toAdd){
		updateables.add(toAdd);
	}

    public void removeTieable(IntervalUpdateable toRemove){
        updateables.remove(toRemove);
    }
	
	public void addTieableAutoTieWrapper(IntervalUpdateable toAdd){
		updateables.add(toAdd);
	}

}
