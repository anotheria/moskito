package net.anotheria.moskito.core.stats.impl;

import net.anotheria.moskito.core.stats.Interval;

import java.util.concurrent.atomic.AtomicLong;
import java.util.function.LongBinaryOperator;
import java.util.function.LongSupplier;

/**
 * Min or max of a sampled level (e.g. memory). Holds the extreme of the values set within the current interval.
 * An interval without any value falls back to the level itself, i.e. the last sample, so it neither drops to a constant
 * default nor carries the previous interval's extreme over.
 * <p>
 * Values have to be fed with {@link #setValueAsLong(long)}, which keeps the extreme itself.
 * The conditional setters of the stat value (setValueIfGreater/LesserThanCurrent) must not be used, because
 * they compare against the level fallback of an empty interval. Increase/decrease are not meaningful here.
 */
class SampledExtremeValueHolder extends LongValueHolder {
	/**
	 * Math::max or Math::min.
	 */
	private final LongBinaryOperator extreme;
	/**
	 * Identity of the operator, marks an interval in which no value has been set yet.
	 */
	private final long none;
	/**
	 * The sampled level, used for an interval without values.
	 */
	private final LongSupplier level;

	private final AtomicLong value;
	private volatile long lastValue;

	SampledExtremeValueHolder(Interval interval, boolean max, LongSupplier level) {
		super(interval);
		this.extreme = max ? Math::max : Math::min;
		this.none = max ? Long.MIN_VALUE : Long.MAX_VALUE;
		this.level = level;
		this.value = new AtomicLong(none);
		this.lastValue = none;
	}

	private long orLevel(long aValue) {
		return aValue == none ? level.getAsLong() : aValue;
	}

	@Override public void setValueAsLong(long aValue) {
		value.accumulateAndGet(aValue, extreme);
	}

	@Override public void setValueAsString(String aValue) {
		setValueAsLong(Long.parseLong(aValue));
	}

	@Override public long getCurrentValueAsLong() {
		return orLevel(value.get());
	}

	@Override public long getValueAsLong() {
		return orLevel(lastValue);
	}

	@Override public long getLastValue() {
		return getValueAsLong();
	}

	@Override public String getValueAsString() {
		return String.valueOf(getValueAsLong());
	}

	@Override public String getCurrentValueAsString() {
		return String.valueOf(getCurrentValueAsLong());
	}

	@Override public void intervalUpdated(Interval caller) {
		lastValue = orLevel(value.getAndSet(none));
	}

	@Override public void reset() {
		value.set(none);
		lastValue = none;
	}

	@Override public String toString() {
		return getClass().getSimpleName() + " L " + getValueAsLong() + " / " + getCurrentValueAsLong();
	}
}
