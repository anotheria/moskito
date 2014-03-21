package net.anotheria.moskito.webui.threads.api;

import java.io.Serializable;
import java.lang.management.ThreadInfo;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 21.03.14 23:32
 */
public class ThreadInfoAO implements Serializable{
	private long threadId;
	private String threadName;
	private Thread.State threadState;
	private boolean inNative;
	private boolean suspended;

	private String lockName;
	private long lockOwnerId;
	private String lockOwnerName;

	private long blockedCount;
	private long blockedTime;

	private long waitedCount;
	private long waitedTime;

	public ThreadInfoAO(ThreadInfo jmxInfo){
		threadId = jmxInfo.getThreadId();
		threadName = jmxInfo.getThreadName();
		threadState = jmxInfo.getThreadState();

		inNative = jmxInfo.isInNative();
		suspended = jmxInfo.isSuspended();

		lockName = jmxInfo.getLockName();
		lockOwnerName = jmxInfo.getLockOwnerName();
		lockOwnerId = jmxInfo.getLockOwnerId();

		blockedCount = jmxInfo.getBlockedCount();
		blockedTime = jmxInfo.getBlockedTime();

		waitedCount = jmxInfo.getWaitedCount();
		waitedTime = jmxInfo.getWaitedTime();
	}

	public long getThreadId() {
		return threadId;
	}

	public void setThreadId(long threadId) {
		this.threadId = threadId;
	}

	public String getThreadName() {
		return threadName;
	}

	public void setThreadName(String threadName) {
		this.threadName = threadName;
	}

	public Thread.State getThreadState() {
		return threadState;
	}

	public void setThreadState(Thread.State threadState) {
		this.threadState = threadState;
	}

	public boolean isInNative() {
		return inNative;
	}

	public void setInNative(boolean inNative) {
		this.inNative = inNative;
	}

	public boolean isSuspended() {
		return suspended;
	}

	public void setSuspended(boolean suspended) {
		this.suspended = suspended;
	}

	public String getLockName() {
		return lockName;
	}

	public void setLockName(String lockName) {
		this.lockName = lockName;
	}

	public long getLockOwnerId() {
		return lockOwnerId;
	}

	public void setLockOwnerId(long lockOwnerId) {
		this.lockOwnerId = lockOwnerId;
	}

	public String getLockOwnerName() {
		return lockOwnerName;
	}

	public void setLockOwnerName(String lockOwnerName) {
		this.lockOwnerName = lockOwnerName;
	}

	public long getBlockedCount() {
		return blockedCount;
	}

	public void setBlockedCount(long blockedCount) {
		this.blockedCount = blockedCount;
	}

	public long getBlockedTime() {
		return blockedTime;
	}

	public void setBlockedTime(long blockedTime) {
		this.blockedTime = blockedTime;
	}

	public long getWaitedCount() {
		return waitedCount;
	}

	public void setWaitedCount(long waitedCount) {
		this.waitedCount = waitedCount;
	}

	public long getWaitedTime() {
		return waitedTime;
	}

	public void setWaitedTime(long waitedTime) {
		this.waitedTime = waitedTime;
	}
}
