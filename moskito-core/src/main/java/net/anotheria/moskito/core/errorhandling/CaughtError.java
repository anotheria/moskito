package net.anotheria.moskito.core.errorhandling;

import net.anotheria.moskito.core.context.MoSKitoContext;

import java.util.Map;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 11.06.17 20:21
 */
public class CaughtError {
	/**
	 * Error timestamp
	 */
	private long timestamp;
	/**
	 * Error object.
	 */
	private Throwable throwable;
	/**
	 * Tags associated with this request (if any).
	 */
	private Map<String, String> tags;
	
	public CaughtError(Throwable aThrowable){
		throwable = aThrowable;
		timestamp = System.currentTimeMillis();
		tags = MoSKitoContext.getTags();
	}

	public long getTimestamp() {
		return timestamp;
	}

	public Throwable getThrowable() {
		return throwable;
	}

	public Map<String, String> getTags() {
		return tags;
	}

	@Override
	public String toString() {
		return "CaughtError{" +
				"timestamp=" + timestamp +
				", throwable=" + throwable +
				", tags=" + tags +
				'}';
	}
}
