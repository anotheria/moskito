package net.anotheria.moskito.extension.apache.decorator;

import net.anotheria.moskito.core.decorators.AbstractDecorator;
import net.anotheria.moskito.core.decorators.value.DoubleValueAO;
import net.anotheria.moskito.core.decorators.value.LongValueAO;
import net.anotheria.moskito.core.decorators.value.StatValueAO;
import net.anotheria.moskito.core.decorators.value.StringValueAO;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.extension.apache.ApacheMetrics;
import net.anotheria.moskito.extension.apache.ApacheStats;

import java.util.ArrayList;
import java.util.List;

/**
 * Decorator for ApacheStats.
 *
 * @author dzhmud
 */
public class ApacheStatsDecorator  extends AbstractDecorator<ApacheStats> {

    public ApacheStatsDecorator() {
        super("Apache",
                ApacheMetrics.getCaptions(),
                ApacheMetrics.getShortExplanations(),
                ApacheMetrics.getExplanations()
        );
    }

    @Override
    public List<StatValueAO> getValues(ApacheStats apacheStats, String interval, TimeUnit unit) {
        List<StatValueAO> bean = new ArrayList<>(getCaptions().size());
        for (ApacheMetrics metric : ApacheMetrics.values()) {
            if (apacheStats.isNeverUpdated()) {
                //display "NoR" to clearly differentiate not connectible/parseable from inactive
                bean.add(new StringValueAO(metric.valueName, "NoR"));
            } else {
                if (metric.isDoubleValue())
                    bean.add(new DoubleValueAO(metric.valueName, apacheStats.getStatValueAsDouble(metric, interval)));
                else if (metric.isIntegerValue())
                    bean.add(new LongValueAO(metric.valueName, apacheStats.getStatValueAsInteger(metric, interval)));
                else if (metric.isStringValue())
                    bean.add(new StringValueAO(metric.valueName, apacheStats.getStatValueAsString(metric, interval)));
                else
                    bean.add(new LongValueAO(metric.valueName, apacheStats.getStatValueAsLong(metric, interval)));
            }
        }
        return bean;
    }

}