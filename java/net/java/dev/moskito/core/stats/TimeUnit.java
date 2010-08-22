package net.java.dev.moskito.core.stats;
/**
 * Timeunit is used to transform internal stored time into nanos and forth.
 * @author lrosenberg
 */
public enum TimeUnit {
	/**
	 * Nanosecods, native measurement unit.
	 */
	NANOSECONDS(1),
	/**
	 * Microseconds.
	 */
	MICROSECONDS(1000),
	/**
	 * Milliseconds, 1 ms = 1000 micros.
	 */
	MILLISECONDS(1000L*MICROSECONDS.factor),
	/**
	 * Seconds.
	 */
	SECONDS(1000L*MILLISECONDS.factor);
	/**
	 * Transformation factor.
	 */
	private long factor;
	/**
	 * Creates a new timeunit with given transformation factor.
	 * @param aFactor
	 */
	private TimeUnit(long aFactor){
		factor = aFactor;
	}
	/**
	 * Transforms nanos to internal format (/factor).
	 * @param nanos
	 * @return
	 */
	public long transformNanos(long nanos){
		return nanos / factor;
	}
	
}
