package net.anotheria.moskito.core.util;

import net.anotheria.moskito.core.predefined.ThreadStateStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.IProducerRegistry;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * This builtin producer monitors the thread states and how many threads are in each state i.e. BLOCKED, RUNNABLE etc.
 *
 * @author lrosenberg
 * @since 25.11.12 23:55
 */
public class BuiltInThreadStatesProducer extends AbstractBuiltInProducer<ThreadStateStats> implements IStatsProducer<ThreadStateStats>, BuiltInProducer{
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

		statsMap = new EnumMap<>(Thread.State.class);
		statsList = new CopyOnWriteArrayList<>();
		statsList.add(cumulated);
		for (Thread.State state : Thread.State.values()){
			ThreadStateStats statsObject = new ThreadStateStats(state.name());
			statsMap.put(state, statsObject);
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
		long[] ids = threadMxBean.getAllThreadIds();

		Map<Thread.State, Long> count = new EnumMap<>(Thread.State.class);
		for (long id : ids) {
			ThreadInfo info = threadMxBean.getThreadInfo(id);
			if (info != null) {
				Thread.State state = info.getThreadState();

				Long old = count.get(state);
				if (old == null) {
					old = 0L;
				}
				count.put(state, old + 1);
			}
		}

		long total = 0;
		for (Map.Entry<Thread.State, ThreadStateStats> entry : statsMap.entrySet()){
			Long c = count.get(entry.getKey());
			entry.getValue().updateCurrentValue(c==null ? 0 : c);
			if (c!=null)
				total += c;
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

}
