package net.anotheria.moskito.core.config.producers;

import net.anotheria.moskito.core.stats.TimeUnit;
import org.configureme.annotations.Configure;
import org.configureme.annotations.ConfigureMe;

/**
 * @author strel
 */
@ConfigureMe(allfields = true)
public class ProducerConfig {

    /**
     * Producer name.
     */
    @Configure
    private String name;

    /**
     * Name of the interval. 5m is default.
     */
    @Configure
    private String intervalName = "5m";

    /**
     * Timeunit, default is millis.
     */
    @Configure
    private String timeUnit = TimeUnit.MILLISECONDS.name();


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntervalName() {
        return intervalName;
    }

    public void setIntervalName(String intervalName) {
        this.intervalName = intervalName;
    }

    public String getTimeUnit() {
        return timeUnit;
    }

    public void setTimeUnit(String timeUnit) {
        this.timeUnit = timeUnit;
    }


    @Override
    public String toString() {
        return "ProducerConfig{" +
                "name='" + name + '\'' +
                ", intervalName='" + intervalName + '\'' +
                ", timeUnit='" + timeUnit + '\'' +
                '}';
    }
}
