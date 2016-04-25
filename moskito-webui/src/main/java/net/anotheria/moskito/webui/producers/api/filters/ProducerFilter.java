package net.anotheria.moskito.webui.producers.api.filters;

import net.anotheria.moskito.core.producers.IStatsProducer;

/**
 * API Level producer filter are used to remove some producers from producer list to make it less overloaded.
 *
 * @author lrosenberg
 * @since 19.04.15 23:50
 */
public interface ProducerFilter {
	boolean mayPass(IStatsProducer<?> statsProducer);

	void customize(String parameter);
}
