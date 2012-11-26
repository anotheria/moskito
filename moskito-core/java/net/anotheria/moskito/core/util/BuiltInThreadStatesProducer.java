package net.anotheria.moskito.core.util;

import net.anotheria.moskito.core.predefined.ThreadStateStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.IProducerRegistry;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 25.11.12 23:55
 */
	public class BuiltInThreadStatesProducer extends AbstractBuiltInProducer implements IStatsProducer<ThreadStateStats>, BuiltInProducer{
	/**
	 * Cumulated stats object.
	 */
	private ThreadStateStats cumulated;
	/**
	 * Stats objects as list.
	 */
	private List<ThreadStateStats> statsList;
	/**
	 * ThreadStateStats map.
	 */
	private Map<Thread.State, ThreadStateStats> statsMap;
	/**
	 * Reference to the jmx bean.
	 */
	private ThreadMXBean threadMxBean;
	/**
	 * Package protected constructor.
	 */
	BuiltInThreadStatesProducer(){

		cumulated = new ThreadStateStats("cumulated");

		statsMap = new HashMap<Thread.State, ThreadStateStats>();
		statsList = new CopyOnWriteArrayList<ThreadStateStats>();
		statsList.add(cumulated);
		for (Thread.State st : Thread.State.values()){
			ThreadStateStats statsObject = new ThreadStateStats(st.name());
			statsMap.put(st, statsObject);
			statsList.add(statsObject);
		}

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
		long ids[] = threadMxBean.getAllThreadIds();

		HashMap<Thread.State, Long> count = new HashMap<Thread.State, Long>();
		for (int i = 0; i<ids.length; i++){
			long id = ids[i];
			ThreadInfo info = threadMxBean.getThreadInfo(id);
			Thread.State state = info.getThreadState();

			Long old = count.get(state);
			if (old==null)
				old = new Long(0);
			count.put(state, old+1);
		}

		long total = 0;
		for (Map.Entry<Thread.State, ThreadStateStats> entry : statsMap.entrySet()){
			Long c = count.get(entry.getKey());
			entry.getValue().updateCurrentValue(c==null ? 0 : c.longValue());
			if (c!=null)
				total += c.longValue();
		}
		cumulated.updateCurrentValue(total);

	}

	@Override
	public String getCategory() {
		return "threads";
	}

	@Override
	public String getProducerId() {
		return "ThreadStates";
	}

	@Override
	public List<ThreadStateStats> getStats() {
		return statsList;
	}

	@Override
	public String getSubsystem() {
		return SUBSYSTEM_BUILTIN;
	}
}
