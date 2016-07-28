package net.anotheria.moskito.webui.util.offlinecharts;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a chart for generation.
 */
public class OfflineChart {
    /**
     * Caption of the chart.
     */
    private String caption;

    /**
     * Definition of chart lines.
     */
    private List<OfflineChartLineDefinition> lineDefinitions = new LinkedList<>();
    /**
     * Points with values for all lines.
     */
    private List<OfflineChartPoint> points = new LinkedList<>();

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public List<OfflineChartLineDefinition> getLineDefinitions() {
        return lineDefinitions;
    }

    public void setLineDefinitions(List<OfflineChartLineDefinition> lineDefinitions) {
        this.lineDefinitions = lineDefinitions;
    }

    public List<OfflineChartPoint> getPoints() {
        return points;
    }

    public void setPoints(List<OfflineChartPoint> points) {
        this.points = points;
    }

    @Override public String toString(){
        return "Chart "+ caption +", LineDefinitions: "+ lineDefinitions +", Points: "+ points;
    }

    public void addLineDefinition(OfflineChartLineDefinition offlineChartLineDefinition) {
        lineDefinitions.add(offlineChartLineDefinition);
    }

    public void addPoint(OfflineChartPoint ocp) {
        points.add(ocp);
    }
}