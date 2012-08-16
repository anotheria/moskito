package net.java.dev.moskito.util.threadhistory;

import net.anotheria.util.NumberUtils;

public class ThreadHistoryEvent {
	
	public enum OPERATION{
		CREATED, DELETED;
	};
	/**
	 * Id of the thread.
	 */
	private long threadId;
	/**
	 * Name of the thread.	
	 */
	private String threadName;
	/**
	 * Operation.
	 */
	private OPERATION operation;
	/**
	 * Timestamp of the history.
	 */
	private long timestamp;
	
	public ThreadHistoryEvent(long aThreadId, String aThreadName, OPERATION anOperation){
		threadId = aThreadId;
		threadName = aThreadName;
		operation = anOperation;
		timestamp = System.currentTimeMillis();
	}
	
	@Override public String toString(){
		return getNiceTimestamp()+" "+getThreadId()+" "+getThreadName()+" "+getOperation();
	}

	public long getThreadId() {
		return threadId;
	}

	public String getThreadName() {
		return threadName;
	}

	public OPERATION getOperation() {
		return operation;
	}

	public static final ThreadHistoryEvent created(long threadId, String threadName){
		return new ThreadHistoryEvent(threadId, threadName, OPERATION.CREATED);
	}
	public static final ThreadHistoryEvent deleted(long threadId, String threadName){
		return new ThreadHistoryEvent(threadId, threadName, OPERATION.DELETED);
	}

	public long getTimestamp() {
		return timestamp;
	}
	
	public String getNiceTimestamp(){
		return NumberUtils.makeISO8601TimestampString(getTimestamp());
	}
}
