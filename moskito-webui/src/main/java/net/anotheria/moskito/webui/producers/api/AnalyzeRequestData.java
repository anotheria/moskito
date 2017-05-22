package net.anotheria.moskito.webui.producers.api;

import java.util.List;

/**
 * Represents data for moskito-analyze request.
 *
 * @esmakula
 */
public class AnalyzeRequestData {

    private String chart;
    private String interval;
    private String start;
    private String end;
    private List<ChartData> stats;

    public String getChart() {
        return chart;
    }

    public void setChart(String chart) {
        this.chart = chart;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public List<ChartData> getStats() {
        return stats;
    }

    public void setStats(List<ChartData> stats) {
        this.stats = stats;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getJson() {
        return '{' +
                "\"chart\" : \"" + chart + '"' +
                ", \"interval\" : \"" + interval + '"' +
                ", \"start\"  : \"" + start + '"' +
                ", \"end\"  : \"" + end + '"' +
                ", \"stats\"  : " + getStatsJson() +
                '}';
    }

    public String getStatsJson() {
        StringBuilder sb = new StringBuilder();
        sb.append('[');
        for (ChartData chartData: stats){
            sb.append(chartData.toJson());
            sb.append(", ");
        }
        sb.setLength(sb.length() - 2);
        sb.append(']');
        return sb.toString();
    }
}
