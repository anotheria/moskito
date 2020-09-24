package net.anotheria.moskito.webui.accumulators.action;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.core.accumulation.Accumulator;
import net.anotheria.moskito.core.accumulation.AccumulatorRepository;
import net.anotheria.moskito.core.config.MoskitoConfiguration;
import net.anotheria.moskito.core.config.accumulators.AccumulatorSetConfig;
import net.anotheria.moskito.core.config.accumulators.AccumulatorSetMode;
import net.anotheria.moskito.core.config.accumulators.AccumulatorsConfig;
import net.anotheria.moskito.core.config.thresholds.GuardConfig;
import net.anotheria.moskito.webui.accumulators.api.AccumulatedSingleGraphAO;
import net.anotheria.moskito.webui.accumulators.api.AccumulatorAO;
import net.anotheria.moskito.webui.accumulators.api.MultilineChartAO;
import net.anotheria.moskito.webui.accumulators.bean.AccumulatedValuesBean;
import net.anotheria.moskito.webui.accumulators.bean.AccumulatorSetBean;
import net.anotheria.moskito.webui.accumulators.util.AccumulatorUtility;
import net.anotheria.moskito.webui.util.WebUIConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
/**
 * Displays configured accumulators and the gathered data and graphs.
 * @author lrosenberg
 */
public class ShowAccumulatorsAction extends BaseAccumulatorsAction {

	/**
	 * Graph data modes.
	 * @author lrosenberg
	 *
	 */
	static enum MODE{
		/**
		 * Combined mode means multiple graphs in one graph.
		 */
		combined, 
		/**
		 * Multiple graphs in one graph, normalized to 1-100% scale.
		 */
		normalized, 
		/**
		 * Multiple graphs on one page, one per accumulator.
		 */
		multiple;
		
		static MODE fromString(String value){
			if (value==null)
				return combined;
			for (MODE m : values()){
				if (m.name().equals(value)){
					return m;
				}
			}
			//combined is default
			return combined;
		}
	}

	private AccumulatorSetMode getModeFromParameter(String parameterValue){
		if (parameterValue == null ||parameterValue.length()==0)
			return AccumulatorSetMode.COMBINED; //default

		for (AccumulatorSetMode mode : AccumulatorSetMode.values()){
			if (mode.name().equalsIgnoreCase(parameterValue))
				return mode;
		}

		return AccumulatorSetMode.COMBINED; //default
	}
	
	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean formBean,
			HttpServletRequest req, HttpServletResponse res) throws Exception {

		//handling of accumulator sets
        MoskitoConfiguration config = getAdditionalFunctionalityAPI().getConfiguration();
        AccumulatorsConfig configuration = config.getAccumulatorsConfig();
		AccumulatorSetConfig setConfigs[] = configuration.getAccumulatorSets();
		if (setConfigs!=null && setConfigs.length>0) {
			LinkedList<AccumulatorSetBean> acSetBeans = new LinkedList<AccumulatorSetBean>();
			for (AccumulatorSetConfig asc : setConfigs) {
				AccumulatorSetBean bean = new AccumulatorSetBean();
				bean.setName(asc.getName());
				StringBuilder names = new StringBuilder();
				StringBuilder link = new StringBuilder("mskAccumulators?");
				for (String n : asc.getAccumulatorNames()) {
					if (names.length() > 0)
						names.append(", ");
					names.append(n);
					Accumulator acc = AccumulatorRepository.getInstance().getByName(n);
					if (acc != null) {
						link.append("id_").append(acc.getId()).append("=set&");
					}
				}
				link.append("mode=").append(asc.getMode().name().toLowerCase());
				bean.setAccumulatorNames(names.toString());
				bean.setLink(link.toString());
				acSetBeans.add(bean);
			}
			if (acSetBeans.size() > 0) {
				req.setAttribute("accumulatorSetBeans", acSetBeans);
			}
		}

		//selected accumulator handling
		AccumulatorSetMode mode = getModeFromParameter(req.getParameter("mode"));
		req.setAttribute(mode.name().toLowerCase()+"_set", Boolean.TRUE);

		int maxValues = 200;
		try{
			maxValues = Integer.parseInt(req.getParameter("maxValues"));
		}catch(Exception ignored){;/*empty*/}
		req.setAttribute("maxValues", maxValues);
		
		req.setAttribute("accumulators", getAccumulatorAPI().getAccumulatorDefinitions());
		
		List<String> ids = new ArrayList<>();
		
		Enumeration<?> names = req.getParameterNames();
		while(names.hasMoreElements()){
			String name = (String)names.nextElement();
			if (name.startsWith("id_")){
				ids.add(name.substring("id_".length()));
				//set flag to activate checkbox
				req.setAttribute(name+"_set", Boolean.TRUE);
			}
		}

		int numberOfIds = ids.size();
		if (numberOfIds > 0){

			if (mode == AccumulatorSetMode.COMBINED || mode == AccumulatorSetMode.NORMALIZED){
				MultilineChartAO mchartAO = null;
				if (mode == AccumulatorSetMode.COMBINED)
					mchartAO = getAccumulatorAPI().getCombinedAccumulatorGraphData(ids);
				if (mode == AccumulatorSetMode.NORMALIZED)
					mchartAO = getAccumulatorAPI().getNormalizedAccumulatorGraphData(ids);

				req.setAttribute("data", mchartAO.getData());
				req.setAttribute("accNames", mchartAO.getNames());
				req.setAttribute("accNamesConcat", net.anotheria.util.StringUtils.concatenateTokens(mchartAO.getNames(), ","));
				req.setAttribute("accumulatorsColors", mchartAO.getAccumulatorsColorsDataJSON());
			} else {

				//create multiple graphs with one line each.
				List<AccumulatedSingleGraphAO> singleGraphDataBeans = new ArrayList<>(numberOfIds);
				List<String> accumulatorsNames = new ArrayList<>(numberOfIds);

				for (String id : ids) {
					final AccumulatorAO accumulator = getAccumulatorAPI().getAccumulator(id);
					// collect accumulators names
					accumulatorsNames.add(accumulator.getName());
					// collect graph beans
					final AccumulatedSingleGraphAO singleGraphDataBean = getAccumulatorAPI().getAccumulatorGraphData(id);
					singleGraphDataBean.setData(accumulator.getValues());
					singleGraphDataBeans.add(singleGraphDataBean);
				}

				req.setAttribute("accNames", accumulatorsNames);
				req.setAttribute("accNamesConcat", net.anotheria.util.StringUtils.concatenateTokens(accumulatorsNames, ","));
				req.setAttribute("singleGraphData", singleGraphDataBeans);
				req.setAttribute("accumulatorsColors", AccumulatorUtility.accumulatorsColorsToJSON(singleGraphDataBeans));
				req.setAttribute("data", Boolean.TRUE);
			}

			if (req.getParameter("withThresholds") != null) {
				req.setAttribute("withThresholds" + "_set", Boolean.TRUE);

				if (mode == AccumulatorSetMode.MULTIPLE || numberOfIds == 1) {
					Map<String, List<GuardConfig>> thresholds = new LinkedHashMap<>();

					for (String id : ids) {
						final AccumulatedSingleGraphAO singleGraphDataBean = getAccumulatorAPI().getAccumulatorGraphData(id);
						thresholds.put(singleGraphDataBean.getName(), singleGraphDataBean.getThreshold());
					}
					req.setAttribute("thresholds", thresholds);
					req.setAttribute("thresholdGraphColors", WebUIConfig.getInstance().getThresholdGraphColors());
				}
			}
		}

		req.setAttribute("dashboards", getDashboardsList());
		req.setAttribute("charts", "enable");

		if (getForward(req).equalsIgnoreCase("csv")){
			res.setHeader("Content-Disposition", "attachment; filename=\"accumulators.csv\"");
		}
		return mapping.findCommand(getForward(req));
	}
	
	/*test visibility */ static void normalize(List<AccumulatedValuesBean> values, List<String> names, int limit){
		for (String name : names){
			ArrayList<Float> valueCopy = new ArrayList<Float>(values.size());
			//step1 transform everything to float
			float min = Float.MAX_VALUE, max = Float.MIN_VALUE;
			for (AccumulatedValuesBean v : values){
				float val = Float.parseFloat(v.getValue(name));
				if (val>max)
					max = val;
				if (val<min)
					min = val;
				valueCopy.add(val);
			}
			float range = max - min;
			float multiplier = limit / range;

			//step2 recalculate
			for (int i=0; i<values.size(); i++){
				float newValue = (valueCopy.get(i)-min)*multiplier;
				values.get(i).setValue(name, String.valueOf(newValue));
			}
		}
	}
	

	@Override
	protected String getLinkToCurrentPage(HttpServletRequest req) {
		String newQS = rebuildQueryStringWithoutParameter(req.getQueryString(), "ts", "pReloadInterval");
		if (newQS.length()>0)
			newQS+="&";
		newQS+="ts="+System.currentTimeMillis();
		return "mskAccumulators?"+ newQS;
	}

	@Override
	protected String getPageName() {
		return "accumulators";
	}

	@Override
	protected boolean exportSupported() {
		return true;
	}

	private String getDashboardsList() throws APIException {
		return net.anotheria.util.StringUtils.concatenateTokens(getDashboardAPI().getDashboardNames(), ",");
	}
}
