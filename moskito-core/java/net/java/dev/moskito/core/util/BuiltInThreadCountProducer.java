package net.java.dev.moskito.core.util;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

import net.java.dev.moskito.core.predefined.ThreadCountStats;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.core.registry.IProducerRegistry;
import net.java.dev.moskito.core.registry.ProducerRegistryFactory;

/**
 * A built-in producer that counts threads based on jmx provided beans.
 * @author lrosenberg
 *
 */
public class BuiltInThreadCountProducer extends AbstractBuiltInProducer implements IStatsProducer, BuiltInProducer{
	/**
	 * Stats objects as list.
	 */
	private List<IStats> statsList;
	/**
	 * ThreadCountStats object.
	 */
	private ThreadCountStats stats;
	/**
	 * Reference to the jmx bean.
	 */
	private ThreadMXBean threadMxBean;
	/**
	 * Package protected constructor.
	 */
	BuiltInThreadCountProducer(){
		
		stats = new ThreadCountStats();
		
		statsList = new CopyOnWriteArrayList<IStats>();
		statsList.add(stats);
		
		threadMxBean = ManagementFactory.getThreadMXBean();
		
		IProducerRegistry reg = ProducerRegistryFactory.getProducerRegistryInstance();
		reg.registerProducer(this);
		
		BuiltinUpdater.addTask(new TimerTask() {
			@Override
			public void run() {
				readThreads();
			}
		});
	}
	
	/**
	 * Reads and updates the thread info.
	 */
	private void readThreads(){
		stats.update(threadMxBean.getTotalStartedThreadCount(), threadMxBean.getDaemonThreadCount(), threadMxBean.getThreadCount());
	}

	@Override
	public String getCategory() {
		return "threads";
	}

	@Override
	public String getProducerId() {
		return "ThreadCount";
	}

	@Override
	public List<IStats> getStats() {
		return statsList;
	}

	@Override
	public String getSubsystem() {
		return SUBSYSTEM_BUILTIN;
	}
}
