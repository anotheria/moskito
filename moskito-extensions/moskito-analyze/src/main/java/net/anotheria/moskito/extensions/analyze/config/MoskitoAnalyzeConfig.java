package net.anotheria.moskito.extensions.analyze.config;


import org.configureme.ConfigurationManager;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;
import org.configureme.annotations.DontConfigure;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Config for MoSKitoAnalyze plugin.
 *
 * @author esmakula
 */
@ConfigureMe(name = "moskito-analyze-plugin")
public class MoskitoAnalyzeConfig {

    @Configure
    private String url;

    @Configure
    private AnalyzeChart[] charts;

    @DontConfigure
    private Map<String, List<AnalyzeChart>> chartsByProducer;

    @DontConfigure
    private Map<String, Set<String>> chartsToRequestByProducer;

    /**
     * Monitor object.
     */
    private static final Object monitor = new Object();

    /**
     * {@link MoskitoAnalyzeConfig} instance
     */
    private static MoskitoAnalyzeConfig instance;

    public static MoskitoAnalyzeConfig getInstance() {
        if (instance != null)
            return instance;
        synchronized (monitor) {
            if (instance != null)
                return instance;
            instance = new MoskitoAnalyzeConfig();
            try {
                ConfigurationManager.INSTANCE.configure(instance);
                createAdditionalData();
            } catch (Exception e) {
                LoggerFactory.getLogger(MoskitoAnalyzeConfig.class).warn("Configuration failed. Relying on defaults. " + e.getMessage());
            }
            return instance;
        }
    }

    private static void createAdditionalData() {
        Map<String, List<AnalyzeChart>> chartsByProducer = new HashMap<>();
        Map<String, Set<String>> chartNameIntervalByProducer = new HashMap<>();

        instance.chartsByProducer = chartsByProducer;
        instance.chartsToRequestByProducer = chartNameIntervalByProducer;
        for (AnalyzeChart chart : instance.charts) {
            List<AnalyzeChart> charts = chartsByProducer.get(chart.getProducerName());
            Set<String> chartNameInterval = chartNameIntervalByProducer.get(chart.getProducerName());
            if (charts == null){
                charts = new ArrayList<>();
                chartNameInterval = new HashSet<>();
                chartsByProducer.put(chart.getProducerName(), charts);
                chartNameIntervalByProducer.put(chart.getProducerName(), chartNameInterval);
            }
            charts.add(chart);
            chartNameInterval.add(chart.getChartName() + '.' + chart.getIntervalName());
        }
    }

    private MoskitoAnalyzeConfig(){
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public AnalyzeChart[] getCharts() {
        return charts;
    }

    public void setCharts(AnalyzeChart[] charts) {
        this.charts = charts;
    }

    public List<AnalyzeChart> getChartsDataByProducer(String producer) {
        return chartsByProducer.get(producer) == null ? Collections.EMPTY_LIST : chartsByProducer.get(producer);
    }

    public Set<String> getChartsByProducer(String producer) {
        return chartsToRequestByProducer.get(producer) == null ? Collections.EMPTY_SET : chartsToRequestByProducer.get(producer);
    }
}
