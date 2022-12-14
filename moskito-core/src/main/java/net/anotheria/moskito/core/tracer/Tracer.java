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
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 04.05.15 17:40
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
