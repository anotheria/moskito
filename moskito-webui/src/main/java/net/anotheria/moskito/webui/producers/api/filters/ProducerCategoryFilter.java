package net.anotheria.moskito.webui.producers.api.filters;

import net.anotheria.moskito.core.producers.IStatsProducer;

/**
 * Filters out all producers in a category.
 *
 * @author lrosenberg
 * @since 20.04.15 13:33
 */
public class ProducerCategoryFilter extends ProducerWithMatcherFilter {
	@Override
	protected String getTargetStringFromProducer(IStatsProducer<?> statsProducer) {
		return statsProducer.getCategory();
	}
}

