package net.anotheria.moskito.webui.journey.action;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.webui.MoSKitoWebUIContext;
import net.anotheria.moskito.webui.journey.api.AnalyzedProducerCallsAOSortType;
import net.anotheria.moskito.webui.journey.api.AnalyzedProducerCallsMapAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AnalyzeJourneyAction extends BaseJourneyAction{
	
	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "";
	}
	
	private static Logger log = LoggerFactory.getLogger(AnalyzeJourneyAction.class);

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws APIException{

		String journeyName = req.getParameter("pJourneyName");
		req.setAttribute("journeyName", journeyName);

		List<AnalyzedProducerCallsMapAO> callsList = getJourneyAPI().analyzeJourney(journeyName);
		req.setAttribute("callsList", callsList);
		
		//prepare sort type
		String sortOrder = req.getParameter("pSortOrder");
		String sortBy = req.getParameter("pSortBy");
		if ( sortBy!=null && sortBy.length()>0){
			AnalyzedProducerCallsAOSortType st = AnalyzedProducerCallsAOSortType.fromStrings(sortBy, sortOrder);
			MoSKitoWebUIContext.getCallContext().setAnalyzeProducerCallsSortType(st);
		}
		return mapping.success();
	}

	@Override
	protected String getPageName() {
		return "journey_analyze";
	}

}