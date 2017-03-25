package net.anotheria.moskito.core.stats.impl;

import net.anotheria.moskito.core.stats.DetailedStatValue;
import net.anotheria.moskito.core.stats.IValueHolderFactory;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.StatValueTypes;

/**
 * Extends the {@link TypeAwareStatValueImpl} type with some predefined values:
 * min, max, avg.
 *
 * @author Marlon Patrick
 */
public class DetailedTypeAwareStatValueImpl extends TypeAwareStatValueImpl implements DetailedStatValue {


	private StatValue min;

	private StatValue max;

	private StatValue sum;

	private StatValue count;

	public DetailedTypeAwareStatValueImpl(final String aName, final StatValueTypes svType,
			final IValueHolderFactory aFactory) {

		super(aName, svType, aFactory);

		if (StatValueTypes.STRING.equals(svType)) {
			throw new AssertionError("Unsupported type: " + svType);
		}

		this.sum = StatValueFactory.createStatValue(svType, "sum" + aName, this.values.keySet());
		this.count = StatValueFactory.createStatValue(StatValueTypes.LONG, "count" + aName, this.values.keySet());
		this.min = StatValueFactory.createStatValue(svType, "min" + aName, this.values.keySet());
		this.max = StatValueFactory.createStatValue(svType, "max" + aName, this.values.keySet());

		this.setMinMaxDefaultValues();

	}

	private void setMinMaxDefaultValues() {
		StatValueTypes svType = this.getType();

		if (StatValueTypes.LONG.equals(svType) || StatValueTypes.COUNTER.equals(svType)
				|| StatValueTypes.DIFFLONG.equals(svType)) {

			this.max.setDefaultValueAsLong(Long.MIN_VALUE);
			this.min.setDefaultValueAsLong(Long.MAX_VALUE);
		}

		if (StatValueTypes.INT.equals(svType)) {
			this.max.setDefaultValueAsInt(Integer.MIN_VALUE);
			this.min.setDefaultValueAsInt(Integer.MAX_VALUE);
		}

		if (StatValueTypes.DOUBLE.equals(svType)) {
			this.max.setDefaultValueAsDouble(Double.MIN_VALUE);
			this.min.setDefaultValueAsDouble(Double.MAX_VALUE);
		}
	}

	@Override
	public void addInterval(Interval aInterval) {
		super.addInterval(aInterval);
		this.sum.addInterval(aInterval);
		this.count.addInterval(aInterval);
		this.min.addInterval(aInterval);
		this.max.addInterval(aInterval);
		this.setMinMaxDefaultValues();// should invoke a reset after add all
										// intervals
	}

	private void setMaxValue() {

		StatValueTypes svType = this.getType();

		if (StatValueTypes.LONG.equals(svType) || StatValueTypes.COUNTER.equals(svType)
				|| StatValueTypes.DIFFLONG.equals(svType)) {

			long longVal = this.absoluteValue.getCurrentValueAsLong();
			this.max.setValueIfGreaterThanCurrentAsLong(longVal);
		}

		if (StatValueTypes.INT.equals(svType)) {
			int intVal = this.absoluteValue.getCurrentValueAsInt();
			this.max.setValueIfGreaterThanCurrentAsInt(intVal);
		}

		if (StatValueTypes.DOUBLE.equals(svType)) {
			double doubleVal = this.absoluteValue.getCurrentValueAsDouble();
			this.max.setValueIfGreaterThanCurrentAsDouble(doubleVal);
		}
	}

	private void setMinValue() {
		StatValueTypes svType = this.getType();

		if (StatValueTypes.LONG.equals(svType) || StatValueTypes.COUNTER.equals(svType)
				|| StatValueTypes.DIFFLONG.equals(svType)) {

			long longVal = this.absoluteValue.getCurrentValueAsLong();
			this.min.setValueIfLesserThanCurrentAsLong(longVal);
		}

		if (StatValueTypes.INT.equals(svType)) {
			int intVal = this.absoluteValue.getCurrentValueAsInt();
			this.min.setValueIfLesserThanCurrentAsInt(intVal);
		}

		if (StatValueTypes.DOUBLE.equals(svType)) {
			double doubleVal = this.absoluteValue.getCurrentValueAsDouble();
			this.min.setValueIfLesserThanCurrentAsDouble(doubleVal);
		}
	}

	private void sumCurrentValue() throws AssertionError {

		StatValueTypes svType = this.getType();

		if (StatValueTypes.LONG.equals(svType) || StatValueTypes.COUNTER.equals(svType)
				|| StatValueTypes.DIFFLONG.equals(svType)) {

			long longVal = this.absoluteValue.getCurrentValueAsLong();
			this.sum.increaseByLong(longVal);
		}

		if (StatValueTypes.INT.equals(svType)) {
			int intVal = this.absoluteValue.getCurrentValueAsInt();
			this.sum.increaseByInt(intVal);
		}

		if (StatValueTypes.DOUBLE.equals(svType)) {
			double doubleVal = this.absoluteValue.getCurrentValueAsDouble();
			this.sum.increaseByDouble(doubleVal);
		}
	}

	@Override
	public void setValueAsInt(int aValue) {
		super.setValueAsInt(aValue);
		this.max.setValueIfGreaterThanCurrentAsInt(aValue);
		this.min.setValueIfLesserThanCurrentAsInt(aValue);
		this.sum.increaseByInt(aValue);
		this.count.increase();
	}

	@Override
	public void setValueAsLong(long aValue) {
		super.setValueAsLong(aValue);
		this.max.setValueIfGreaterThanCurrentAsLong(aValue);
		this.min.setValueIfLesserThanCurrentAsLong(aValue);
		this.sum.increaseByLong(aValue);
		this.count.increase();
	}

	@Override
	public void setValueAsDouble(double aValue) {
		super.setValueAsDouble(aValue);
		this.max.setValueIfGreaterThanCurrentAsDouble(aValue);
		this.min.setValueIfLesserThanCurrentAsDouble(aValue);
		this.sum.increaseByDouble(aValue);
		this.count.increase();
	}

	@Override
	public void setValueAsString(String aValue) {
		super.setValueAsString(aValue);

		this.count.increase();
		this.setMaxValue();
		this.setMinValue();
		this.sumCurrentValue();
	}

	@Override
	public void increase() {
		super.increase();
		this.count.increase();
		this.sum.increase();
		this.setMaxValue();
	}

	@Override
	public void increaseByInt(int aValue) {
		super.increaseByInt(aValue);
		this.count.increase();
		this.sum.increaseByInt(aValue);
		this.setMaxValue();
	}

	@Override
	public void increaseByLong(long aValue) {
		super.increaseByLong(aValue);
		this.count.increase();
		this.sum.increaseByLong(aValue);
		this.setMaxValue();

	}

	@Override
	public void increaseByDouble(double aValue) {
		super.increaseByDouble(aValue);
		this.count.increase();
		this.sum.increaseByDouble(aValue);
		this.setMaxValue();

	}

	@Override
	public void decrease() {
		super.decrease();
		this.count.increase();
		this.sum.decrease();
		this.setMinValue();
	}

	@Override
	public void decreaseByInt(int aValue) {
		super.decreaseByInt(aValue);
		this.count.increase();
		this.sum.decreaseByInt(aValue);
		this.setMinValue();
	}

	@Override
	public void decreaseByLong(long aValue) {
		super.decreaseByLong(aValue);
		this.count.increase();
		this.sum.decreaseByLong(aValue);
		this.setMinValue();
	}

	@Override
	public void decreaseByDouble(double aValue) {
		super.decreaseByDouble(aValue);
		this.count.increase();
		this.sum.decreaseByDouble(aValue);
		this.setMinValue();
	}

	@Override
	public void setValueIfGreaterThanCurrentAsLong(long aValue) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setValueIfGreaterThanCurrentAsInt(int aValue) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setValueIfGreaterThanCurrentAsDouble(double aValue) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setValueIfLesserThanCurrentAsLong(long aValue) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setValueIfLesserThanCurrentAsInt(int aValue) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void setValueIfLesserThanCurrentAsDouble(double aValue) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void reset() {
		super.reset();
		this.max.reset();
		this.min.reset();
		this.sum.reset();
		this.count.reset();
	}

	@Override
	public void destroy() {
		super.destroy();
		this.max.destroy();
		this.min.destroy();
		this.sum.destroy();
		this.count.destroy();
	}

	@Override
	public int getMinValueAsInt() {
		return this.min.getValueAsInt();
	}

	@Override
	public long getMinValueAsLong() {
		return this.min.getValueAsLong();
	}

	@Override
	public double getMinValueAsDouble() {
		return this.min.getValueAsDouble();
	}

	@Override
	public int getMinValueAsInt(String aIntervalName) {
		return this.min.getValueAsInt(aIntervalName);
	}

	@Override
	public long getMinValueAsLong(String aIntervalName) {
		return this.min.getValueAsLong(aIntervalName);
	}

	@Override
	public double getMinValueAsDouble(String aIntervalName) {
		return this.min.getValueAsDouble(aIntervalName);
	}

	@Override
	public String getMinValueAsString(String intervalName) {
		return this.min.getValueAsString(intervalName);
	}

	@Override
	public String getMinValueAsString() {
		return this.min.getValueAsString();
	}

	@Override
	public int getMaxValueAsInt() {
		return this.max.getValueAsInt();
	}

	@Override
	public long getMaxValueAsLong() {
		return this.max.getValueAsLong();
	}

	@Override
	public double getMaxValueAsDouble() {
		return this.max.getValueAsDouble();
	}

	@Override
	public int getMaxValueAsInt(String aIntervalName) {
		return this.max.getValueAsInt(aIntervalName);
	}

	@Override
	public long getMaxValueAsLong(String aIntervalName) {
		return this.max.getValueAsLong(aIntervalName);
	}

	@Override
	public double getMaxValueAsDouble(String aIntervalName) {
		return this.max.getValueAsDouble(aIntervalName);
	}

	@Override
	public String getMaxValueAsString(String intervalName) {
		return this.max.getValueAsString(intervalName);
	}

	@Override
	public String getMaxValueAsString() {
		return this.max.getValueAsString();
	}

	@Override
	public int getSumValueAsInt() {
		return this.sum.getValueAsInt();
	}

	@Override
	public long getSumValueAsLong() {
		return this.sum.getValueAsLong();
	}

	@Override
	public double getSumValueAsDouble() {
		return this.sum.getValueAsDouble();
	}

	@Override
	public int getSumValueAsInt(String aIntervalName) {
		return this.sum.getValueAsInt(aIntervalName);
	}

	@Override
	public long getSumValueAsLong(String aIntervalName) {
		return this.sum.getValueAsLong(aIntervalName);
	}

	@Override
	public double getSumValueAsDouble(String aIntervalName) {
		return this.sum.getValueAsDouble(aIntervalName);
	}

	@Override
	public String getSumValueAsString(String intervalName) {
		return this.sum.getValueAsString(intervalName);
	}

	@Override
	public String getSumValueAsString() {
		return this.sum.getValueAsString();
	}

	@Override
	public int getCountValueAsInt() {
		return this.count.getValueAsInt();
	}

	@Override
	public long getCountValueAsLong() {
		return this.count.getValueAsLong();
	}

	@Override
	public double getCountValueAsDouble() {
		return this.count.getValueAsDouble();
	}

	@Override
	public int getCountValueAsInt(String aIntervalName) {
		return this.count.getValueAsInt(aIntervalName);
	}

	@Override
	public long getCountValueAsLong(String aIntervalName) {
		return this.count.getValueAsLong(aIntervalName);
	}

	@Override
	public double getCountValueAsDouble(String aIntervalName) {
		return this.count.getValueAsDouble(aIntervalName);
	}

	@Override
	public String getCountValueAsString(String intervalName) {
		return this.count.getValueAsString(intervalName);
	}

	@Override
	public String getCountValueAsString() {
		return this.count.getValueAsString();
	}

	@Override
	public int getAvgValueAsInt() {
		if (this.count.getValueAsLong() <= 0L) {
			return 0;
		}
		return this.sum.getValueAsInt() / this.count.getValueAsInt();
	}

	@Override
	public long getAvgValueAsLong() {
		if (this.count.getValueAsLong() <= 0L) {
			return 0;
		}
		return this.sum.getValueAsLong() / this.count.getValueAsLong();
	}

	@Override
	public double getAvgValueAsDouble() {
		if (this.count.getValueAsLong() <= 0L) {
			return 0;
		}
		return this.sum.getValueAsDouble() / this.count.getValueAsDouble();
	}

	@Override
	public int getAvgValueAsInt(String aIntervalName) {
		if (this.count.getValueAsLong(aIntervalName) <= 0L) {
			return 0;
		}
		return this.sum.getValueAsInt(aIntervalName) / this.count.getValueAsInt(aIntervalName);
	}

	@Override
	public long getAvgValueAsLong(String aIntervalName) {
		if (this.count.getValueAsLong(aIntervalName) <= 0L) {
			return 0;
		}
		return this.sum.getValueAsLong(aIntervalName) / this.count.getValueAsLong(aIntervalName);
	}

	@Override
	public double getAvgValueAsDouble(String aIntervalName) {
		if (this.count.getValueAsLong(aIntervalName) <= 0L) {
			return 0;
		}
		return this.sum.getValueAsDouble(aIntervalName) / this.count.getValueAsDouble(aIntervalName);
	}

	@Override
	public String getAvgValueAsString(String intervalName) {
		return String.valueOf(this.getAvgValueAsDouble(intervalName));
	}

	@Override
	public String getAvgValueAsString() {
		return String.valueOf(this.getAvgValueAsDouble());
	}
}
