package net.anotheria.moskito.core.registry;

import net.anotheria.moskito.core.producers.IStatsProducer;

/**
 * This class is a wrapper which protects the way the registry stores it references to the producers from possible customers.
 * The idea is to be able to create weak references in the future, to prevent memory leaks if someone is registering new beans within the registry.
 * @author lrosenberg
 *
 */
public class ProducerReference {
	/**
	 * Link to the producer. TODO in the future it should/could be a WeakReference.
	 */
	private final IStatsProducer target;
	
	public ProducerReference(IStatsProducer aProducer){
		target = aProducer;
	}

	/**
	 * Returns the underlying stats producer.
	 * @return
	 */
	public IStatsProducer get(){
		return target;
	}
	
	@Override public String toString(){
		return "PR->"+get();
	}
}
