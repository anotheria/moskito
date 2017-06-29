package net.anotheria.moskito.core.predefined;

import net.anotheria.moskito.core.decorators.DecoratorRegistryFactory;
import net.anotheria.moskito.core.decorators.predefined.GlobalRequestProcessorStatsDecorator;
import net.anotheria.moskito.core.producers.AbstractStats;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.StatValueTypes;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.stats.impl.StatValueFactory;
import net.anotheria.moskito.core.util.MoskitoWebUi;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Stats object for Global Request Processor related values.
 *
 * @author esmakula
 */
public class GlobalRequestProcessorStats extends AbstractStats {

    /* if you have MoSKito WebUI this block will register stats decorator when the class is loaded at the first time */
    static {
        if (MoskitoWebUi.isPresent()) {
            DecoratorRegistryFactory.getDecoratorRegistry().addDecorator(GlobalRequestProcessorStats.class, new GlobalRequestProcessorStatsDecorator());
        }
    }

    /**
     * Request count.
     */
    private final StatValue requestCount;

    /**
     * Max time.
     */
    private final StatValue maxTime;

    /**
     * Bytes received amount.
     */
    private final StatValue bytesReceived;

    /**
     * Bytes sent amount.
     */
    private final StatValue bytesSent;

    /**
     * Processing time.
     */
    private final StatValue processingTime;

    /**
     * Error count.
     */
    private final StatValue errorCount;

    /**
     * Default Constructor.
     * @param aName stat name
     */
    public GlobalRequestProcessorStats(String aName) {
        this(aName, Constants.getDefaultIntervals());
    }

    /**
     * Constructor.
     * @param selectedIntervals selected intervals.
     */
    private GlobalRequestProcessorStats(String aName, Interval[] selectedIntervals) {
        super(aName);
        requestCount = StatValueFactory.createStatValue(StatValueTypes.DIFFLONG, "requestCount", selectedIntervals);
        maxTime = StatValueFactory.createStatValue(StatValueTypes.DIFFLONG, "maxTime", selectedIntervals);
        bytesReceived = StatValueFactory.createStatValue(StatValueTypes.DIFFLONG, "bytesReceived", selectedIntervals);
        bytesSent = StatValueFactory.createStatValue(StatValueTypes.DIFFLONG, "bytesSent", selectedIntervals);
        processingTime = StatValueFactory.createStatValue(StatValueTypes.DIFFLONG, "processingTime", selectedIntervals);
        errorCount = StatValueFactory.createStatValue(StatValueTypes.DIFFLONG, "errorCount", selectedIntervals);

        addStatValues(requestCount, maxTime, bytesReceived, bytesSent, processingTime, errorCount);

    }

    @Override
    public String toStatsString(String intervalName, TimeUnit unit) {
        StringBuilder ret = new StringBuilder();

        ret.append(getName()).append(' ');
        ret.append(" RequestCount: ").append(requestCount.getValueAsLong(intervalName));
        ret.append(" MaxTime: ").append(maxTime.getValueAsLong(intervalName));
        ret.append(" BytesReceived: ").append(bytesReceived.getValueAsLong(intervalName));
        ret.append(" BytesSent: ").append(bytesSent.getValueAsLong(intervalName));
        ret.append(" ProcessingTime: ").append(processingTime.getValueAsLong(intervalName));
        ret.append(" ErrorCount: ").append(errorCount.getValueAsLong(intervalName));

        return ret.toString();
    }

    @Override
    public String getValueByNameAsString(String valueName, String intervalName, TimeUnit timeUnit) {

        if (valueName == null)
            throw new AssertionError("Value name can't be null");
        valueName = valueName.toLowerCase();

        if (valueName.equals("requestcount") || valueName.equals("request count"))
            return String.valueOf(getRequestCount(intervalName));
        if (valueName.equals("maxtime") || valueName.equals("max time"))
            return String.valueOf(getMaxTime(intervalName));
        if (valueName.equals("bytesreceived") || valueName.equals("bytes received"))
            return String.valueOf(getBytesReceived(intervalName));
        if (valueName.equals("bytessent") || valueName.equals("bytes sent"))
            return String.valueOf(getBytesSent(intervalName));
        if (valueName.equals("processingtime") || valueName.equals("processing time"))
            return String.valueOf(getProcessingTime(intervalName));
        if (valueName.equals("errorcount") || valueName.equals("error count"))
            return String.valueOf(getErrorCount(intervalName));

        return super.getValueByNameAsString(valueName, intervalName, timeUnit);
    }

    /**
     * Value names list.
     */
    private static final List<String> VALUE_NAMES = Collections.unmodifiableList(Arrays.asList(
            "RequestCount",
            "MaxTime",
            "BytesReceived",
            "BytesSent",
            "ProcessingTime",
            "ErrorCount"
    ));

    @Override
    public List<String> getAvailableValueNames() {
        return VALUE_NAMES;
    }

    /**
     * Updates stats.
     * @param aRequestCount request count
     * @param aMaxTime  max time
     * @param aBytesReceived bytes received
     * @param aBytesSent bytes sent
     * @param aProcessingTime processing time
     * @param aErrorCount error count
     */
    public void update(long aRequestCount, long aMaxTime, long aBytesReceived, long aBytesSent, long aProcessingTime, long aErrorCount) {
        requestCount.setValueAsLong(aRequestCount);
        maxTime.setValueAsLong(aMaxTime);
        bytesReceived.setValueAsLong(aBytesReceived);
        bytesSent.setValueAsLong(aBytesSent);
        processingTime.setValueAsLong(aProcessingTime);
        errorCount.setValueAsLong(aErrorCount);
    }

    /**
     * Returns request count by selected interval.
     * @param intervalName interval name
     * @return request count long value
     */
    public long getRequestCount(String intervalName) {
        return requestCount.getValueAsLong(intervalName);
    }

    /**
     * Returns max time value by selected interval.
     * @param intervalName interval name
     * @return max time long value
     */
    public long getMaxTime(String intervalName) {
        return maxTime.getValueAsLong(intervalName);
    }

    /**
     * Returns bytes received value by selected interval.
     * @param intervalName interval name
     * @return bytes received long value
     */
    public long getBytesReceived(String intervalName) {
        return bytesReceived.getValueAsLong(intervalName);
    }

    /**
     * Returns bytes sent value by selected interval.
     * @param intervalName interval name
     * @return bytes sent long value
     */
    public long getBytesSent(String intervalName) {
        return bytesSent.getValueAsLong(intervalName);
    }

    /**
     * Returns processing time value by selected interval.
     * @param intervalName interval name
     * @return processing time long value
     */
    public long getProcessingTime(String intervalName) {
        return processingTime.getValueAsLong(intervalName);
    }

    /**
     * Returns error count by selected interval.
     * @param intervalName interval name
     * @return error count long value
     */
    public long getErrorCount(String intervalName) {
        return errorCount.getValueAsLong(intervalName);
    }
}
