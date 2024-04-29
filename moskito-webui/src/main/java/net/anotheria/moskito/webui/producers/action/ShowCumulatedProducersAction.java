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

import net.anotheria.anoplass.api.APIException;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.moskito.core.decorators.value.DoubleValueAO;
import net.anotheria.moskito.core.decorators.value.LongValueAO;
import net.anotheria.moskito.core.decorators.value.StatValueAO;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.webui.accumulators.api.AccumulatedSingleGraphAO;
import net.anotheria.moskito.webui.accumulators.util.AccumulatorUtility;
import net.anotheria.moskito.webui.producers.api.ProducerAO;
import net.anotheria.moskito.webui.producers.api.StatLineAO;
import net.anotheria.moskito.webui.producers.util.ProducerUtility;
import net.anotheria.moskito.webui.shared.action.BaseMoskitoUIAction;
import net.anotheria.moskito.webui.shared.bean.GraphDataBean;
import net.anotheria.moskito.webui.shared.bean.NaviItem;
import net.anotheria.moskito.webui.shared.bean.ProducerDecoratorBean;
import net.anotheria.moskito.webui.shared.bean.UnitBean;
import net.anotheria.moskito.webui.threshold.bean.ThresholdStatusBean;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static net.anotheria.moskito.webui.threshold.util.ThresholdStatusBeanUtility.getThresholdBeans;

/**
 * Cumulates several producers into one big producer.
 * Cumulated stat is recalculated based on all values from producers.
 *
 * @author strel
 */
public class ShowCumulatedProducersAction extends BaseMoskitoUIAction {

    /**
     * {@link Logger} instance.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(ShowCumulatedProducersAction.class);


    /**
     * {@inheritDoc}
     */
    @Override
    public ActionCommand execute(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {

        Map<String, GraphDataBean> graphData = new HashMap<>();
        List<String> accumulatorIds = new ArrayList<>();
        List<String> thresholdIds = new ArrayList<>();

        // Obtaining common parameters, required for ProducerAPI
        String intervalName = getCurrentInterval(req);
        UnitBean currentUnit = getCurrentUnit(req);

        // Getting action parameters, i.e. filters
        String decoratorName = req.getParameter(PARAM_DECORATOR);
        String categoryName = req.getParameter(PARAM_CATEGORY);
        String subsystemName = req.getParameter(PARAM_SUBSYSTEM);

        // Getting producers filtered by category and / or subsystem
        List<ProducerAO> producersList = getFilteredProducers(intervalName, currentUnit.getUnit(), categoryName, subsystemName);

        // Getting specified decorator with producers
        ProducerDecoratorBean decorator = ProducerUtility.filterProducersByDecoratorName(decoratorName, producersList, graphData);

        // Fill threshold and accumulator ids list
        for (ProducerAO producer : decorator.getProducers()) {
            accumulatorIds.addAll(getAccumulatorAPI().getAccumulatorIdsTiedToASpecificProducer(producer.getProducerId()));
            thresholdIds.addAll(getThresholdAPI().getThresholdIdsTiedToASpecificProducer(producer.getProducerId()));
        }

        // Populate common cumulated stat
        if (hasCumulatedStat(decorator)) {
            populateCumulatedStats(decorator);
        }

        // Remove cumulated stats from producer lines
        if (hasAnyStat(decorator)) {
            removeCumulatedStats(decorator);
        }

        req.setAttribute("decorator", decorator);
        req.setAttribute("graphDatas", graphData.values());

        setAccumulatorAttributes(accumulatorIds, req);
        setThresholdAttributes(thresholdIds, req);

        return mapping.findCommand(getForward(req));
    }

    /**
     * Sets accumulator specific attributes, required to show accumulator charts on page.
     * @param accumulatorIds Accumulator IDs, tied to given producers
     * @param request {@link HttpServletRequest}
     */
    private void setAccumulatorAttributes(List<String> accumulatorIds, HttpServletRequest request) throws APIException {
        if (accumulatorIds == null || accumulatorIds.isEmpty()) {
            return;
        }

        request.setAttribute("accumulatorsPresent", Boolean.TRUE);

        //create multiple graphs with one line each.
        List<AccumulatedSingleGraphAO> singleGraphDataBeans = getAccumulatorAPI().getChartsForMultipleAccumulators(accumulatorIds);
        request.setAttribute("singleGraphData", singleGraphDataBeans);
        request.setAttribute("accumulatorsColors", AccumulatorUtility.accumulatorsColorsToJSON(singleGraphDataBeans));

        List<String> accumulatorsNames = new LinkedList<>();
        for (AccumulatedSingleGraphAO ao : singleGraphDataBeans) {
            accumulatorsNames.add(ao.getName());
        }

        request.setAttribute("accNames", accumulatorsNames);
        request.setAttribute("accNamesConcat", net.anotheria.util.StringUtils.concatenateTokens(accumulatorsNames, ","));
    }

    /**
     * Sets threshold specific attributes, required to show thresholds on page.
     * @param thresholdIds Threshold IDs, tied to given producers
     * @param request {@link HttpServletRequest}
     */
    private void setThresholdAttributes(List<String> thresholdIds, HttpServletRequest request) throws APIException {
        if (thresholdIds == null || thresholdIds.isEmpty()) {
            return;
        }

        request.setAttribute("thresholdsPresent", Boolean.TRUE);
        List<ThresholdStatusBean> thresholdStatusBeans = getThresholdBeans(getThresholdAPI().getThresholdStatuses(thresholdIds.toArray(new String[0])));
        request.setAttribute("thresholds", thresholdStatusBeans);
    }


    /**
     * Returns list of producers filtered by category and / or subsystem.
     * @param intervalName Currently used interval name
     * @param timeUnit Currently used time unit
     * @param category Producer category name
     * @param subsystem Producer subsystem name
     */
    private List<ProducerAO> getFilteredProducers(String intervalName, TimeUnit timeUnit, String category, String subsystem) throws APIException {
        if (!StringUtils.isEmpty(category)) {
            return getProducerAPI().getAllProducersByCategory(category, intervalName, timeUnit, true);
        }

        if (!StringUtils.isEmpty(subsystem)) {
            return getProducerAPI().getAllProducersBySubsystem(subsystem, intervalName, timeUnit, true);
        }

        return getProducerAPI().getAllProducers(intervalName, timeUnit, true);
    }

    /**
     * Allows to set cumulated stat to decorator bean.
     * @param decorator {@link ProducerDecoratorBean}
     */
    private void populateCumulatedStats(final ProducerDecoratorBean decorator) {
        if (decorator == null || decorator.getProducers().isEmpty()) {
            LOGGER.warn("Producer's are empty");
            return;
        }

        // Used to calculate cumulated Avg stat
        int cumulatedStatsCounter = 0;

        // Aggregating values
        for (ProducerAO producer : decorator.getProducers()) {
            for (StatLineAO line : producer.getLines()) {
                if (!CUMULATED_STAT_NAME_VALUE.equals(line.getStatName())) {
                    final StatLineAO cumulatedStatLine = new StatLineAO();
                    cumulatedStatLine.setStatName(CUMULATED_STAT_NAME_VALUE);

                    if (decorator.getCumulatedStat() != null) {
                        cumulatedStatLine.setValues(mergeStatValues(decorator.getCumulatedStat().getValues(), line.getValues()));
                    } else {
                        cumulatedStatLine.setValues(line.getValues());
                    }

                    decorator.setCumulatedStat(cumulatedStatLine);
                    cumulatedStatsCounter++;
                }
            }
        }

        // Calculating specific values like ERate and Avg
        if (decorator.getCumulatedStat() != null) {
            // Finding Err and Req stats to calculate ERate
            StatValueAO reqStat = null;
            StatValueAO errStat = null;

            for (StatValueAO value : decorator.getCumulatedStat().getValues()) {
                if ("req".equalsIgnoreCase(value.getName())) {
                    reqStat = value;
                } else if ("err".equalsIgnoreCase(value.getName())) {
                    errStat = value;
                }
            }

            // After merging cumulated stats recalculate ERate and Avg
            List<StatValueAO> cumulatedValues = new ArrayList<>();
            for (StatValueAO value : decorator.getCumulatedStat().getValues()) {
                try {
                    if ("avg".equalsIgnoreCase(value.getName())) {
                        Number averageSumNumber = NumberFormat.getNumberInstance(Locale.US).parse(value.getValue());
                        final DoubleValueAO average = new DoubleValueAO(value.getName(), averageSumNumber.doubleValue() / cumulatedStatsCounter);
                        cumulatedValues.add(average);
                    } else if ("erate".equalsIgnoreCase(value.getName()) && reqStat != null && errStat != null) {
                        Number errNumber = NumberFormat.getNumberInstance(Locale.US).parse(errStat.getValue());
                        Number reqNumber = NumberFormat.getNumberInstance(Locale.US).parse(reqStat.getValue());
                        final DoubleValueAO erate = new DoubleValueAO(value.getName(), errNumber.doubleValue() * 100 / reqNumber.doubleValue());
                        cumulatedValues.add(erate);
                    } else {
                        cumulatedValues.add(value);
                    }
                }
                catch (ParseException ex) {
                    LOGGER.warn("Can't parse stat values", ex);
                }
            }

            decorator.getCumulatedStat().setValues(cumulatedValues);
        }
    }

    /**
     * Removes cumulated stats from producers inside decorator.
     * @param decorator {@link ProducerDecoratorBean}
     */
    private void removeCumulatedStats(ProducerDecoratorBean decorator) {
        if (decorator == null) {
            LOGGER.warn("Decorator is empty");
            return;
        }

        for (ProducerAO producer : decorator.getProducers()) {
            for (Iterator<StatLineAO> statLineIterator = producer.getLines().listIterator(); statLineIterator.hasNext(); ) {
                if (CUMULATED_STAT_NAME_VALUE.equals(statLineIterator.next().getStatName())) {
                    statLineIterator.remove();
                }
            }
        }
    }

    /**
     * Checks whether given decorator stat lines contain cumulated stat.
     * @param decorator {@link ProducerDecoratorBean}
     */
    private boolean hasCumulatedStat(ProducerDecoratorBean decorator) {
        for (ProducerAO producer : decorator.getProducers()) {
            for (StatLineAO line : producer.getLines()) {
                if (CUMULATED_STAT_NAME_VALUE.equals(line.getStatName())) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Checks whether given decorator stat lines contain something besides cumulated stat.
     * @param decorator {@link ProducerDecoratorBean}
     */
    private boolean hasAnyStat(ProducerDecoratorBean decorator) {
        for (ProducerAO producer : decorator.getProducers()) {
            for (StatLineAO line : producer.getLines()) {
                if (!CUMULATED_STAT_NAME_VALUE.equals(line.getStatName())) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Merges two {@link StatValueAO} lists, by cumulating corresponding values.
     * @param firstStat List of {@link StatValueAO}
     * @param secondStat List of {@link StatValueAO}
     */
    private List<StatValueAO> mergeStatValues(List<StatValueAO> firstStat, List<StatValueAO> secondStat) {
        // If stats are null or not of the same type return nothing
        if (firstStat == null || secondStat == null) {
            return new ArrayList<>();
        }

        List<StatValueAO> result = new ArrayList<>();
        for (StatValueAO firstStatValue : firstStat) {
            for (StatValueAO secondStatValue : secondStat) {
                // If it's one stat value type cumulate them
                if (firstStatValue.getName().equals(secondStatValue.getName())) {
                    try {
                        // Stat value equals NoR, just set it
                        if ("NoR".equals(firstStatValue.getValue()) || "NoR".equals(secondStatValue.getValue())) {
                            final StatValueAO norValue = new LongValueAO(firstStatValue.getName(), Long.MAX_VALUE);
                            result.add(norValue);
                        } else if (firstStatValue instanceof LongValueAO && secondStatValue instanceof LongValueAO) {
                            Number firstStatNumber = NumberFormat.getNumberInstance(Locale.US).parse(firstStatValue.getValue());
                            Number secondStatNumber = NumberFormat.getNumberInstance(Locale.US).parse(secondStatValue.getValue());
                            final StatValueAO sumValue = new LongValueAO(firstStatValue.getName(), firstStatNumber.longValue() + secondStatNumber.longValue());

                            result.add(sumValue);
                        } else if (firstStatValue instanceof DoubleValueAO && secondStatValue instanceof DoubleValueAO) {
                            Number firstStatNumber = NumberFormat.getNumberInstance(Locale.US).parse(firstStatValue.getValue());
                            Number secondStatNumber = NumberFormat.getNumberInstance(Locale.US).parse(secondStatValue.getValue());
                            final StatValueAO sumValue = new DoubleValueAO(firstStatValue.getName(), firstStatNumber.doubleValue() + secondStatNumber.doubleValue());

                            result.add(sumValue);
                        }
                    }
                    catch (ParseException ex) {
                        LOGGER.warn("Can't parse stat values: " + firstStatValue.getValue() + " and " + secondStatValue.getValue());
                    }
                }
            }
        }

        return result;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getLinkToCurrentPage(HttpServletRequest req) {
        return "mskShowCumulatedProducers" + '?' + PARAM_DECORATOR + '=' + req.getParameter(PARAM_DECORATOR) +
                (!StringUtils.isEmpty(req.getParameter(PARAM_CATEGORY)) ? ('&' + PARAM_CATEGORY + '=' + req.getParameter(PARAM_CATEGORY)) : "") +
                (!StringUtils.isEmpty(req.getParameter(PARAM_SUBSYSTEM)) ? ('&' + PARAM_SUBSYSTEM + '=' + req.getParameter(PARAM_SUBSYSTEM)) : "");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected NaviItem getCurrentNaviItem() {
        return NaviItem.PRODUCERS;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected String getPageName() {
        return "cumulated-producers";
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected boolean exportSupported() {
        return true;
    }

}
