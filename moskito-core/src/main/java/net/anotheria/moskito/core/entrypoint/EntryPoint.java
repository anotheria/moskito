package net.anotheria.moskito.core.entrypoint;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicLong;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 10.09.20 16:08
 */
public class EntryPoint {
	/**
	 * Name of the producer which is also the id of the entry point.
	 */
	private String producerId;
	/**
	 * Total number of requests this entry saw.
	 */
	private AtomicLong totalRequests = new AtomicLong();
	/**
	 * Number of currently active requests.
	 */
	private AtomicLong currentRequests = new AtomicLong();

	private CopyOnWriteArrayList<ActiveMeasurement> currentMeasurements = new CopyOnWriteArrayList<>();

	private PastMeasurementChainNode pastMeasurements;

	public EntryPoint(String aProducerId){
		producerId = aProducerId;
	}

	public void requestStarted(){
		totalRequests.incrementAndGet();
		currentRequests.incrementAndGet();
	}

	public void requestFinished(ActiveMeasurement measurement){
		currentRequests.decrementAndGet();
		currentMeasurements.remove(measurement);
		PastMeasurementChainNode newNode = new PastMeasurementChainNode(measurement);
		pastMeasurements = PastMeasurementChainNode.addToChainIfLongerDuration(pastMeasurements, newNode);
	}

	public String toString(){
		return "Id: "+producerId+", CR: "+currentRequests+", TR: "+totalRequests+", CM: "+currentMeasurements;
	}

	public String getProducerId() {
		return producerId;
	}

	public long getTotalRequestCount() {
		return totalRequests.get();
	}

	public long getCurrentRequestCount() {
		return currentRequests.get();
	}

	public void addCurrentMeasurements(ActiveMeasurement measurement) {
		currentMeasurements.add(measurement);
	}

	public List<ActiveMeasurement> getCurrentMeasurements() {
		return currentMeasurements;
	}

	public List<PastMeasurement> getPastMeasurements(){
		return pastMeasurements == null ?
			Collections.<PastMeasurement>emptyList() :
			pastMeasurements.getMeasurements();
	}
}
