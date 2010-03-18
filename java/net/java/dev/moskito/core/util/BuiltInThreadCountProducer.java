package net.java.dev.moskito.core.util;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import net.java.dev.moskito.core.predefined.ThreadCountStats;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.core.registry.IProducerRegistry;
import net.java.dev.moskito.core.registry.ProducerRegistryFactory;

public class BuiltInThreadCountProducer implements IStatsProducer{
	
	private List<IStats> statsList;
	private ThreadCountStats stats;
	
	private static final Timer timer = new Timer("MoskitoMemoryPoolReader", true);

	private ThreadMXBean threadMxBean;
	
	BuiltInThreadCountProducer(){
		
		stats = new ThreadCountStats();
		
		statsList = new ArrayList<IStats>();
		statsList.add(stats);
		
		threadMxBean = ManagementFactory.getThreadMXBean();
		
		IProducerRegistry reg = ProducerRegistryFactory.getProducerRegistryInstance();
		reg.registerProducer(this);
		
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				readThreads();
			}
		}, 0, 1000L*60);
		readThreads();
	}
	
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
