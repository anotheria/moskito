package net.java.dev.moskito.webui.action;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.util.NumberUtils;
import net.java.dev.moskito.core.calltrace.CurrentlyTracedCall;
import net.java.dev.moskito.core.calltrace.TraceStep;
import net.java.dev.moskito.core.journey.Journey;
import net.java.dev.moskito.core.journey.NoSuchJourneyException;
import net.java.dev.moskito.core.stats.TimeUnit;
import net.java.dev.moskito.webui.bean.TraceStepBean;
import net.java.dev.moskito.webui.bean.TracedCallBean;

/**
 * Show a single call in a monitoring session. 
 * @author lrosenberg.
 */
public class ShowJourneyCallAction extends BaseJourneyAction{

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "mskShowJourneyCall?pJourneyName="+req.getParameter("pJourneyName")+"&pPos="+req.getParameter("pPos");
	}

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) {

		String journeyName = req.getParameter("pJourneyName");
		int callPosition = 0;
		try{ 
			callPosition = Integer.parseInt(req.getParameter("pPos"));
		}catch(Exception ignored){}
				
		Journey journey = null;
		try{
			journey = getJourneyManager().getJourney(journeyName);
		}catch(NoSuchJourneyException e){
			throw new IllegalArgumentException("Journey with name "+journeyName+" not found.");
		}
		CurrentlyTracedCall useCase = journey.getTracedCalls().get(callPosition);
		
		req.setAttribute("journeyName", journeyName);
		
		TraceStep root = useCase.getRootStep();
		TracedCallBean bean = new TracedCallBean();
		bean.setName(useCase.getName());
		bean.setCreated(useCase.getCreated());
		bean.setDate(NumberUtils.makeISO8601TimestampString(useCase.getCreated()));
			
		TimeUnit unit = getCurrentUnit(req).getUnit();
		
		ArrayList<TraceStepBean> elements = new ArrayList<TraceStepBean>();
		fillUseCasePathElementBeanList(elements, root,0, unit);
		bean.setElements(elements);
		
		req.setAttribute("tracedCall", bean);
		
		return mapping.success();
	}
	
	private void fillUseCasePathElementBeanList(List<TraceStepBean> list, TraceStep element, int recursion, TimeUnit unit){
		TraceStepBean b = new TraceStepBean();
		b.setCall(recursion == 0 ? "ROOT" : element.getCall());
		b.setRoot(recursion == 0);
		b.setLayer(recursion);
		b.setDuration(unit.transformNanos(element.getDuration()));
		StringBuilder ident = new StringBuilder();
		for (int i=0; i<recursion; i++)
			ident.append("&nbsp;&nbsp;");
		b.setIdent(ident.toString());
		b.setAborted(element.isAborted());
		list.add(b);

		long timespentInChilds = 0;
		for (TraceStep p : element.getChildren()){
			
			timespentInChilds += p.getDuration();
			fillUseCasePathElementBeanList(list, p, recursion+1, unit);
		}
		b.setTimespent(unit.transformNanos((element.getDuration() - timespentInChilds)));
		
	}

}
