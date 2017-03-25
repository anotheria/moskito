package net.anotheria.moskito.core.stats;

/**
 * Extends the {@link StatValue} interface with a {@link StatValueTypes} value.
 * 
 * @author Marlon Patrick
 */
public interface DetailedStatValue extends TypeAwareStatValue {

	int getMinValueAsInt();

	long getMinValueAsLong();

	double getMinValueAsDouble();

	int getMinValueAsInt(String aIntervalName);

	long getMinValueAsLong(String aIntervalName);

	double getMinValueAsDouble(String aIntervalName);

	String getMinValueAsString(String intervalName);

	String getMinValueAsString();

	int getMaxValueAsInt();

	long getMaxValueAsLong();

	double getMaxValueAsDouble();

	int getMaxValueAsInt(String aIntervalName);

	long getMaxValueAsLong(String aIntervalName);

	double getMaxValueAsDouble(String aIntervalName);

	String getMaxValueAsString(String intervalName);

	String getMaxValueAsString();

	int getSumValueAsInt();

	long getSumValueAsLong();

	double getSumValueAsDouble();

	int getSumValueAsInt(String aIntervalName);

	long getSumValueAsLong(String aIntervalName);

	double getSumValueAsDouble(String aIntervalName);

	String getSumValueAsString(String intervalName);

	String getSumValueAsString();

	int getCountValueAsInt();

	long getCountValueAsLong();

	double getCountValueAsDouble();

	int getCountValueAsInt(String aIntervalName);

	long getCountValueAsLong(String aIntervalName);

	double getCountValueAsDouble(String aIntervalName);

	String getCountValueAsString(String intervalName);

	String getCountValueAsString();

	int getAvgValueAsInt();

	long getAvgValueAsLong();

	double getAvgValueAsDouble();

	int getAvgValueAsInt(String aIntervalName);

	long getAvgValueAsLong(String aIntervalName);

	double getAvgValueAsDouble(String aIntervalName);

	String getAvgValueAsString(String intervalName);

	String getAvgValueAsString();
}
