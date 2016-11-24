/*
 * $Id$
 * 
 * This file is part of the MoSKito software project
 * that is hosted at http://moskito.dev.java.net.
 * 
 * All MoSKito files are distributed under MIT License:
 * 
 * Copyright (c) 2006 The MoSKito Project Team.
 * 
 * Permission is hereby granted, free of charge,
 * to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), 
 * to deal in the Software without restriction, 
 * including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit 
 * persons to whom the Software is furnished to do so, 
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice
 * shall be included in all copies 
 * or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY
 * OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS 
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, 
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package net.anotheria.moskito.webui.producers.action;

import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.core.decorators.IDecorator;
import net.anotheria.moskito.core.decorators.value.StatValueAO;
import net.anotheria.moskito.core.inspection.CreationInfo;
import net.anotheria.moskito.webui.accumulators.api.AccumulatedSingleGraphAO;
import net.anotheria.moskito.webui.producers.api.ProducerAO;
import net.anotheria.moskito.webui.producers.api.StatLineAO;
import net.anotheria.moskito.webui.shared.action.BaseMoskitoUIAction;
import net.anotheria.moskito.webui.shared.bean.GraphDataBean;
import net.anotheria.moskito.webui.shared.bean.GraphDataValueBean;
import net.anotheria.moskito.webui.shared.bean.NaviItem;
import net.anotheria.moskito.webui.shared.bean.StatBean;
import net.anotheria.moskito.webui.shared.bean.StatBeanSortType;
import net.anotheria.moskito.webui.shared.bean.StatDecoratorBean;
import net.anotheria.moskito.webui.shared.bean.UnitBean;
import net.anotheria.moskito.webui.threshold.bean.ThresholdStatusBean;
import net.anotheria.util.NumberUtils;
import net.anotheria.util.sorter.StaticQuickSorter;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static net.anotheria.moskito.webui.threshold.util.ThresholdStatusBeanUtility.getThresholdBeans;

/**
 * Presents a single, previously selected producer.
 * @author another
 *
 */
public class ShowProducerAction extends BaseMoskitoUIAction {
	/**
	 * Cumulated caption value.
	 */
	private static final String CUMULATED_STAT_NAME_VALUE = "cumulated";
	/**
	 * {@link Logger} instance.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ShowProducerAction.class);

	@Override public ActionCommand execute(ActionMapping mapping, FormBean bean, HttpServletRequest req, HttpServletResponse res) throws Exception {

		String intervalName = getCurrentInterval(req);
		UnitBean currentUnit = getCurrentUnit(req);

		ProducerAO producer = getProducerAPI().getProducer(req.getParameter(PARAM_PRODUCER_ID), intervalName, currentUnit.getUnit());
		req.setAttribute("producer", producer);

		//copies parameter for producer selection page.
		String target = req.getParameter("target");
		req.setAttribute("target", target);

		//String pFilterZero = req.getParameter(PARAM_FILTER_ZERO);
		//boolean filterZero = pFilterZero != null && pFilterZero.equalsIgnoreCase("true");

		IDecorator decorator = getDecoratorRegistry().getDecorator(producer.getStatsClazzName());
		Map<String, GraphDataBean> graphData = new HashMap<>();


		List<StatLineAO> allLines = producer.getLines();
		for (StatLineAO statLine : allLines){
			try{
				for(StatValueAO statBean : statLine.getValues()){
					String graphKey = decorator.getName()+ '_' +statBean.getName();
					GraphDataBean graphDataBean = new GraphDataBean(decorator.getName()+ '_' +statBean.getJsVariableName(), statBean.getName());
					graphData.put(graphKey, graphDataBean);
				}
			}catch(ArrayIndexOutOfBoundsException e){
				//producer has no stats at all, ignoring
			}
		}

		List<StatDecoratorBean> beans = new ArrayList<>(1);
		//sort

		final StatDecoratorBean decoratorBean = new StatDecoratorBean();
		decoratorBean.setName(decorator.getName());
		decoratorBean.setCaptions(decorator.getCaptions());

		final StatBeanSortType sortType = getStatBeanSortType(decoratorBean, req);

		// populate stats
		populateStats(decoratorBean, allLines, sortType);

		// populate cumulated stat
		populateCumulatedStats(decoratorBean, allLines);

		beans.add(decoratorBean);

		// populate graph data
		populateGraphData(decorator, graphData, allLines);

		req.setAttribute("decorators", beans);
		req.setAttribute("graphDatas", graphData.values());

		inspectProducer(req, producer);

		//check if there are accumulators or thresholds associated with this producer.
		List<String> accumulatorIdsTiedToThisProducer = getAccumulatorAPI().getAccumulatorIdsTiedToASpecificProducer(producer.getProducerId());
		if (accumulatorIdsTiedToThisProducer.size()>0){
			req.setAttribute("accumulatorsPresent", Boolean.TRUE);
			//create multiple graphs with one line each.
			List<AccumulatedSingleGraphAO> singleGraphDataBeans = getAccumulatorAPI().getChartsForMultipleAccumulators(accumulatorIdsTiedToThisProducer);
			req.setAttribute("singleGraphData", singleGraphDataBeans);
			req.setAttribute("accumulatorsColors", accumulatorsColorsToJSON(singleGraphDataBeans));

			List<String> accumulatorsNames = new LinkedList<>();

			for (AccumulatedSingleGraphAO ao : singleGraphDataBeans){
				accumulatorsNames.add(ao.getName());

			}

			req.setAttribute("accNames", accumulatorsNames);
			req.setAttribute("accNamesConcat", net.anotheria.util.StringUtils.concatenateTokens(accumulatorsNames, ","));
		}

		List<String> thresholdIdsTiedToThisProducers = getThresholdAPI().getThresholdIdsTiedToASpecificProducer(producer.getProducerId());
		if (thresholdIdsTiedToThisProducers.size()>0){
			req.setAttribute("thresholdsPresent", Boolean.TRUE);
			List<ThresholdStatusBean> thresholdStatusBeans = getThresholdBeans(getThresholdAPI().getThresholdStatuses(thresholdIdsTiedToThisProducers.toArray(new String[0])));
			req.setAttribute("thresholds", thresholdStatusBeans);
		}

		return mapping.findCommand( getForward(req) );
	}

	//TODO copied from show accumulators, should be moved to utility.
	private JSONArray accumulatorsColorsToJSON(final List<AccumulatedSingleGraphAO> graphAOs) {
		final JSONArray jsonArray = new JSONArray();

		for (AccumulatedSingleGraphAO graphAO : graphAOs) {
			if (StringUtils.isEmpty(graphAO.getName()) || StringUtils.isEmpty(graphAO.getColor()))
				continue;

			final JSONObject jsonObject = graphAO.mapColorDataToJSON();
			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}


	/**
	 * Allows to set all stats to decorator except cumulated stat.
	 * Stats will be sorted using given sort type.
	 *
	 * @param statDecoratorBean {@link StatDecoratorBean}
	 * @param allStatLines      list of {@link StatLineAO}, all stats present in producer
	 * @param sortType          {@link StatBeanSortType}
	 */
	private void populateStats(final StatDecoratorBean statDecoratorBean, final List<StatLineAO> allStatLines, final StatBeanSortType sortType) {
		if (allStatLines == null || allStatLines.isEmpty()) {
			LOGGER.warn("Producer's stats are empty");
			return;
		}

		final int cumulatedIndex = getCumulatedIndex(allStatLines);

		// stats
		int allStatLinesSize = allStatLines.size();
		final List<StatBean> statBeans = new ArrayList<>(allStatLinesSize);
		for (int i = 0; i < allStatLinesSize; i++) {
			if (i == cumulatedIndex)
				continue;

			final StatLineAO line = allStatLines.get(i);
			final List<StatValueAO> statValues = line.getValues();

			final StatBean statBean = new StatBean();
			statBean.setName(line.getStatName());
			statBean.setValues(statValues);
			statBeans.add(statBean);
		}

		// sort stat beans
		StaticQuickSorter.sort(statBeans, sortType);

		// set stats
		statDecoratorBean.setStats(statBeans);
	}

	/**
	 * Allows to set cumulated stat to decorator bean.
	 *
	 * @param decoratorBean {@link StatDecoratorBean}
	 * @param allStatLines  list of {@link StatLineAO}, all stats present in producer
	 */
	private void populateCumulatedStats(final StatDecoratorBean decoratorBean, final List<StatLineAO> allStatLines) {
		if (allStatLines == null || allStatLines.isEmpty()) {
			LOGGER.warn("Producer's stats are empty");
			return;
		}

		final int cumulatedIndex = getCumulatedIndex(allStatLines);
		if (cumulatedIndex == -1)
			return;

		final StatLineAO cumulatedStatLineAO = allStatLines.get(cumulatedIndex);

		final StatBean cumulatedStat = new StatBean();
		cumulatedStat.setName(cumulatedStatLineAO.getStatName());
		cumulatedStat.setValues(cumulatedStatLineAO.getValues());

		decoratorBean.setCumulatedStat(cumulatedStat);
	}

	/**
	 * Allows to populate graph data.
	 *
	 * @param decorator    {@link IDecorator}
	 * @param graphData    map with graph data
	 * @param allStatLines list of {@link StatLineAO}, all stats present in producer
	 */
	private void populateGraphData(final IDecorator decorator, final Map<String, GraphDataBean> graphData, final List<StatLineAO> allStatLines) {
		final int cumulatedIndex = getCumulatedIndex(allStatLines);

		for (int i = 0; i < allStatLines.size(); i++) {
			if (i == cumulatedIndex)
				continue;

			//TODO fix filterzero.
//			if (!filterZero || !s.isEmpty(intervalName)){

			final StatLineAO line = allStatLines.get(i);
			final List<StatValueAO> statValues = line.getValues();

			for (StatValueAO statValue : statValues) {
				final String graphKey = decorator.getName() + '_' + statValue.getName();
				final GraphDataValueBean value = new GraphDataValueBean(line.getStatName(), statValue.getRawValue());

				final GraphDataBean graphDataBean = graphData.get(graphKey);
				if (graphDataBean != null)
					graphDataBean.addValue(value);
			}
		}
	}

	/**
	 * Returns index of cumulated stat in producer's stats.
	 *
	 * @param allStatLines list of {@link StatLineAO}
	 * @return index of cumulated stat or {@value -1} if was not found
	 */
	private int getCumulatedIndex(final List<StatLineAO> allStatLines) {
		if (allStatLines == null || allStatLines.isEmpty()) {
			return -1;
		}

		int cumulatedIndex = -1;

		for (int i = 0, allStatLinesSize = allStatLines.size(); i < allStatLinesSize; i++) {
			final StatLineAO statLine = allStatLines.get(i);

			if (CUMULATED_STAT_NAME_VALUE.equals(statLine.getStatName())) {
				cumulatedIndex = i;
				break;
			}
		}

		return cumulatedIndex;
	}

	private void inspectProducer(HttpServletRequest req, ProducerAO producer){
		if (! (producer.isInspectable()))
			return;
		CreationInfo cInfo = producer.getCreationInfo();
		req.setAttribute("creationTimestamp", cInfo.getTimestamp());
		req.setAttribute("creationTime", NumberUtils.makeISO8601TimestampString(cInfo.getTimestamp()));
		List<String> stackTraceList = new ArrayList<String>(cInfo.getStackTrace().length);
		for (StackTraceElement elem : cInfo.getStackTrace())
			stackTraceList.add(elem.toString());
		req.setAttribute("creationTrace", stackTraceList);
	}

	@Override protected String getLinkToCurrentPage(HttpServletRequest req) {
		return "mskShowProducer"+ '?' +PARAM_PRODUCER_ID+ '=' +req.getParameter(PARAM_PRODUCER_ID);
	}

	private StatBeanSortType getStatBeanSortType(StatDecoratorBean decoratorBean, HttpServletRequest req){
		StatBeanSortType sortType;
		String paramSortBy = req.getParameter(decoratorBean.getSortByParameterName());
		if (paramSortBy!=null && paramSortBy.length()>0){
			try{
				int sortBy = Integer.parseInt(paramSortBy);
				String paramSortOrder = req.getParameter(decoratorBean.getSortOrderParameterName());
				boolean sortOrder = paramSortOrder!=null && paramSortOrder.equals("ASC") ?
						StatBeanSortType.ASC : StatBeanSortType.DESC;
				sortType = new StatBeanSortType(sortBy, sortOrder);
				req.getSession().setAttribute(decoratorBean.getSortTypeName(), sortType);
				return sortType;
			}catch(NumberFormatException skip){}
		}
		sortType = (StatBeanSortType)req.getSession().getAttribute(decoratorBean.getSortTypeName());
		if (sortType==null){
			sortType = new StatBeanSortType();
			req.getSession().setAttribute(decoratorBean.getSortTypeName(), sortType);
		}
		return sortType;
	}

	@Override
	protected NaviItem getCurrentNaviItem() {
		return NaviItem.PRODUCERS;
	}

	@Override
	protected String getPageName() {
		return "producer";
	}

	@Override
	protected boolean exportSupported() {
		return true;
	}

}
