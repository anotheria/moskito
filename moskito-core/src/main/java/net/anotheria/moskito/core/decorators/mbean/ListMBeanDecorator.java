package net.anotheria.moskito.core.decorators.mbean;

import net.anotheria.moskito.core.decorators.value.StatCaptionBean;
import net.anotheria.moskito.core.decorators.value.StatValueAO;
import net.anotheria.moskito.core.predefined.MBeanStats;
import net.anotheria.moskito.core.stats.TimeUnit;

import java.util.LinkedList;
import java.util.List;

public class ListMBeanDecorator extends MBeanDecorator {

    public ListMBeanDecorator() {
        super(new LinkedList<StatCaptionBean>());
    }

    @Override
    public List<StatValueAO> getValues(MBeanStats stats, String interval, TimeUnit unit) {
        return new LinkedList<>();
    }

}
