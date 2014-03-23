package net.anotheria.moskito.webui.journey.api;

import net.anotheria.anoplass.api.API;
import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoprise.metafactory.Service;
import net.anotheria.moskito.core.journey.Journey;
import org.distributeme.annotation.DistributeMe;
import org.distributeme.annotation.SupportService;

import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 14.02.13 10:00
 */
@DistributeMe(agentsSupport = false)
@SupportService
public interface JourneyAPI extends API, Service {
	/**
	 * Returns list of available journeys.
	 * @return
	 * @throws APIException
	 */
	List<JourneyListItemAO> getJourneys() throws APIException;

	/**
	 * Returns specified journey.
	 * TODO - maybe in the future, the Journey object should be replaced by AO object.
	 * @param name
	 * @return
	 * @throws APIException
	 */
	Journey getJourney(String name) throws APIException;
}
