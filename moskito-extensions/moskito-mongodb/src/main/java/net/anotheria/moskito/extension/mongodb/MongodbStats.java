package net.anotheria.moskito.extension.mongodb;

import net.anotheria.moskito.core.decorators.DecoratorRegistryFactory;
import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.producers.AbstractStats;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.stats.impl.StatValueFactory;
import net.anotheria.moskito.core.util.MoskitoWebUi;
import net.anotheria.moskito.extension.mongodb.decorator.MongodbStatsDecorator;
import net.anotheria.util.StringUtils;

/**
 * Container for mongodb stats
 */
public class MongodbStats extends AbstractStats {

    private StatValue flushes;

    private StatValue total_ms_write;

    private StatValue avg_ms_write;

    private StatValue last_ms_write;

    private StatValue current_connections;

    private StatValue available_connections;

    private StatValue total_created_connections;

    public MongodbStats(String aName) {
        super(aName);
        this.flushes = newIntStatValue(MongoStatsFields.FLUSHES.toString());
        this.total_ms_write = newIntStatValue(MongoStatsFields.TOTAL_MS_WRITE.toString());
        this.avg_ms_write = newDoubleStatValue(MongoStatsFields.AVG_MS_WRITE.toString());
        this.last_ms_write = newIntStatValue(MongoStatsFields.LAST_MS_WRITE.toString());
        this.current_connections = newIntStatValue(MongoStatsFields.CURRENT_CONNECTIONS.toString());
        this.available_connections = newIntStatValue(MongoStatsFields.AVAIlABLE_CONNECTIONS.toString());
        this.total_created_connections = newIntStatValue(MongoStatsFields.TOTAL_CREATED_CONNECTIONS.toString());
    }

    public StatValue getFlushes() {
        return flushes;
    }

    public void setFlushes(StatValue flushes) {
        this.flushes = flushes;
    }

    public StatValue getTotal_ms_write() {
        return total_ms_write;
    }

    public void setTotal_ms_write(StatValue total_ms_write) {
        this.total_ms_write = total_ms_write;
    }

    public StatValue getAvg_ms_write() {
        return avg_ms_write;
    }

    public void setAvg_ms_write(StatValue avg_ms_write) {
        this.avg_ms_write = avg_ms_write;
    }

    public StatValue getLast_ms_write() {
        return last_ms_write;
    }

    public void setLast_ms_write(StatValue last_ms_write) {
        this.last_ms_write = last_ms_write;
    }

    public StatValue getCurrent_connections() {
        return current_connections;
    }

    public void setCurrent_connections(StatValue current_connections) {
        this.current_connections = current_connections;
    }

    public StatValue getAvailable_connections() {
        return available_connections;
    }

    public void setAvailable_connections(StatValue available_connections) {
        this.available_connections = available_connections;
    }

    public StatValue getTotal_created_connections() {
        return total_created_connections;
    }

    public void setTotal_created_connections(StatValue total_created_connections) {
        this.total_created_connections = total_created_connections;
    }

    private StatValue newIntStatValue(String valueName) {
        StatValue sv = StatValueFactory.createStatValue(0, valueName, Constants.getDefaultIntervals());
        addStatValues(sv);
        return sv;
    }

    private StatValue newDoubleStatValue(String valueName) {
        StatValue sv = StatValueFactory.createStatValue(0.0d, valueName, Constants.getDefaultIntervals());
        addStatValues(sv);
        return sv;
    }

    @Override
    public String getValueByNameAsString(String valueName, String intervalName, TimeUnit timeUnit) {
        if (StringUtils.isEmpty(valueName)) {
            throw new AssertionError("Value name can not be null or empty");
        }
        if (valueName.equals(MongoStatsFields.FLUSHES.toString())) {
            return getFlushes().getValueAsString(intervalName);
        }
        if (valueName.equals(MongoStatsFields.TOTAL_MS_WRITE.toString())) {
            return "" + timeUnit.transformMillis(getTotal_ms_write().getValueAsLong(intervalName));
        }
        if (valueName.equals(MongoStatsFields.AVG_MS_WRITE.toString())) {
            return "" + timeUnit.transformMillis(getAvg_ms_write().getValueAsDouble(intervalName));
        }
        if (valueName.equals(MongoStatsFields.LAST_MS_WRITE.toString())) {
            return "" + timeUnit.transformMillis(getLast_ms_write().getValueAsDouble(intervalName));
        }
        if (valueName.equals(MongoStatsFields.CURRENT_CONNECTIONS.toString())) {
            return getCurrent_connections().getValueAsString(intervalName);
        }
        if (valueName.equals(MongoStatsFields.AVAIlABLE_CONNECTIONS.toString())) {
            return getAvailable_connections().getValueAsString(intervalName);
        }
        if (valueName.equals(MongoStatsFields.TOTAL_CREATED_CONNECTIONS.toString())) {
            return getTotal_created_connections().getValueAsString(intervalName);
        }
        return super.getValueByNameAsString(valueName, intervalName, timeUnit);
    }

    @Override
    public String toStatsString(String aIntervalName, TimeUnit unit) {
        final StringBuilder sb = new StringBuilder("MongodbStats{");
        sb.append("flushes=").append(flushes.getValueAsString(aIntervalName));
        sb.append(", total_ms_write=").append(total_ms_write.getValueAsString(aIntervalName));
        sb.append(", avg_ms_write=").append(avg_ms_write.getValueAsString(aIntervalName));
        sb.append(", last_ms_write=").append(last_ms_write.getValueAsString(aIntervalName));
        sb.append(", current_connections=").append(current_connections.getValueAsString(aIntervalName));
        sb.append(", available_connections=").append(available_connections.getValueAsString(aIntervalName));
        sb.append(", total_created_connections=").append(total_created_connections.getValueAsString(aIntervalName));
        sb.append('}');
        return sb.toString();
    }

    /* if you have MoSKito WebUI this block will register stats decorator when the class is loaded at the first time */
    static {
        if (MoskitoWebUi.isPresent()) {
            new StatsDecoratorRegistrator().register();
        }
    }

    /* will be initialized only if MoSKito WebUI is embedded into application */
    private static final class StatsDecoratorRegistrator {
        public void register() {
            DecoratorRegistryFactory.getDecoratorRegistry().addDecorator(MongodbStats.class, new MongodbStatsDecorator());
        }
    }

    private enum MongoStatsFields {
        FLUSHES("flushes"),
        TOTAL_MS_WRITE("totalMsWrite"),
        AVG_MS_WRITE("avgMsWrite"),
        LAST_MS_WRITE("lastMsWrite"),
        CURRENT_CONNECTIONS("currentConnections"),
        AVAIlABLE_CONNECTIONS("availableConnections"),
        TOTAL_CREATED_CONNECTIONS("totalCreatedConnections");

        private String fieldName;

        MongoStatsFields(String fieldName) {
            this.fieldName = fieldName;
        }

        @Override
        public String toString() {
            return fieldName;
        }
    }
}
