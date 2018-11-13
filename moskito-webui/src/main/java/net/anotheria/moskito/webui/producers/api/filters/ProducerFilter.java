package net.anotheria.moskito.webui.producers.api.filters;

import net.anotheria.moskito.core.producers.IStatsProducer;

/**
 * API Level producer filter are used to remove some producers from producer list to make it less overloaded.
 *
 * @author lrosenberg
 * @since 19.04.15 23:50
 */
public interface ProducerFilter {
	/**
	 * If this method retuns true the producer is not filtered out.
	 * @param statsProducer
	 * @return
	 */
	boolean mayPass(IStatsProducer<?> statsProducer);

	/**
	 * Allows to customize filter after creation.
	 * @param parameter
	 */
	void customize(String parameter);
}
