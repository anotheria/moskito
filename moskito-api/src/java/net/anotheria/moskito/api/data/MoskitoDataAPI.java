package net.anotheria.moskito.api.data;

import java.util.List;

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
	 * @param producers
	 *            - producers names
	 * @return {@link List} of {@link ProducerDataAO}
	 * @throws MoskitoAPIException
	 */
	List<ProducerDataAO> getProducersData(List<String> producers) throws MoskitoAPIException;

	/**
	 * Get producers data for interval.
	 * 
	 * @param interval
	 *            - interval
	 * @param producers
	 *            - producers names
	 * @return {@link List} of {@link ProducerDataAO}
	 * @throws MoskitoAPIException
	 */
	List<ProducerDataAO> getProducersData(String interval, List<String> producers) throws MoskitoAPIException;

}
