package net.anotheria.moskito.core.journey;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * An implementation of the JourneyManager..
 * @author lrosenberg
 */
public class JourneyManagerImpl implements JourneyManager{

	/**
	 * The map with stored sessions.
	 */
	private Map<String, Journey> journeys;
	/**
	 * Creates a new JourneyManagerImpl.
	 */
	JourneyManagerImpl(){
		journeys = new ConcurrentHashMap<>();
	}
	
	@Override public Journey createJourney(String name) {
		Journey s = new Journey(name);
		journeys.put(s.getName(), s);
		return s;
	}

	@Override public Journey getJourney(String name) throws NoSuchJourneyException {
		Journey s = journeys.get(name);
		if (s==null)
			throw new NoSuchJourneyException(name);
		return s;
	}

	@Override
	public Journey getOrCreateJourney(String name) {
		Journey s = journeys.get(name);
		if (s!=null)
			return s;
		s = new Journey(name);
		journeys.put(s.getName(), s);
		return s;

	}

	@Override public List<Journey> getJourneys() {
		ArrayList<Journey> ret = new ArrayList<Journey>(journeys.values());//do not call journeys size since it will synchronize the map
		return ret;
	}

	@Override public void removeJourney(Journey journey) {
		removeJourney(journey.getName());
		
	}

	@Override public void removeJourney(String name) {
		journeys.remove(name);		
	}

}
