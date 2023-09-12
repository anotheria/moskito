package net.anotheria.moskito.webui.journey.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.moskito.webui.journey.api.JourneyListItemAO;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Show all available journeys.
 * @author lrosenberg.
 *
 */
public class ShowJourneysAction extends BaseJourneyAction{
	
	
	@Override
	public ActionCommand execute(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception{

		//prepare URLs to start/stop journey from the console-ui.
		String contextPath = req.getContextPath();
		if (contextPath==null)
			contextPath = "";
		if (!contextPath.endsWith("/"))
			contextPath+="/";

		String url = req.getScheme();
		url += "://";
		url += req.getServerName();
		if (req.getServerPort()!=80 && req.getServerPort()!=443)
			url += ":"+req.getServerPort();
		if (!contextPath.startsWith("/"))
			contextPath = '/' +contextPath;
		url += contextPath;
		String url1 = url + "?mskJourney=start&mskJourneyName=";
		String url2 = url + "?mskJourney=stop&mskJourneyName=";

		req.setAttribute("new_journey_url", url1);
		req.setAttribute("stop_journey_url", url2);

		//... end prepare URLs to start/stop journey from the console-ui.
		List<JourneyListItemAO> journeys = getJourneyAPI().getJourneys();
		req.setAttribute("journeys", journeys);

		if (journeys.size()>0)
			req.setAttribute("journeysPresent", Boolean.TRUE);

		return mapping.success();
	}

	@Override
	protected String getPageName() {
		return "journeys";
	}


}