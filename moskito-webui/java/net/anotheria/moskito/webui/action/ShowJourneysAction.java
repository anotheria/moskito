package net.anotheria.moskito.webui.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.core.journey.Journey;
import net.anotheria.moskito.webui.bean.JourneyListItemBean;
import net.anotheria.util.NumberUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Show all available (recorded) monitoring sessions.
 * @author lrosenberg.
 *
 */
public class ShowJourneysAction extends BaseJourneyAction{
	
	
	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "";
	}

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) {

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
			contextPath = "/"+contextPath;
		url += contextPath;
		String url1 = url + "?mskJourney=start&mskJourneyName=";
		String url2 = url + "?mskJourney=stop&mskJourneyName=";

		req.setAttribute("new_journey_url", url1);
		req.setAttribute("stop_journey_url", url2);

		List<Journey> journeys = getJourneyManager().getJourneys();
		List<JourneyListItemBean> beans = new ArrayList<JourneyListItemBean>(journeys.size());
		
		for (Journey j : journeys){
			JourneyListItemBean bean = new JourneyListItemBean();
			
			bean.setName(j.getName());
			bean.setActive(j.isActive());
			bean.setCreated(NumberUtils.makeISO8601TimestampString(j.getCreatedTimestamp()));
			bean.setLastActivity(NumberUtils.makeISO8601TimestampString(j.getLastActivityTimestamp()));
			bean.setNumberOfCalls(j.getTracedCalls().size());
			beans.add(bean);
		}

		req.setAttribute("journeys", beans);
		if (beans.size()>0)
			req.setAttribute("journeysPresent", Boolean.TRUE);
		return mapping.success();
	}
	

}