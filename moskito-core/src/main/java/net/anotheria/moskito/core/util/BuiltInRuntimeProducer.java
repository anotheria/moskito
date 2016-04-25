package net.anotheria.moskito.core.util;

import net.anotheria.moskito.core.predefined.RuntimeStats;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

/**
 * Builtin producer for values supplied by jmx for the runtime.
 * @author lrosenberg
 */
public class BuiltInRuntimeProducer extends AbstractBuiltInProducer  implements IStatsProducer, BuiltInProducer{
	/**
	 * Associated stats.
	 */
	private RuntimeStats stats;
	/**
	 * Stats container
	 */
	private List<IStats> statsList;

	/**
	 * The monitored pool.
	 */
	private RuntimeMXBean mxBean;

	public BuiltInRuntimeProducer(){
		mxBean = ManagementFactory.getRuntimeMXBean();
		statsList = new ArrayList<IStats>(1);
		stats = new RuntimeStats();
		statsList.add(stats);
		
		BuiltinUpdater.addTask(new TimerTask() {
			@Override
			public void run() {
				readMbean();
			}});
		
		ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(this);
	}
	
	@Override
	public String getCategory() {
		return "runtime";
	}

	@Override
	public String getProducerId() {
		return "Runtime";
	}

	@Override
	public List<IStats> getStats() {
		return statsList;
	}

	private void readMbean() {
		long starttime = mxBean.getStartTime();
		long uptime = mxBean.getUptime();
		String name = mxBean.getName();
		stats.update(name, starttime, uptime);
			
	}
}
