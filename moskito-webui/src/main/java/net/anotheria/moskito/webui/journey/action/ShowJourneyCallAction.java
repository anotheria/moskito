package net.anotheria.moskito.webui.journey.action;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.moskito.webui.journey.api.TracedCallAO;
import net.anotheria.moskito.webui.journey.api.TracedCallDuplicateStepsAO;
import net.anotheria.moskito.webui.journey.api.TracedCallDuplicateStepsAOSortType;
import net.anotheria.util.sorter.StaticQuickSorter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

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
	public ActionCommand execute(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws APIException{

		String journeyName = req.getParameter(PARAM_JOURNEY_NAME);

		String tracedCallName = req.getParameter("pTracedCallName");

		int callPosition = 0;
		try{ 
			callPosition = Integer.parseInt(req.getParameter("pPos"));
		}catch(Exception ignored){}
				
		req.setAttribute("journeyName", journeyName);


		TracedCallAO bean = tracedCallName !=null && tracedCallName.length()>0 ?
				getJourneyAPI().getTracedCallByName(journeyName, tracedCallName, getCurrentUnit(req).getUnit()) :
				getJourneyAPI().getTracedCall(journeyName, callPosition, getCurrentUnit(req).getUnit());

		//check for duplicates
		List<TracedCallDuplicateStepsAO> dupStepBeans = bean.getDuplicateSteps();

		if (dupStepBeans.size()>0){
			int sortBy = TracedCallDuplicateStepsAOSortType.SORT_BY_DEFAULT;
			try{
				sortBy = Integer.parseInt(req.getParameter(PARAM_SORT_BY));
			}catch(Exception ignored){
			}
			
			boolean sortOrder = TracedCallDuplicateStepsAOSortType.ASC;
			try{
				sortOrder = req.getParameter(PARAM_SORT_ORDER).equalsIgnoreCase("ASC");
			}catch(Exception ignored){
			}
			dupStepBeans = StaticQuickSorter.sort(dupStepBeans, new TracedCallDuplicateStepsAOSortType(sortBy, sortOrder));

			req.setAttribute("dupStepBeansSize", dupStepBeans.size());
			req.setAttribute("dupStepBeans", dupStepBeans);
		}
		
		req.setAttribute("tracedCall", bean);
		
		return mapping.success();
	}
	

	@Override
	protected String getPageName() {
		return "journey_call";
	}


}
