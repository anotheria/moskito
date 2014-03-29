package net.anotheria.moskito.webui.threads.api;

/**
 * Contains information about threads in the system.
 */
public class ThreadsInfoAO {
	/**
	 * Number of threads.
	 */
	private int threadCount;
	/**
	 * Number of daemon threads.
	 */
	private int daemonThreadCount;
	/**
	 * Peak number of threads.
	 */
	private int peakThreadCount;
	/**
	 * Total number of started threads.
	 */
	private long totalStarted;

	private boolean currentThreadCpuTimeSupported;
	/**
	 * Is measuring of the thread contention monitoring enabled?
	 */
	private boolean threadContentionMonitoringEnabled;
	/**
	 * Is measuring of the thread contention monitoring supported?
	 */
	private boolean	threadContentionMonitoringSupported;
	/**
	 * Is measuring of the cpu time spent in thread enabled?
	 */
	private boolean threadCpuTimeEnabled;
	/**
	 * Is measuring of the cpu time spent in thread supported?
	 */
	private boolean	threadCpuTimeSupported;
	public int getThreadCount() {
		return threadCount;
	}
	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount;
	}
	public int getDaemonThreadCount() {
		return daemonThreadCount;
	}
	public void setDaemonThreadCount(int daemonThreadCount) {
		this.daemonThreadCount = daemonThreadCount;
	}
	public int getPeakThreadCount() {
		return peakThreadCount;
	}
	public void setPeakThreadCount(int peakThreadCount) {
		this.peakThreadCount = peakThreadCount;
	}
	public long getTotalStarted() {
		return totalStarted;
	}
	public void setTotalStarted(long totalStarted) {
		this.totalStarted = totalStarted;
	}
	public boolean isCurrentThreadCpuTimeSupported() {
		return currentThreadCpuTimeSupported;
	}
	public void setCurrentThreadCpuTimeSupported(
			boolean currentThreadCpuTimeSupported) {
		this.currentThreadCpuTimeSupported = currentThreadCpuTimeSupported;
	}
	public boolean isThreadContentionMonitoringEnabled() {
		return threadContentionMonitoringEnabled;
	}
	public void setThreadContentionMonitoringEnabled(
			boolean threadContentionMonitoringEnabled) {
		this.threadContentionMonitoringEnabled = threadContentionMonitoringEnabled;
	}
	public boolean isThreadContentionMonitoringSupported() {
		return threadContentionMonitoringSupported;
	}
	public void setThreadContentionMonitoringSupported(
			boolean threadContentionMonitoringSupported) {
		this.threadContentionMonitoringSupported = threadContentionMonitoringSupported;
	}
	public boolean isThreadCpuTimeEnabled() {
		return threadCpuTimeEnabled;
	}
	public void setThreadCpuTimeEnabled(boolean threadCpuTimeEnabled) {
		this.threadCpuTimeEnabled = threadCpuTimeEnabled;
	}
	public boolean isThreadCpuTimeSupported() {
		return threadCpuTimeSupported;
	}
	public void setThreadCpuTimeSupported(boolean threadCpuTimeSupported) {
		this.threadCpuTimeSupported = threadCpuTimeSupported;
	} 
}
