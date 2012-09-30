package net.anotheria.moskito.webui.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.core.calltrace.CurrentlyTracedCall;
import net.anotheria.moskito.core.journey.Journey;
import net.anotheria.moskito.core.journey.NoSuchJourneyException;
import net.anotheria.moskito.webui.bean.JourneyListItemBean;
import net.anotheria.moskito.webui.bean.TracedCallListItemBean;
import net.anotheria.util.NumberUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * The actions displays a journey as a whole.
 * @author lrosenberg.
 *
 */
public class ShowJourneyAction extends BaseJourneyAction{
	
	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "";
	}

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res){

		String journeyName = req.getParameter("pJourneyName");
		Journey journey = null;
		try{
			journey = getJourneyManager().getJourney(journeyName);
		}catch(NoSuchJourneyException e){
			throw new IllegalArgumentException("Journey with name "+journeyName+" not found.");
		}

		JourneyListItemBean bean = new JourneyListItemBean();
			
		bean.setName(journey.getName());
		bean.setActive(journey.isActive());
		bean.setCreated(NumberUtils.makeISO8601TimestampString(journey.getCreatedTimestamp()));
		bean.setLastActivity(NumberUtils.makeISO8601TimestampString(journey.getLastActivityTimestamp()));
		bean.setNumberOfCalls(journey.getTracedCalls().size());
		req.setAttribute("journey", bean);

		List<CurrentlyTracedCall> recorded = journey.getTracedCalls();
		List<TracedCallListItemBean> beans = new ArrayList<TracedCallListItemBean>(recorded.size());
		for (int i=0; i<recorded.size(); i++){
			CurrentlyTracedCall tracedCall = recorded.get(i);
			TracedCallListItemBean b = new TracedCallListItemBean();
			b.setName(tracedCall.getName());
			b.setDate(NumberUtils.makeISO8601TimestampString(tracedCall.getCreated()));
			b.setContainedSteps(tracedCall.getNumberOfSteps());
			beans.add(b);
		}
		
		req.setAttribute("recorded", beans);
		return mapping.success();
	}
	
}