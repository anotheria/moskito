package net.anotheria.moskito.core.journey;

import java.util.List;

/**
 * Interface for the monitoring journey manager, which manages (creates, stores, deletes) monitoring journeys.
 * @author lrosenberg
 *
 */
public interface JourneyManager {
	/**
	 * Returns all known journeys.
	 * @return
	 */
	List<Journey> getJourneys();
	
	/**
	 * Creates a new journey with the given name and returns it to the caller.
	 * @param name
	 * @return
	 */
	Journey createJourney(String name);
	/**
	 * Returns the monitoring journey with the given name.
	 * @param name name of the journey.
	 * @return newly created journey.
	 * @throws NoSuchJourneyException
	 */
	Journey getJourney(String name) throws NoSuchJourneyException;
	/**
	 * Removes the journey with the given name.
	 * @param name name of the journey to remove.
	 */
	void removeJourney(String name);
	/**
	 * Removes the journey from the internal storage.
	 * @param journey journey to remove.
	 */
	void removeJourney(Journey journey);

	/**
	 * Returns an existing journey or creates a new one.
	 * @param name
	 * @return
	 */
	Journey getOrCreateJourney(String name);
	
	
}
