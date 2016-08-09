package net.anotheria.moskito.web.filters;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Helper class that is stored in the http session and contains data about currently running journey.
 */
class JourneyRecord implements Serializable {
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Name of the session.
	 */
	private String name;
	/**
	 * Number of requests so far.
	 */
	private AtomicInteger requestCount;

	/**
	 * Creates a new journey filter.
	 * @param aName
	 */
	JourneyRecord(String aName){
		name = aName;
		requestCount = new AtomicInteger(0);
	}

	public int getRequestCount(){
		return requestCount.incrementAndGet();
	}

	public String getName(){
		return name;
	}

	@Override public String toString(){
        return name +" - "+requestCount.get();
	}

	public String getUseCaseName(){
        return name + '-' +getRequestCount();
	}
}
