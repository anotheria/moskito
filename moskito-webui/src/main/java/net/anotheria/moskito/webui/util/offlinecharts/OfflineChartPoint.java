package net.anotheria.moskito.webui.util.offlinecharts;

import java.util.List;

public class OfflineChartPoint {
    /**
     * Values for different chart lines. They are in the same order as the OfflineChartLineDefinition in OfflineChart.
     */
    private List<String> values;
    /**
     * Timestamp as string.
     */
    private String timestampAsString;

    /**
     * Timestamp of this point.
     */
    private long timestamp;

    public List<String> getValues() {
        return values;
    }

    public void setValues(List<String> values) {
        this.values = values;
    }

    public String getTimestampAsString() {
        return timestampAsString;
    }

    public void setTimestampAsString(String timestampAsString) {
        this.timestampAsString = timestampAsString;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "OfflineChartPoint{" +
                "values=" + values +
                ", timestampAsString='" + timestampAsString + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}