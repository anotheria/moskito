package net.java.dev.moskito.webui.bean.threads;

public class ThreadsInfoBean {
	private int threadCount;
	private int daemonThreadCount;
	private int peakThreadCount;
	private long totalStarted;
	
	private boolean currentThreadCpuTimeSupported;
	private boolean threadContentionMonitoringEnabled;
	private boolean	threadContentionMonitoringSupported; 
	private boolean threadCpuTimeEnabled; 
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
