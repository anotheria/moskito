package net.anotheria.moskito.webui.journey.action;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.webui.MoSKitoWebUIContext;
import net.anotheria.moskito.webui.journey.api.AnalyzedJourneyAO;
import net.anotheria.moskito.webui.journey.api.AnalyzedProducerCallsAOSortType;
import net.anotheria.moskito.webui.journey.api.AnalyzedProducerCallsMapAO;
import net.anotheria.moskito.webui.shared.bean.GraphDataBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Executes journey analyses, which means calculates how many time each producer was called in each step of the journey and how long it took.
 * Also presents overall statistics for total calls, by category and by subsystem.
 */
public class AnalyzeJourneyAction extends BaseJourneyAction {

    /**
     * URL parameter - journey name to be analyzed.
     */
    public static final String PARAM_JOURNEY_NAME = "pJourneyName";

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionCommand execute(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws APIException {

        String journeyName = req.getParameter(PARAM_JOURNEY_NAME);
        req.setAttribute("journeyName", journeyName);

        //add totals to the journey list on top.
        AnalyzedJourneyAO analyzedJourney = getJourneyAPI().analyzeJourney(journeyName);
        List<AnalyzedProducerCallsMapAO> callsInJourny = analyzedJourney.getCalls();
		LinkedList<AnalyzedProducerCallsMapAO> newCalls = new LinkedList<>();
		newCalls.add(analyzedJourney.getTotalByProducerId());
		newCalls.add(analyzedJourney.getTotalByCategoryId());
		newCalls.add(analyzedJourney.getTotalBySubsystemId());
		newCalls.addAll(callsInJourny);
		analyzedJourney.setCalls(newCalls);
        req.setAttribute("analyzedJourney", analyzedJourney);

        // Preparing graph data
        Map<String, GraphDataBean> graphData = new HashMap<>();
        for (AnalyzedProducerCallsMapAO callsMap : analyzedJourney.getCalls()) {
            graphData = fillGraphDataMap(graphData, callsMap);
        }

        req.setAttribute("graphDatas", graphData.values());

        //prepare sort type
        String sortOrder = req.getParameter("pSortOrder");
        String sortBy = req.getParameter("pSortBy");
        if (sortBy != null && sortBy.length() > 0) {
            AnalyzedProducerCallsAOSortType st = AnalyzedProducerCallsAOSortType.fromStrings(sortBy, sortOrder);
            MoSKitoWebUIContext.getCallContext().setAnalyzeProducerCallsSortType(st);
        }
        return mapping.success();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getLinkToCurrentPage(HttpServletRequest req) {
        return "mskAnalyzeJourney?" + PARAM_JOURNEY_NAME + '=' + req.getParameter(PARAM_JOURNEY_NAME);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getPageName() {
        return "journey_analyze";
    }

}