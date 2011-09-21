package net.java.dev.moskito.core.registry;

import net.java.dev.moskito.core.producers.IStatsProducer;

/**
 * This class is a wrapper which protects the way the registry stores it references to the producers from possible customers. 
 * @author lrosenberg
 *
 */
public class ProducerReference {
	private final IStatsProducer target;
	
	public ProducerReference(IStatsProducer aProducer){
		target = aProducer;
	}
	
	public IStatsProducer get(){
		return target;
	}
	
	@Override public String toString(){
		return "PR->"+get();
	}
}
