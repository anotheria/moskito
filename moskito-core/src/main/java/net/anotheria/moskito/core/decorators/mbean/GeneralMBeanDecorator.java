package net.anotheria.moskito.core.decorators.mbean;

import net.anotheria.moskito.core.decorators.value.StatCaptionBean;
import net.anotheria.moskito.core.decorators.value.StatValueAO;
import net.anotheria.moskito.core.producers.GenericStats;
import net.anotheria.moskito.core.stats.TimeUnit;

import java.util.LinkedList;
import java.util.List;

/**
 * Decorator that returns empty captions and values.
 * Can be used to generify all mbean stats
 * with different values sets
 */
public class GeneralMBeanDecorator extends MBeanDecorator {

    public GeneralMBeanDecorator() {
        super(new LinkedList<StatCaptionBean>());
    }

    @Override
    public List<StatValueAO> getValues(GenericStats stats, String interval, TimeUnit unit) {
        return new LinkedList<>();
    }

}
