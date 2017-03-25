package net.anotheria.moskito.core.stats;

/**
 * Extends the {@link StatValue} interface with a {@link StatValueTypes} value.
 * 
 * @author Michael König
 */
public interface DetailedStatValue extends TypeAwareStatValue {

	public int getMinValueAsInt();

	public long getMinValueAsLong();

	public double getMinValueAsDouble();

	public int getMinValueAsInt(String aIntervalName);

	public long getMinValueAsLong(String aIntervalName);

	public double getMinValueAsDouble(String aIntervalName);

	public String getMinValueAsString(String intervalName);

	public String getMinValueAsString();

	public int getMaxValueAsInt();

	public long getMaxValueAsLong();

	public double getMaxValueAsDouble();

	public int getMaxValueAsInt(String aIntervalName);

	public long getMaxValueAsLong(String aIntervalName);

	public double getMaxValueAsDouble(String aIntervalName);

	public String getMaxValueAsString(String intervalName);

	public String getMaxValueAsString();

	public int getSumValueAsInt();

	public long getSumValueAsLong();

	public double getSumValueAsDouble();

	public int getSumValueAsInt(String aIntervalName);

	public long getSumValueAsLong(String aIntervalName);

	public double getSumValueAsDouble(String aIntervalName);

	public String getSumValueAsString(String intervalName);

	public String getSumValueAsString();

	public int getCountValueAsInt();

	public long getCountValueAsLong();

	public double getCountValueAsDouble();

	public int getCountValueAsInt(String aIntervalName);

	public long getCountValueAsLong(String aIntervalName);

	public double getCountValueAsDouble(String aIntervalName);

	public String getCountValueAsString(String intervalName);

	public String getCountValueAsString();

	public int getAvgValueAsInt();

	public long getAvgValueAsLong();

	public double getAvgValueAsDouble();

	public int getAvgValueAsInt(String aIntervalName);

	public long getAvgValueAsLong(String aIntervalName);

	public double getAvgValueAsDouble(String aIntervalName);

	public String getAvgValueAsString(String intervalName);

	public String getAvgValueAsString();
}
