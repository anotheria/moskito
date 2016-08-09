package net.anotheria.moskito.core.helper;

import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.IProducerRegistry;
import net.anotheria.moskito.core.registry.IProducerRegistryListener;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.core.stats.impl.IntervalRegistry;
import net.anotheria.moskito.core.timing.IUpdateable;
import net.anotheria.moskito.core.timing.UpdateTriggerServiceFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;
/**
 * Basic class for repository that manages tieable objects.
 * @author lrosenberg
 *
 * @param <T>
 */
public abstract class TieableRepository<T extends Tieable, S extends IStats> implements IProducerRegistryListener<S>, IUpdateable {
	/**
	 * Interval listeners.
	 */
	private ConcurrentMap<String, IntervalListener> listeners = new ConcurrentHashMap<>();
	
	/**
	 * Reference to producer registry.
	 */
	private final IProducerRegistry registry = ProducerRegistryFactory.getProducerRegistryInstance();
	/**
	 * Logger.
	 */
	private static Logger log = LoggerFactory.getLogger(TieableRepository.class);
	/**
	 * Tieables. This map contains already tied tieables.
	 */
	private ConcurrentMap<String, T> tieables = new ConcurrentHashMap<>();
	/**
	 * The listener to the default interval.
	 */
	private final IntervalListener defaultListener = new IntervalListener();

	
	/**
	 * Threshold objects that are already created and registered but yet not tied to a concrete producer/stats object.
	 */
	private List<T> yetUntied = new CopyOnWriteArrayList<>();

	/**
	 * Map that contains names of the tieables maped by ids.
	 */
	private ConcurrentMap<String, String> id2nameMapping = new ConcurrentHashMap<>();


	public TieableRepository() {
		ProducerRegistryFactory.getProducerRegistryInstance().addListener(this);
		listeners.put("default", defaultListener);
		UpdateTriggerServiceFactory.getUpdateTriggerService().addUpdateable(this, 60);

	}
	
	protected void addUntied(T t){
		yetUntied.add(t);
	}

	protected void addToAutoTie(T tieable, IStatsProducer<?> producer) {
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
	public void notifyProducerRegistered(IStatsProducer<S> producer) {
		Iterable<T> tmpList = new ArrayList<>(yetUntied);
		for (T t : tmpList){
			if (t.getDefinition().getProducerName().equals(producer.getProducerId())){
				try{
					tie(t, producer);
				}catch(Exception e){
					log.error("notifyProducerRegistered("+producer+ ')',e );
				}
			}
		}
	}

	@Override
	public void notifyProducerUnregistered(IStatsProducer<S> producer) {
		//nothing
	}
	
	protected abstract boolean tie(T t, IStatsProducer<? extends IStats> to);
	
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
		List<T> ret = new ArrayList<>(tieables.size());
		ret.addAll(tieables.values());
		return ret;
	}
	
	protected abstract T create(TieableDefinition def);
	
	public T createTieable(TieableDefinition definition){
		T t = create(definition);
		String name = t.getName();
		int i=1;
		while( tieables.get(name)!=null){
			name = t.getName()+ '-' +(i++);
		}
		definition.setName(name);//set net name, in order to prevent name conflicts.
		tieables.put(t.getName(), t);
		attachToListener(t);

        IStatsProducer<?> producer = registry.getProducer(definition.getProducerName());
		if (producer!=null){
			tie(t, producer);
		}else{
			addUntied(t);
		}

		id2nameMapping.put(t.getId(), t.getName());

		return t;
	}

	public void removeById(String id){
		String name = id2nameMapping.get(id);
		if (name==null)
			throw new IllegalArgumentException("Id: "+id+" not found");
		removeTieable(name);
	}

	/**
     * Removes previously added tieable by name.
     * @param name name of the tieable to remove.
     */
    public void removeTieable(String name){
        T t = tieables.remove(name);
        if (t==null)
            return;
        detachFromListener(t);
        try{
            //in case its yet untied.
            yetUntied.remove(t);
        }catch(Exception e){/* ignored */}

    }

    public void removeTieable(TieableDefinition def){
        removeTieable(def.getName());
    }


	
	public T getByName(String name){
		return tieables.get(name);
	}
	
	public void update(){
		defaultListener.intervalUpdated(null);
	}


	/**
	 * Returns the tieable with the corresponding id. Usually tieables are created and addressed by name. The id
	 * of the tieable is volatile (between starts) since it depends on initialization order. However, for some use cases
	 * it is better to use the id previously obtained from a tieable listing as a name, that must be url encoded etc.
	 * @param id the internal id of the tieable.
	 * @return the Tieable with corresponding id.
	 */
	public T getById(String id){
		String name = id2nameMapping.get(id);
		if (name==null)
			throw new IllegalArgumentException("Id: "+id+" not found");
		return getByName(name);
	}

    /**
     * cleans the internal state maps.
     */
    protected void cleanup() {
		id2nameMapping.clear();
		tieables.clear();
	}


}
