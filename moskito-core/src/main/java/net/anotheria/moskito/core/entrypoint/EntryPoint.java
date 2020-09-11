package net.anotheria.moskito.core.entrypoint;

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

	public EntryPoint(String aProducerId){
		producerId = aProducerId;
	}

	public void requestStarted(){
		totalRequests.incrementAndGet();
		currentRequests.incrementAndGet();
	}

	public void requestFinished(){
		currentRequests.decrementAndGet();
	}

	public String toString(){
		return "Id: "+producerId+", CR: "+currentRequests+", TR: "+totalRequests;
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
}
