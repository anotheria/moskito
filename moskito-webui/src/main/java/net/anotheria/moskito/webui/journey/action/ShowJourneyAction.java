package net.anotheria.moskito.webui.journey.action;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.moskito.webui.journey.api.JourneyAO;
import net.anotheria.moskito.webui.journey.api.JourneyListItemAO;
import net.anotheria.util.NumberUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The action displays a journey as a whole.
 * @author lrosenberg.
 *
 */
public class ShowJourneyAction extends BaseJourneyAction{

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "mskShowJourney?"+PARAM_JOURNEY_NAME+"="+req.getParameter(PARAM_JOURNEY_NAME);
	}


	@Override
	public ActionCommand execute(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws APIException{

		String journeyName = req.getParameter(PARAM_JOURNEY_NAME);
		JourneyAO journey = getJourneyAPI().getJourney(journeyName, getCurrentUnit(req).getUnit());
		JourneyListItemAO bean = new JourneyListItemAO();

		bean.setName(journey.getName());
		bean.setActive(journey.isActive());
		bean.setCreated(NumberUtils.makeISO8601TimestampString(journey.getCreatedTimestamp()));
		bean.setLastActivity(NumberUtils.makeISO8601TimestampString(journey.getLastActivityTimestamp()));
		bean.setNumberOfCalls(journey.getCalls().size());
		req.setAttribute("journey", bean);
		req.setAttribute("calls", journey.getCalls());

		return mapping.success();
	}

	@Override
	protected String getPageName() {
		return "journey";
	}


}