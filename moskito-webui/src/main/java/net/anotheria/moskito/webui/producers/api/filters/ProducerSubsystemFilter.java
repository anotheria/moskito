package net.anotheria.moskito.webui.producers.api.filters;

import net.anotheria.moskito.core.producers.IStatsProducer;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 20.04.15 13:33
 */
public class ProducerSubsystemFilter extends ProducerWithMatcherFilter {
	@Override
	protected String getTargetStringFromProducer(IStatsProducer<?> statsProducer) {
		return statsProducer.getSubsystem();
	}
}
