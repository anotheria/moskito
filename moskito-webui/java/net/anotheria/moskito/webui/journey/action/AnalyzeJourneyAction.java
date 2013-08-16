package net.anotheria.moskito.webui.journey.action;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.core.calltrace.CurrentlyTracedCall;
import net.anotheria.moskito.core.calltrace.TraceStep;
import net.anotheria.moskito.core.journey.Journey;
import net.anotheria.moskito.webui.CurrentSelection;
import net.anotheria.moskito.webui.journey.bean.AnalyzeProducerCallsBeanSortType;
import net.anotheria.moskito.webui.journey.bean.AnalyzeProducerCallsMapBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

public class AnalyzeJourneyAction extends BaseJourneyAction{
	
	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "";
	}
	
	private static Logger log = LoggerFactory.getLogger(AnalyzeJourneyAction.class);

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res){

		List<AnalyzeProducerCallsMapBean> callsList = new ArrayList<AnalyzeProducerCallsMapBean>();

		String journeyName = req.getParameter("pJourneyName");
		Journey journey = null;
		try{
			journey = getJourneyAPI().getJourney(journeyName);
		}catch(APIException e){
			throw new IllegalArgumentException("Journey with name "+journeyName+" not found.");
		}
		AnalyzeProducerCallsMapBean overallCallsMap = new AnalyzeProducerCallsMapBean(journey.getName()+" - TOTAL");
		callsList.add(overallCallsMap);
		req.setAttribute("journeyName", journeyName);
		
		List<CurrentlyTracedCall> tracedCalls = journey.getTracedCalls();
		for (CurrentlyTracedCall tc : tracedCalls){
			if (tc==null){
				log.warn("TracedCall is null!");
				continue;
			}
			AnalyzeProducerCallsMapBean singleCallMap = new AnalyzeProducerCallsMapBean(tc.getName());
			for (TraceStep step : tc.getRootStep().getChildren()){
				addStep(step, singleCallMap, overallCallsMap);
			}
			callsList.add(singleCallMap);
		}
		req.setAttribute("callsList", callsList);
		
		//prepare sort type
		String sortOrder = req.getParameter("pSortOrder");
		String sortBy = req.getParameter("pSortBy");
		if ( sortBy!=null && sortBy.length()>0){
			AnalyzeProducerCallsBeanSortType st = AnalyzeProducerCallsBeanSortType.fromStrings(sortBy, sortOrder);
			CurrentSelection.get().setAnalyzeProducerCallsSortType(st);
		}
		return mapping.success();
	}
	
	private void addStep(TraceStep step, AnalyzeProducerCallsMapBean... maps){
		String producerName = step.getProducer() == null ? 
				"UNKNOWN" : step.getProducer().getProducerId();
		for (AnalyzeProducerCallsMapBean map : maps){
			map.addProducerCall(producerName,  step.getNetDuration());
		}
		for (TraceStep childStep : step.getChildren()){
			addStep(childStep, maps);
		}
	}
}