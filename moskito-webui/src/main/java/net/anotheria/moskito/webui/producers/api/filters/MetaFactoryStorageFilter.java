package net.anotheria.moskito.webui.producers.api.filters;

import net.anotheria.moskito.core.producers.IStatsProducer;

/**
 * Filters out MetaFactory related storages.
 *
 * @author lrosenberg
 * @since 21.10.20 00:24
 */
public class MetaFactoryStorageFilter implements ProducerFilter {

	private static final String[] NAMES = new String[]{
			"mf-aliases",
			"mf-factories",
			"mf-factoryClasses",
			"mf-instances"
	};

	private static final String PREFIX = "mf-";

	@Override
	public boolean mayPass(IStatsProducer<?> statsProducer) {
		String producerId = statsProducer.getProducerId();
		if (!producerId.startsWith(PREFIX))
			return true;
		for (String name : NAMES){
			if (producerId.startsWith(name))
				return false;
		}
		return true;
	}

	@Override
	public void customize(String parameter) {
		
	}
}
