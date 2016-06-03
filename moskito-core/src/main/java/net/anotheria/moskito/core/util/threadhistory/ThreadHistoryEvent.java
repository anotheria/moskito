package net.anotheria.moskito.core.util.threadhistory;

import net.anotheria.util.NumberUtils;

import java.io.Serializable;

/**
 * THis event contains the information about a thread creation or deletion.
 * @author lrosenberg
 *
 */
public class ThreadHistoryEvent implements Serializable{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * History operation.
	 * @author lrosenberg
	 *
	 */
	public enum OPERATION{
		/**
		 * A thread has been created.
		 */
		CREATED, 
		/**
		 * A thread has been deleted.
		 */
		DELETED;
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
	
	/**
	 * Creates new event.
	 * @param aThreadId
	 * @param aThreadName
	 * @param anOperation
	 */
	public ThreadHistoryEvent(long aThreadId, String aThreadName, OPERATION anOperation){
		threadId = aThreadId;
		threadName = aThreadName;
		operation = anOperation;
		timestamp = System.currentTimeMillis();
	}
	
	@Override public String toString(){
		return getNiceTimestamp()+ ' ' +getThreadId()+ ' ' +getThreadName()+ ' ' +getOperation();
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

	/**
	 * Factory method to create new 'created' event.
	 * @param threadId
	 * @param threadName
	 * @return
	 */
	public static final ThreadHistoryEvent created(long threadId, String threadName){
		return new ThreadHistoryEvent(threadId, threadName, OPERATION.CREATED);
	}
	/**
	 * Factory method to create new 'deleted' event.
	 * @param threadId
	 * @param threadName
	 * @return
	 */
	public static final ThreadHistoryEvent deleted(long threadId, String threadName){
		return new ThreadHistoryEvent(threadId, threadName, OPERATION.DELETED);
	}

	public long getTimestamp() {
		return timestamp;
	}
	
	/**
	 * Returns timestamp as ISO8601 tiemstamp string.
	 * @return
	 */
	public String getNiceTimestamp(){
		return NumberUtils.makeISO8601TimestampString(getTimestamp());
	}
}
