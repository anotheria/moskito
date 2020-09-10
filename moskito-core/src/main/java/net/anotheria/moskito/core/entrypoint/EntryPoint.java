package net.anotheria.moskito.core.entrypoint;

import java.util.concurrent.atomic.AtomicLong;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 10.09.20 16:08
 */
public class EntryPoint {
	private String producerId;
	private AtomicLong totalRequests = new AtomicLong();
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

	
}
