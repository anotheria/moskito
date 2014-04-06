package net.anotheria.moskito.webui.journey.api;

import net.anotheria.anoplass.api.API;
import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoprise.metafactory.Service;
import net.anotheria.moskito.core.stats.TimeUnit;
import org.distributeme.annotation.DistributeMe;
import org.distributeme.annotation.SupportService;

import java.util.List;

/**
 * API for journeys.
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
	 * @param name name of the journey.
	 * @return
	 * @throws APIException
	 */
	JourneyAO getJourney(String name) throws APIException;

	TracedCallAO getTracedCall(String journeyName, int callPosition, TimeUnit unit) throws APIException;

	List<AnalyzedProducerCallsMapAO> analyzeJourney(String journeyName) throws APIException;
}
