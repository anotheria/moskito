package net.anotheria.moskito.core.util;

import net.anotheria.moskito.core.predefined.GCStats;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

/**
 * Builtin producer for values supplied by GC.
 *
 * @author esmakula
 */
public class BuiltInGCProducer extends AbstractBuiltInProducer implements IStatsProducer, BuiltInProducer {
    /**
     * Stats container
     */
    private List<IStats> iStatsList;

    /**
     * The monitored pool.
     */
    private List<GarbageCollectorMXBean> mxBeans;

    /**
     * Constructor.
     */
    public BuiltInGCProducer() {
        mxBeans = ManagementFactory.getGarbageCollectorMXBeans();
        iStatsList = new ArrayList<>(mxBeans.size());
        for (GarbageCollectorMXBean bean : mxBeans) {
            iStatsList.add(new GCStats(bean.getName()));
        }

        BuiltinUpdater.addTask(new TimerTask() {
            @Override
            public void run() {
                readMbean();
            }
        });

        ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(this);
    }

    @Override
    public String getCategory() {
        return "gc";
    }

    @Override
    public String getProducerId() {
        return "GC";
    }

    @Override
    public List<IStats> getStats() {
        return iStatsList;
    }

    /**
     * Reads the management bean and extracts monitored data on regular base.
     */
    private void readMbean() {
        int i = 0;
        for (GarbageCollectorMXBean bean : mxBeans) {
            GCStats stat = (GCStats) iStatsList.get(i);
            stat.update(bean.getCollectionCount(), bean.getCollectionTime());
            i++;
        }
    }

}
