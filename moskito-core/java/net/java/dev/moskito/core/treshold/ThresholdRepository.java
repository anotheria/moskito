package net.java.dev.moskito.core.treshold;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;

import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.core.registry.IProducerRegistryAPI;
import net.java.dev.moskito.core.registry.IProducerRegistryListener;
import net.java.dev.moskito.core.registry.ProducerRegistryAPIFactory;
import net.java.dev.moskito.core.registry.ProducerRegistryFactory;
import net.java.dev.moskito.core.stats.Interval;
import net.java.dev.moskito.core.stats.impl.IntervalRegistry;

public enum ThresholdRepository implements IProducerRegistryListener{
	INSTANCE;
	
	private final IProducerRegistryAPI registryAPI = new ProducerRegistryAPIFactory().createProducerRegistryAPI();
	
	private ConcurrentMap<String, Threshold> thresholds = new ConcurrentHashMap<String, Threshold>();
	private ConcurrentMap<String, IntervalListener> listeners = new ConcurrentHashMap<String, IntervalListener>();
	private List<Threshold> yetUntied = new CopyOnWriteArrayList<Threshold>();
	
	private static Logger log = Logger.getLogger(ThresholdRepository.class);
	
	private ThresholdRepository(){
		ProducerRegistryFactory.getProducerRegistryInstance().addListener(this);
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
	
	private boolean tie(Threshold threshold, IStatsProducer producer){
		ThresholdDefinition definition = threshold.getDefinition();
		IStats target = null;
		for (IStats s : producer.getStats()){
			if (s.getName().equals(definition.getStatName())){
				target = s;
				break;
			}
		}
		
		if (target==null){
			throw new IllegalArgumentException("StatObject not found "+definition.getStatName()+" in "+definition);
		}

		threshold.tieToStats(target);
		
		//if (definition.getIntervalName()!=null){
		//	Interval interval = IntervalRegistry.getInstance().getInterval(definition.getIntervalName());
		//}
		
		return true;
		
	}
	
	public Threshold createThreshold(ThresholdDefinition definition){
		Threshold t = new Threshold(definition);
		thresholds.put(t.getName(), t);
		if (definition.getIntervalName()!=null){
			IntervalListener listener = getListener(definition.getIntervalName());
			listener.addThreshold(t);
		}

		IStatsProducer producer = registryAPI.getProducer(definition.getProducerName());
		if (producer!=null){
			tie(t, producer);
		}else{
			//schedule for later
			yetUntied.add(t);
		}
		
		return t;
	}

	public List<Threshold> getThresholds() {
		ArrayList<Threshold> ret = new ArrayList<Threshold>(thresholds.size());
		ret.addAll(thresholds.values());
		return ret;
	}

	@Override
	public void notifyProducerRegistered(IStatsProducer producer) {
		ArrayList<Threshold> tmpList = new ArrayList<Threshold>();
		tmpList.addAll(yetUntied);
		for (Threshold t : tmpList){
			if (t.getDefinition().getProducerName().equals(producer.getProducerId())){
				try{
					tie(t, producer);
				}catch(Exception e){
					log.error("Couldn't post-tie "+t+" to "+producer,e );
				}
			}
		}
	}

	@Override
	public void notifyProducerUnregistered(IStatsProducer producer) {
		//nothing
	}
	
	
	
	
}
