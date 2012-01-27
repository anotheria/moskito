package net.anotheria.moskito.api.metadata;

import java.util.List;

import net.anotheria.moskito.api.MoskitoAPI;

/**
 * API for working with moskito meta data.
 * 
 * @author Alexandr Bolbat
 */
public interface MoskitoMetaDataAPI extends MoskitoAPI {

	/**
	 * Get available intervals.
	 * 
	 * @return {@link List} of {@link String}
	 */
	List<String> getIntervals();

	/**
	 * Get all subsystems.
	 * 
	 * @return {@link List} of {@link String}
	 */
	List<String> getSubsystems();

	/**
	 * Get all categories.
	 * 
	 * @return {@link List} of {@link String}
	 */
	List<String> getCategories();

	/**
	 * Get all producers.
	 * 
	 * @return {@link List} of {@link String}
	 */
	List<String> getProducers();

	/**
	 * Get producers for subsystems.
	 * 
	 * @param subsystems
	 *            - subsystems names
	 * @return {@link List} of {@link String}
	 */
	List<String> getProducersBySubsystems(List<String> subsystems);

	/**
	 * Get producers for categories.
	 * 
	 * @param categories
	 *            - categories names
	 * @return {@link List} of {@link String}
	 */
	List<String> getProducersByCategories(List<String> categories);

	/**
	 * Get producers for subsystem and categories.
	 * 
	 * @param subsystem
	 *            - subsystem name
	 * @param categories
	 *            - categories names
	 * @return {@link List} of {@link String}
	 */
	List<String> getProducers(String subsystem, List<String> categories);

}
