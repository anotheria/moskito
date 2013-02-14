package net.anotheria.moskito.webui.journey.api;

import net.anotheria.anoplass.api.API;
import net.anotheria.anoplass.api.APIException;

import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 14.02.13 10:00
 */
public interface JourneyAPI extends API {
	List<JourneyListItemAO> getJourneys() throws APIException;
}
