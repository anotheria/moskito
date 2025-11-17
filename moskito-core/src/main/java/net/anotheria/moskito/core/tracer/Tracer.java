package net.anotheria.moskito.core.tracer;

import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.tracing.TracingConfiguration;
import net.anotheria.moskito.core.journey.JourneyManager;
import net.anotheria.moskito.core.journey.JourneyManagerFactory;
import net.anotheria.util.sorter.StaticQuickSorter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Collects and manages execution traces for a specific producer or producer statistic.
 *
 * <p>A Tracer captures detailed execution information (traces) for monitored operations,
 * providing insight into method calls, timing, and execution paths. Each tracer is associated
 * with a specific producer and optionally a specific statistic within that producer.
 *
 * <p><b>Trace Management:</b> The tracer maintains a collection of {@link Trace} objects
 * and automatically manages memory by applying a shrinking strategy when the trace count
 * exceeds configured limits. Two strategies are supported:
 * <ul>
 *   <li><b>KEEPLONGEST</b> - Retains traces with the longest execution duration</li>
 *   <li><b>FIFO</b> - Keeps the most recent traces (First-In-First-Out)</li>
 * </ul>
 *
 * <p><b>Thread Safety:</b> This class is thread-safe. Trace collection uses a
 * {@link CopyOnWriteArrayList} for concurrent access, and trace shrinking operations
 * are protected by a {@link ReadWriteLock}.
 *
 * <p><b>Enabling/Disabling:</b> Tracers can be dynamically enabled or disabled. When disabled,
 * no new traces are collected, but existing traces remain accessible.
 *
 * <p><b>Example Usage:</b>
 * <pre>
 * // Get a tracer for a specific producer
 * Tracer tracer = Tracers.getTracer("myProducer", "myStatistic");
 *
 * // Tracer automatically collects traces when monitoring is active
 * // Retrieve collected traces
 * List&lt;Trace&gt; traces = tracer.getTraces();
 *
 * // Disable/enable trace collection
 * tracer.setEnabled(false);
 * </pre>
 *
 * @author lrosenberg
 * @since 04.05.15 17:40
 * @see Tracers
 * @see Trace
 * @see TracingConfiguration
 */
public class Tracer {
	/**
	 * Associated producer for this tracer.
	 */
	private String producerId;
	/**
	 * Associated stat name for this tracer. If null, the tracer applies to the whole producer (pre-3.0 behaviour).
	 */
	private String statName;
	/**
	 * If true the tracer is currently enabled. Disabled tracer doesn't collect any futher traces.
	 */
	private boolean enabled;

	/**
	 * Journey manager is used to add remove journey-steps.
	 */
	private static JourneyManager journeyManager = JourneyManagerFactory.getJourneyManager();

	/**
	 * Sorttype for keep longest strategy.
	 */
	private TraceSortType sortTypeForKeepLongest = new TraceSortType(TraceSortType.SORT_BY_DURATION, TraceSortType.DESC);

	/**
	 * Lock for deletion of traces in case we gather too many traces.
	 */
	private ReadWriteLock resizeLock = new ReentrantReadWriteLock();

	private List<Trace> traces;
	private int totalEntryCount;

	public Tracer(String aProducerId, String aStatName){
		producerId = aProducerId;
		statName = aStatName;
		enabled = true;
		traces = new CopyOnWriteArrayList<Trace>();
	}

	public String getProducerId(){
		return producerId;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public int getEntryCount(){
		return traces == null ? 0 : traces.size();
	}

	public void addTrace(Trace aTrace, int toleratedAmount, int maxAmount){
		totalEntryCount++;
		try {
			resizeLock.writeLock().lock();
			traces.add(aTrace);
			if (traces.size() <= toleratedAmount)
				return;

			TracingConfiguration config = MoskitoConfigurationHolder.getConfiguration().getTracingConfig();
			List<Trace> oldTraces;

			switch (config.getShrinkingStrategy()) {
				case KEEPLONGEST:
					oldTraces = StaticQuickSorter.sort(traces, sortTypeForKeepLongest);
					traces = new CopyOnWriteArrayList<Trace>();
					for (int i = 0; i < oldTraces.size(); i++) {
						if (i < maxAmount) {
							traces.add(oldTraces.get(i));
						} else {
							journeyManager.getOrCreateJourney(Tracers.getJourneyNameForTracers(getTracerId())).removeStepByName(Tracers.getCallName(oldTraces.get(i)));
						}
					}
					break;
				case FIFO:
					oldTraces = traces;
					traces = new CopyOnWriteArrayList<Trace>();
					int offset = toleratedAmount - maxAmount;

					for (int i=0; i<oldTraces.size(); i++){
						if (i>=(1+offset) && i<=(maxAmount+offset)){
							traces.add(oldTraces.get(i));
						}else{
							journeyManager.getOrCreateJourney(Tracers.getJourneyNameForTracers(getTracerId())).removeStepByName(Tracers.getCallName(oldTraces.get(i)));
						}
					}
					break;

				default:
					throw new IllegalArgumentException("Shrinking strategy " + config.getShrinkingStrategy() + " is not supported");
			}
		}finally{
			resizeLock.writeLock().unlock();
		}


	}

	public List<Trace> getTraces(){
		try{
			resizeLock.readLock().lock();
			return traces;
		}finally {
			resizeLock.readLock().unlock();
		}
	}

	public int getTotalEntryCount() {
		return totalEntryCount;
	}

	public void setTotalEntryCount(int totalEntryCount) {
		this.totalEntryCount = totalEntryCount;
	}

	public String getTracerId(){
		return TracerRepository.makeKey(producerId, statName);
	}
}
