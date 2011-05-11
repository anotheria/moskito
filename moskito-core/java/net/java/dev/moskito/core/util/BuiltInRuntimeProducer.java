package net.java.dev.moskito.core.util;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.TimerTask;

import net.java.dev.moskito.core.predefined.RuntimeStats;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.core.registry.ProducerRegistryFactory;

import org.apache.log4j.Logger;

/**
 * Builtin producer for values supplied by jmx for the operation system.
 * @author lrosenberg
 */
public class BuiltInRuntimeProducer implements IStatsProducer{
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
	
	private static Logger log = Logger.getLogger(BuiltInRuntimeProducer.class);
	
	/**
	 * Creates a new producers object for a given pool.
	 * @param aPool
	 */
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

	@Override
	public String getSubsystem() {
		return SUBSYSTEM_BUILTIN;
	}
	
	private void readMbean() {
		long starttime = mxBean.getStartTime();
		long uptime = mxBean.getUptime();
		String name = mxBean.getName();
		stats.update(name, starttime, uptime);
			
	}
}
