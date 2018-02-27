package net.anotheria.moskito.moskitosaas.errorhandling;

import java.util.Map;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 25.02.18 23:38
 */
public class ErrorWrapper {
	private String exceptionClassName;
	private String exceptionMessage;
	private Map<String,String> tags;
	private long timestamp;
	private long runningNumber;


	public long getRunningNumber() {
		return runningNumber;
	}

	public void setRunningNumber(long runningNumber) {
		this.runningNumber = runningNumber;
	}

	public String getExceptionClassName() {
		return exceptionClassName;
	}

	public void setExceptionClassName(String exceptionClassName) {
		this.exceptionClassName = exceptionClassName;
	}

	public String getExceptionMessage() {
		return exceptionMessage;
	}

	public void setExceptionMessage(String exceptionMessage) {
		this.exceptionMessage = exceptionMessage;
	}

	public Map<String, String> getTags() {
		return tags;
	}

	public void setTags(Map<String, String> tags) {
		this.tags = tags;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
}
