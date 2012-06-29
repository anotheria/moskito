package net.java.dev.moskito.core.stats;

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
	 * Counter is similar to long, except that they do not get resetted on interval update.
	 */
	COUNTER
}