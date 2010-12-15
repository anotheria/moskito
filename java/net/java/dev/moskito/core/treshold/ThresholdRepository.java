package net.java.dev.moskito.core.treshold;

import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.core.registry.IProducerRegistryAPI;
import net.java.dev.moskito.core.registry.ProducerRegistryAPIFactory;
import net.java.dev.moskito.core.stats.Interval;
import net.java.dev.moskito.core.stats.impl.IntervalRegistry;

public enum ThresholdRepository {
	INSTANCE;
	
	IProducerRegistryAPI registryAPI = new ProducerRegistryAPIFactory().createProducerRegistryAPI();

	public Threshold createThreshold(ThresholdDefinition definition){
		
		IStatsProducer producer = registryAPI.getProducer(definition.getProducerName());
		if (producer==null){
			throw new IllegalArgumentException("Producer not found "+definition.getProducerName()+" in "+definition);
		}
		
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
		
		if (definition.getIntervalName()!=null){
			Interval interval = IntervalRegistry.getInstance().getInterval(definition.getIntervalName());
			System.out.println(interval);
		}
		
		Threshold t = new Threshold(definition, target);
		return t;
		
		
	}
}
