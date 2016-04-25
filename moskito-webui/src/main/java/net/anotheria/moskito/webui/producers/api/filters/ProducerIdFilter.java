package net.anotheria.moskito.webui.producers.api.filters;

import net.anotheria.moskito.core.producers.IStatsProducer;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 20.04.15 12:30
 */
public class ProducerIdFilter extends ProducerWithMatcherFilter {
	@Override
	protected String getTargetStringFromProducer(IStatsProducer<?> statsProducer) {
		return statsProducer.getProducerId();
	}
}
