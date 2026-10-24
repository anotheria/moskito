package net.anotheria.moskito.core.stats;

/**
 * The internal type representation for StatValues.
 *
 * @author miros,lrosenberg.
 */
public enum StatValueTypes {
	/**
	 * A long value is required
	 */
	LONG, 
	/**
	 * An int value is required
	 */
	INT,
	/**
	 * A double value is required
	 */
	DOUBLE,
	/**
	 * String value. String values are only used to held some widely unrelevant information like process name.
	 */
	STRING,
	/**
	 * A long value that is not reset on interval updates. Behaves exactly like {@link #LEVEL}; despite its name it is
	 * not a per-interval count (that is {@link #LONG}).
	 * @deprecated use {@link #LEVEL} instead.
	 */
	@Deprecated
	COUNTER,
	/**
	 * This is a type used in CPU Counter. It doesn't keep an absolute long value, but rather the diff to previous value.
	 */
	DIFFLONG,
	/**
	 * A long-valued gauge whose current level is retained across interval updates.
	 */
	LEVEL;
}
