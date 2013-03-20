package net.anotheria.moskito.core.snapshot;

import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.predefined.ServiceStats;
import net.anotheria.moskito.core.predefined.ServiceStatsFactory;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.impl.IntervalRegistry;
import net.anotheria.moskito.core.timing.IUpdateable;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 20.03.13 14:59
 */
public class BaseSnapshotTest {
	protected static OnDemandStatsProducer setupProducer(){
		OnDemandStatsProducer<ServiceStats> producer = new OnDemandStatsProducer<ServiceStats>("testProducerId", "aCategory", "aSubsystem", new ServiceStatsFactory());
		return producer;
	}

	protected static void forceIntervalUpdate(String intervalName){
		IntervalRegistry registry = IntervalRegistry.getInstance();
		Interval interval = registry.getInterval(intervalName);
		((IUpdateable)interval).update();
	}
}
