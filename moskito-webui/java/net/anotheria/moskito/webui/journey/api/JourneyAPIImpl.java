package net.anotheria.moskito.webui.journey.api;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoplass.api.APIInitException;
import net.anotheria.moskito.core.journey.Journey;
import net.anotheria.moskito.core.journey.JourneyManager;
import net.anotheria.moskito.core.journey.JourneyManagerFactory;
import net.anotheria.moskito.core.journey.NoSuchJourneyException;
import net.anotheria.moskito.webui.shared.api.AbstractMoskitoAPIImpl;
import net.anotheria.util.NumberUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 14.02.13 10:00
 */
public class JourneyAPIImpl extends AbstractMoskitoAPIImpl implements  JourneyAPI {

	private JourneyManager journeyManager;

	@Override
	public void init() throws APIInitException {
		super.init();
		journeyManager = JourneyManagerFactory.getJourneyManager();
	}

	@Override
	public List<JourneyListItemAO> getJourneys() throws APIException {
		List<Journey> journeys = journeyManager.getJourneys();
		List<JourneyListItemAO> beans = new ArrayList<JourneyListItemAO>(journeys.size());

		for (Journey j : journeys){
			JourneyListItemAO bean = new JourneyListItemAO();

			bean.setName(j.getName());
			bean.setActive(j.isActive());
			bean.setCreated(NumberUtils.makeISO8601TimestampString(j.getCreatedTimestamp()));
			bean.setLastActivity(NumberUtils.makeISO8601TimestampString(j.getLastActivityTimestamp()));
			bean.setNumberOfCalls(j.getTracedCalls().size());
			beans.add(bean);
		}

		return beans;
	}

	@Override
	public Journey getJourney(String name) throws APIException {
		try{
			return journeyManager.getJourney(name);
		}catch(NoSuchJourneyException e){
			throw new APIException("Journey not found.");
		}
	}
}
