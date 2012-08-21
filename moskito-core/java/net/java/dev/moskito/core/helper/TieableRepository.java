package net.java.dev.moskito.core.helper;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.core.registry.IProducerRegistry;
import net.java.dev.moskito.core.registry.IProducerRegistryListener;
import net.java.dev.moskito.core.registry.ProducerRegistryFactory;
import net.java.dev.moskito.core.stats.impl.IntervalRegistry;
import net.java.dev.moskito.core.timing.IUpdateable;
import net.java.dev.moskito.core.timing.UpdateTriggerServiceFactory;

import org.apache.log4j.Logger;
/**
 * Basic class for repository that manages tieable objects.
 * @author lrosenberg
 *
 * @param <T>
 */
public abstract class TieableRepository<T extends Tieable> implements IProducerRegistryListener, IUpdateable{
	/**
	 * Interval listeners.
	 */
	private ConcurrentMap<String, IntervalListener> listeners = new ConcurrentHashMap<String, IntervalListener>();
	
	/**
	 * Reference to producer registry.
	 */
	private final IProducerRegistry registry = ProducerRegistryFactory.getProducerRegistryInstance();
	/**
	 * Logger.
	 */
	private static Logger log = Logger.getLogger(TieableRepository.class);
	/**
	 * Tieables. This map contains already tied tieables.
	 */
	private ConcurrentMap<String, T> tieable = new ConcurrentHashMap<String, T>();
	/**
	 * The listener to the default interval.
	 */
	private final IntervalListener defaultListener = new IntervalListener();

	
	/**
	 * Threshold objects that are already created and registered but yet not tied to a concrete producer/stats object.
	 */
	private List<T> yetUntied = new CopyOnWriteArrayList<T>();


	public TieableRepository() {
		ProducerRegistryFactory.getProducerRegistryInstance().addListener(this);
		listeners.put("default", defaultListener);
		UpdateTriggerServiceFactory.getUpdateTriggerService().addUpdateable(this, 60);

	}
	
	protected void addUntied(T t){
		yetUntied.add(t);
	}

	protected void addToAutoTie(T tieable, IStatsProducer producer) {
		AutoTieWrapper wrapper = new AutoTieWrapper(tieable, producer);
		if (tieable.getDefinition().getIntervalName()!=null){
			IntervalListener listener = getListener(tieable.getDefinition().getIntervalName());
			listener.addTieableAutoTieWrapper(wrapper);
		}
	}

	private IntervalListener getListener(String intervalName){
		IntervalListener listener = listeners.get(intervalName);
		if (listener!=null)
			return listener;
		
		listener = new IntervalListener();
		IntervalListener old = listeners.putIfAbsent(intervalName, listener);
		if (old!=null)
			return old;
		IntervalRegistry.getInstance().getInterval(intervalName).addSecondaryIntervalListener(listener);
		return listener;
	}
	
	@Override
	public void notifyProducerRegistered(IStatsProducer producer) {
		ArrayList<T> tmpList = new ArrayList<T>();
		tmpList.addAll(yetUntied);
		for (T t : tmpList){
			if (t.getDefinition().getProducerName().equals(producer.getProducerId())){
				try{
					tie(t, producer);
				}catch(Exception e){
					log.error("notifyProducerRegistered("+producer+")",e );
				}
			}
		}
	}

	@Override
	public void notifyProducerUnregistered(IStatsProducer producer) {
		//nothing
	}
	
	protected abstract boolean tie(T t, IStatsProducer to);
	
	protected IProducerRegistry getRegistry(){
		return registry;
	}

    protected void detachFromListener(T t){
        if (t.getDefinition().getIntervalName()!=null){
            IntervalListener listener = getListener(t.getDefinition().getIntervalName());
            listener.removeTieable(t);
        }
    }

	protected void attachToListener(T t){
		if (t.getDefinition().getIntervalName()!=null){
			IntervalListener listener = getListener(t.getDefinition().getIntervalName());
			listener.addTieable(t);
		}
	}
	
	public List<T> getTieables() {
		ArrayList<T> ret = new ArrayList<T>(tieable.size());
		ret.addAll(tieable.values());
		return ret;
	}
	
	protected abstract T create(TieableDefinition def);
	
	public T createTieable(TieableDefinition definition){
		T t = create(definition);
		tieable.put(t.getName(), t);
		attachToListener(t);
		
		IStatsProducer producer = getRegistry().getProducer(definition.getProducerName());
		if (producer!=null){
			tie(t, producer);
		}else{
			addUntied(t);
		}
		
		return t;
	}

    /**
     * Removes previously added tieable by name.
     * @param name name of the tieable to remove.
     */
    public void removeTieable(String name){
        T t = tieable.remove(name);
        if (t==null)
            return;
        detachFromListener(t);
        try{
            //incase its yet untied.
            yetUntied.remove(t);
        }catch(Exception e){/* ignored */}

    }

    public void removeTieable(TieableDefinition def){
        removeTieable(def.getName());
    }


	
	public T getByName(String name){
		return tieable.get(name);
	}
	
	public void update(){
		defaultListener.intervalUpdated(null);
	}




}
