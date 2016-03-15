package net.anotheria.moskito.webui.util.offlinecharts;

/**
 * Defines how a line should look like.
 * For now we only define the name of the line, but we could also define the color etc.
 */
public class OfflineChartLineDefinition {
    /**
     * Name of the chart line.
     */
    private String lineName;

    public OfflineChartLineDefinition(String aLineName){
        lineName = aLineName;
    }

    public String getLineName() {
        return lineName;
    }

    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    @Override public String toString(){
        return lineName;
    }
}