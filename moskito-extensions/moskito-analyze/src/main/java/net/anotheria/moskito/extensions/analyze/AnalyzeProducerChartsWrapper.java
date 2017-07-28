package net.anotheria.moskito.extensions.analyze;

import net.anotheria.moskito.extensions.analyze.config.AnalyzeChart;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * Analyze charts wrapper by producer.
 *
 * @author esmakula
 */
public class AnalyzeProducerChartsWrapper implements Serializable {

    private static final long serialVersionUID = 6389792198770264055L;

    private String url;

    private List<String> hosts;

    private List<AnalyzeChart> charts;

    private Set<String> chartTypes;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<String> getHosts() {
        return hosts;
    }

    public void setHosts(List<String> hosts) {
        this.hosts = hosts;
    }

    public List<AnalyzeChart> getCharts() {
        return charts;
    }

    public void setCharts(List<AnalyzeChart> charts) {
        this.charts = charts;
    }

    public Set<String> getChartTypes() {
        return chartTypes;
    }

    public void setChartTypes(Set<String> chartTypes) {
        this.chartTypes = chartTypes;
    }
}
