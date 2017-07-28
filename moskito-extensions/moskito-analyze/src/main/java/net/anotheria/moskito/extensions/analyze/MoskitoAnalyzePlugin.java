package net.anotheria.moskito.extensions.analyze;

import net.anotheria.moskito.core.plugins.AbstractMoskitoPlugin;
import net.anotheria.moskito.extensions.analyze.config.AnalyzeChart;
import net.anotheria.moskito.extensions.analyze.config.MoskitoAnalyzeConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * MoSKito Analyze plugin.
 *
 * @author esmakula
 */
public class MoskitoAnalyzePlugin extends AbstractMoskitoPlugin {

    /**
     * Logger.
     */
    private static Logger log = LoggerFactory.getLogger(MoskitoAnalyzePlugin.class);
    /**
     * Charts wrapper by producer.
     */
    private Map<String, AnalyzeProducerChartsWrapper> chartsWrapperByProducer = new HashMap<>();

    @Override
    public void initialize() {
        fillData(MoskitoAnalyzeConfig.getInstance());
    }

    @Override
    public void deInitialize() {
        chartsWrapperByProducer.clear();
    }

    private void fillData(MoskitoAnalyzeConfig config) {
        for (AnalyzeChart chart : config.getCharts()) {
            AnalyzeProducerChartsWrapper wrapper = chartsWrapperByProducer.get(chart.getProducerName());
            if (wrapper == null){
                wrapper = new AnalyzeProducerChartsWrapper();
                wrapper.setUrl(config.getUrl());
                wrapper.setHosts(config.getHosts() == null ? new ArrayList<String>() : Arrays.asList(config.getHosts()));
                wrapper.setChartTypes(new HashSet<String>());
                wrapper.setCharts(new ArrayList<AnalyzeChart>());
                chartsWrapperByProducer.put(chart.getProducerName(), wrapper);
            }
            wrapper.getCharts().add(chart);
            wrapper.getChartTypes().add(chart.getChartName() + '.' + chart.getIntervalName());
        }
    }

    public AnalyzeProducerChartsWrapper getChartsByProducer(String producer) {
        return chartsWrapperByProducer.get(producer);
    }
}
