package net.java.dev.moskito.webui.action;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.util.NumberUtils;
import net.anotheria.util.sorter.StaticQuickSorter;
import net.java.dev.moskito.core.calltrace.CurrentlyTracedCall;
import net.java.dev.moskito.core.calltrace.TraceStep;
import net.java.dev.moskito.core.journey.Journey;
import net.java.dev.moskito.core.journey.NoSuchJourneyException;
import net.java.dev.moskito.core.stats.TimeUnit;
import net.java.dev.moskito.webui.bean.JourneyCallDuplicateStepBean;
import net.java.dev.moskito.webui.bean.JourneyCallDuplicateStepBeanSortType;
import net.java.dev.moskito.webui.bean.JourneyCallIntermediateContainerBean;
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
		
		JourneyCallIntermediateContainerBean container = new JourneyCallIntermediateContainerBean();
		fillUseCasePathElementBeanList(container, root,0, unit);
		bean.setElements(container.getElements());
		
		//check for duplicates
		List<JourneyCallDuplicateStepBean> dupStepBeans = new ArrayList<JourneyCallDuplicateStepBean>(); 
		Map<String,JourneyCallIntermediateContainerBean.ReversedCallHelper > stepsReversed = container.getReversedSteps();
		for (Map.Entry<String, JourneyCallIntermediateContainerBean.ReversedCallHelper> entry : stepsReversed.entrySet()){
			if (entry.getValue()!=null && entry.getValue().getPositions().size()>1){
				//duplicate found
				JourneyCallDuplicateStepBean dupStepBean = new JourneyCallDuplicateStepBean();
				dupStepBean.setCall(entry.getKey());
				dupStepBean.setPositions(entry.getValue().getPositions());
				dupStepBean.setTimespent(entry.getValue().getTimespent());
				dupStepBean.setDuration(entry.getValue().getDuration());
				dupStepBeans.add(dupStepBean);
			}
		}
		
		if (dupStepBeans.size()>0){
			int sortBy = JourneyCallDuplicateStepBeanSortType.SORT_BY_DEFAULT;
			try{
				sortBy = Integer.parseInt(req.getParameter(PARAM_SORT_BY));
			}catch(Exception ignored){}
			
			boolean sortOrder = JourneyCallDuplicateStepBeanSortType.ASC;
			try{
				sortOrder = req.getParameter(PARAM_SORT_ORDER).equalsIgnoreCase("ASC");
			}catch(Exception ignored){}
			dupStepBeans = StaticQuickSorter.sort(dupStepBeans, new JourneyCallDuplicateStepBeanSortType(sortBy, sortOrder));

			req.setAttribute("dupStepBeansSize", dupStepBeans.size());
			req.setAttribute("dupStepBeans", dupStepBeans);
		}
		
		req.setAttribute("tracedCall", bean);
		
		return mapping.success();
	}
	
	private void fillUseCasePathElementBeanList(JourneyCallIntermediateContainerBean container, TraceStep element, int recursion, TimeUnit unit){
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
		container.add(b);

		long timespentInChilds = 0;
		for (TraceStep p : element.getChildren()){
			
			timespentInChilds += p.getDuration();
			fillUseCasePathElementBeanList(container, p, recursion+1, unit);
		}
		b.setTimespent(unit.transformNanos((element.getDuration() - timespentInChilds)));
		
	}

}
