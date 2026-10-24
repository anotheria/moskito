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
	 * A long-valued count that is not reset on interval updates.
	 */
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
