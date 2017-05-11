package net.anotheria.moskito.extension.nginx.decorator;

import net.anotheria.moskito.core.decorators.AbstractDecorator;
import net.anotheria.moskito.core.decorators.value.DoubleValueAO;
import net.anotheria.moskito.core.decorators.value.LongValueAO;
import net.anotheria.moskito.core.decorators.value.StatValueAO;
import net.anotheria.moskito.core.decorators.value.StringValueAO;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.extension.nginx.NginxMetrics;
import net.anotheria.moskito.extension.nginx.NginxStats;

import java.util.ArrayList;
import java.util.List;

import static net.anotheria.moskito.extension.nginx.NginxMetrics.Strings.*;
import static net.anotheria.moskito.extension.nginx.NginxMetrics.getStrings;

/**
 * Decorator for NginxStats metrics.
 *
 * @author dzhmud
 * @see NginxStats
 * @see NginxMetrics
 */
public class NginxStatsDecorator  extends AbstractDecorator<NginxStats> {

    public NginxStatsDecorator() {
        super("NginxMonitor",
                getStrings(VALUENAME),
                getStrings(SHORTEXPLANATION),
                getStrings(EXPLANATION)
        );
    }

    @Override
    public List<StatValueAO> getValues(NginxStats nginxStats, String interval, TimeUnit unit) {
        List<StatValueAO> bean = new ArrayList<>(getCaptions().size());
        for (NginxMetrics metric : NginxMetrics.values()) {
            if (nginxStats.isNeverUpdated()) {
                bean.add(new StringValueAO(metric.valueName, "NoR"));
            } else {
                if (metric.isDoubleValue())
                    bean.add(new DoubleValueAO(metric.valueName, nginxStats.getStatValueAsDouble(metric, interval)));
                else
                    bean.add(new LongValueAO(metric.valueName, nginxStats.getStatValueAsLong(metric, interval)));
            }
        }
        return bean;
    }

}
