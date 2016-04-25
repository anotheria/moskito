package net.anotheria.moskito.core.config.tracing;

/**
 * This strategy defines how traces are removed, once they reach too many entities.
 *
 * @author lrosenberg
 * @since 23.03.16 18:50
 */
public enum ShrinkingStrategy {
	FIFO,
	KEEPLONGEST;
}
