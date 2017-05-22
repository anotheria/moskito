package net.anotheria.moskito.extensions.analyze.config;

import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

/**
 * Analyze chart config.
 *
 * @author esmakula
 */
@ConfigureMe
public class AnalyzeChart {

    @Configure
    private String name;

    @Configure
    private String producerName;

    @Configure
    private String statName;

    @Configure
    private String valueName;

    @Configure
    private String intervalName;

    @Configure
    private String chartName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProducerName() {
        return producerName;
    }

    public void setProducerName(String producerName) {
        this.producerName = producerName;
    }

    public String getStatName() {
        return statName;
    }

    public void setStatName(String statName) {
        this.statName = statName;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public String getIntervalName() {
        return intervalName;
    }

    public void setIntervalName(String intervalName) {
        this.intervalName = intervalName;
    }

    public String getChartName() {
        return chartName;
    }

    public void setChartName(String chartName) {
        this.chartName = chartName;
    }
}
