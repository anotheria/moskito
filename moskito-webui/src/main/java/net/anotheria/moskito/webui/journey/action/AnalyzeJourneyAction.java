package net.anotheria.moskito.webui.journey.action;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.webui.MoSKitoWebUIContext;
import net.anotheria.moskito.webui.journey.api.AnalyzedJourneyAO;
import net.anotheria.moskito.webui.journey.api.AnalyzedProducerCallsAO;
import net.anotheria.moskito.webui.journey.api.AnalyzedProducerCallsAOSortType;
import net.anotheria.moskito.webui.journey.api.AnalyzedProducerCallsMapAO;
import net.anotheria.moskito.webui.shared.bean.GraphDataBean;
import net.anotheria.moskito.webui.shared.bean.GraphDataValueBean;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class AnalyzeJourneyAction extends BaseJourneyAction {

    /**
     * URL parameter - journey name to be analyzed.
     */
    public static final String PARAM_JOURNEY_NAME = "pJourneyName";

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionCommand execute(ActionMapping mapping, FormBean formBean, HttpServletRequest req, HttpServletResponse res) throws APIException {

        String journeyName = req.getParameter(PARAM_JOURNEY_NAME);
        req.setAttribute("journeyName", journeyName);

        AnalyzedJourneyAO analyzedJourney = getJourneyAPI().analyzeJourney(journeyName);
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

    /**
     * Converts values from {@link AnalyzedProducerCallsMapAO} to {@link GraphDataBean}s.
     * @param graphData Map containing graph data
     * @param analyzedProducerCallsMap {@link AnalyzedProducerCallsMapAO}
     * @return Map with graph data
     */
    private Map<String, GraphDataBean> fillGraphDataMap(Map<String, GraphDataBean> graphData, AnalyzedProducerCallsMapAO analyzedProducerCallsMap) {
        if (graphData == null) {
            graphData = new HashMap<>();
        }

        for (AnalyzedProducerCallsAO producerCallsBean : analyzedProducerCallsMap.getProducerCallsBeans()) {
            // We have only two values for each call: Number of Calls and Duration
            final String callsNumberKey = analyzedProducerCallsMap.getName() + "_Calls";
            final String durationKey = analyzedProducerCallsMap.getName() + "_Duration";

            final GraphDataValueBean callsNumberValue = new GraphDataValueBean(producerCallsBean.getProducerId() + ".Calls", String.valueOf(producerCallsBean.getNumberOfCalls()));
            final GraphDataValueBean durationValue = new GraphDataValueBean(producerCallsBean.getProducerId() + ".Duration", String.valueOf(producerCallsBean.getTotalTimeSpentTransformed()));

            GraphDataBean callsNumberDataBean = graphData.get(callsNumberKey);
            GraphDataBean durationDataBean = graphData.get(durationKey);

            if (callsNumberDataBean == null) {
                callsNumberDataBean = new GraphDataBean(callsNumberKey, "Calls");
            }

            if (durationDataBean == null) {
                durationDataBean = new GraphDataBean(durationKey, "Duration");
            }

            callsNumberDataBean.addValue(callsNumberValue);
            graphData.put(callsNumberKey, callsNumberDataBean);

            durationDataBean.addValue(durationValue);
            graphData.put(durationKey, durationDataBean);
        }

        return graphData;
    }
}