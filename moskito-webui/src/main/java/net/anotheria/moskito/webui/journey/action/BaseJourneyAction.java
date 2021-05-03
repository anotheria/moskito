package net.anotheria.moskito.webui.journey.action;

import net.anotheria.moskito.webui.journey.api.AnalyzedProducerCallsAO;
import net.anotheria.moskito.webui.journey.api.AnalyzedProducerCallsMapAO;
import net.anotheria.moskito.webui.shared.action.BaseMoskitoUIAction;
import net.anotheria.moskito.webui.shared.bean.GraphDataBean;
import net.anotheria.moskito.webui.shared.bean.GraphDataValueBean;
import net.anotheria.moskito.webui.shared.bean.NaviItem;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Base action for journeys.
 * @author lrosenberg
 */
abstract class BaseJourneyAction extends BaseMoskitoUIAction {

	public static final String PARAM_JOURNEY_NAME = "pJourneyName";
	/**
	 * Creates a new action instance.
	 */
	protected BaseJourneyAction(){
	}

	@Override
	protected NaviItem getCurrentNaviItem() {
		return NaviItem.JOURNEYS;
	}

	@Override
	protected String getSubTitle() {
		return "Journeys";
	}
	@Override

	protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "mskShowJourneys?ts="+System.currentTimeMillis();
	}

	/**
	 * Converts values from {@link AnalyzedProducerCallsMapAO} to {@link GraphDataBean}s.
	 * @param graphData Map containing graph data
	 * @param analyzedProducerCallsMap {@link AnalyzedProducerCallsMapAO}
	 * @return Map with graph data
	 */
	protected Map<String, GraphDataBean> fillGraphDataMap(Map<String, GraphDataBean> graphData, AnalyzedProducerCallsMapAO analyzedProducerCallsMap) {
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
