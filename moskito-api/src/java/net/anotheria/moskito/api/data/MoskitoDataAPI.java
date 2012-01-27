package net.anotheria.moskito.api.data;

import java.util.Collection;

import net.anotheria.moskito.api.MoskitoAPI;
import net.anotheria.moskito.api.exception.MoskitoAPIException;

/**
 * API for working with moskito data.
 * 
 * @author Alexandr Bolbat
 */
public interface MoskitoDataAPI extends MoskitoAPI {

	/**
	 * Get producers data for default interval.
	 * 
	 * @param detailed
	 *            - <code>true</code> for detailed data, <code>false</code> for aggregated data
	 * @param producers
	 *            - producers names
	 * @return {@link Collection} of {@link ProducerDataAO}
	 * @throws MoskitoAPIException
	 */
	Collection<ProducerDataAO> getProducersData(boolean detailed, String... producers) throws MoskitoAPIException;

	/**
	 * Get producers data for interval.
	 * 
	 * @param detailed
	 *            - <code>true</code> for detailed data, <code>false</code> for aggregated data
	 * @param interval
	 *            - interval
	 * @param producers
	 *            - producers names
	 * @return {@link Collection} of {@link ProducerDataAO}
	 * @throws MoskitoAPIException
	 */
	Collection<ProducerDataAO> getProducersData(boolean detailed, String interval, String... producers) throws MoskitoAPIException;

}
