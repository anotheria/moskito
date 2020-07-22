package net.anotheria.moskito.core.registry.filters;

import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.IProducerFilter;

/**
 * Filter that filters producers by name.
 * For now it only supports direct matches, but in the future lookups by regexp will be supported as well.
 *
 * @author lrosenberg
 * @since 22.07.20 17:05
 */
public class ProducerNameFilter implements IProducerFilter {
	/**
	 * ProducerName to filter.
	 */
	private String producerName;

	/**
	 * Creates a new ProducerNameFilter.
	 * @param aProducerName 
	 */
	public ProducerNameFilter(String aProducerName){
		producerName = aProducerName;
	}

	@Override public String toString(){
		return "ProducerNameFilter: "+producerName;
	}

	@Override public boolean doesFit(IStatsProducer producer){
		return producerName == null ? true : producerName.equals(producer.getProducerId());
	}
}
