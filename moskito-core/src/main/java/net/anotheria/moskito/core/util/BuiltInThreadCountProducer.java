package net.anotheria.moskito.core.util;

import net.anotheria.moskito.core.predefined.ThreadCountStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.IProducerRegistry;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.List;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * A built-in producer that counts threads based on jmx provided bean.
 * @author lrosenberg
 *
 */
public class BuiltInThreadCountProducer extends AbstractBuiltInProducer<ThreadCountStats> implements IStatsProducer<ThreadCountStats>, BuiltInProducer{
	/**
	 * Stats objects as list.
	 */
	private List<ThreadCountStats> statsList;
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
		
		statsList = new CopyOnWriteArrayList<ThreadCountStats>();
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
	public List<ThreadCountStats> getStats() {
		return statsList;
	}
}
