package net.anotheria.moskito.core.stats;



/**
 * This interface declares a generic statistic value that may record data series for certain 
 * Intervals synchronously. That means, that if there is more than one Interval registered, 
 * the same value will be collected in all of these intervals.<br>    
 * Furthermore this class provides some convenience methods to manipulate the value, 
 * possibly depending on comparison operations.  
 * 
 * @see Interval
 * @author miros
 */
public interface StatValue {

	/**
	 * This method adds the given Interval. After every value manipulation 
	 * will be delegated to this Interval too.  
	 * 
	 * @param aInterval the Interval to add
	 */
	void addInterval(Interval aInterval);

	/**
	 * This method returns the name of this statistic value.
	 * @return the name
	 */
	String getName();

	/**
	 * This method increases the stored values for all Intervals by one.
	 * Particularly, this includes the absolute value.
	 */
	void increase();

	/**
	 * This method decreases the stored values for all Intervals by one.
	 * Particularly, this includes the absolute value.
	 */
	void decrease();

	/**
	 * This method returns the absolute value as int.
	 * 
	 * @return the absolute value
	 */
	int getValueAsInt();

	/**
	 * This method returns the absolute value as long.
	 * 
	 * @return the absolute value
	 */
	long getValueAsLong();

	/**
	 * This method returns the absolute value as double.
	 * 
	 * @return the absolute value
	 */
	double getValueAsDouble();

	/**
	 * This method returns the current value of a specific Interval as int.
	 * 
	 * @param aIntervalName the name of the Interval or <code>null</code> to get the absolute value
	 * @return the current value
	 */
	int getValueAsInt(String aIntervalName);

	/**
	 * This method returns the current value of a specific Interval as long.
	 * 
	 * @param aIntervalName the name of the Interval or <code>null</code> to get the absolute value
	 * @return the current value
	 */
	long getValueAsLong(String aIntervalName);

	/**
	 * This method returns the current value of a specific Interval as double.
	 * 
	 * @param aIntervalName the name of the Interval or <code>null</code> to get the absolute value
	 * @return the current value
	 */
	double getValueAsDouble(String aIntervalName);

	/**
	 * This method sets the given int value to be the current value of all registered Intervals.
	 * Particularly, this includes the absolute value.<br> 
	 * ATTENTION: The Intervals will not be resetted. So the measured values of the first next Interval 
	 * cycles are invalid.  
	 *  
	 * @param aValue the new value
	 */
	void setValueAsInt(int aValue);

	/**
	 * This method sets the given long value to be the current value of all registered Intervals.
	 * Particularly, this includes the absolute value.<br> 
	 * ATTENTION: The Intervals will not be resetted. So the measured values of the first next Interval 
	 * cycles are invalid.  
	 *  
	 * @param aValue the new value
	 */
	void setValueAsLong(long aValue);

	/**
	 * This method sets the given double value to be the current value of all registered Intervals.
	 * Particularly, this includes the absolute value.<br> 
	 * ATTENTION: The Intervals will not be resetted. So the measured values of the first next Interval 
	 * cycles are invalid.  
	 *  
	 * @param aValue the new value
	 */
	void setValueAsDouble(double aValue);

	/**
	 * This method increases the current values of all registered Intervals by the given int value.
	 * Particularly, this includes the absolute value.
	 * 
	 * @param aValue the value to increment by
	 */
	void increaseByInt(int aValue);

	/**
	 * This method increases the current values of all registered Intervals by the given long value.
	 * Particularly, this includes the absolute value.
	 * 
	 * @param aValue the value to increment by
	 */
	void increaseByLong(long aValue);

	/**
	 * This method increases the current values of all registered Intervals by the given double value.
	 * Particularly, this includes the absolute value.
	 * 
	 * @param aValue the value to increment by
	 */
	void increaseByDouble(double aValue);

	/**
	 * This method decreases the current values of all registered Intervals by the given int value.
	 * Particularly, this includes the absolute value.
	 * 
	 * @param aValue the value to decrement by
	 */
	void decreaseByInt(int aValue);

	/**
	 * This method decreases the current values of all registered Intervals by the given long value.
	 * Particularly, this includes the absolute value.
	 * 
	 * @param aValue the value to decrement by
	 */
	void decreaseByLong(long aValue);

	/**
	 * This method decreases the current values of all registered Intervals by the given double value.
	 * Particularly, this includes the absolute value.
	 * 
	 * @param aValue the value to decrement by
	 */
	void decreaseByDouble(double aValue);

	/**
	 * This method sets the default value that will be the initial value after an Interval was elapsed.
	 * Typically, an Interval implementation will execute "currentValue = defaultValue" after 
	 * it Interval period was over. 
	 * 
	 * @param aValue the new default value
	 */
	void setDefaultValueAsLong(long aValue);

	/**
	 * This method sets the default value that will be the initial value after an Interval was elapsed.
	 * Typically, an Interval implementation will execute "currentValue = defaultValue" after 
	 * it Interval period was over. 
	 * 
	 * @param aValue the new default value
	 */
	void setDefaultValueAsInt(int aValue);

	/**
	 * This method sets the default value that will be the initial value after an Interval was elapsed.
	 * Typically, an Interval implementation will execute "currentValue = defaultValue" after 
	 * it Interval period was over. 
	 * 
	 * @param aValue the new default value
	 */
	void setDefaultValueAsDouble(double aValue);

	/**
	 * This method resets the ValueHolders of all registered Intervals.
	 * Typically, an Interval implementation will execute "currentValue = defaultValue" on reset. 
	 */
	void reset();

	/**
	 * This method sets the given long value to be the current value of all registered 
	 * Intervals depending on the condition "current value < given value".
	 * Particularly, this includes the absolute value.<br> 
	 * ATTENTION: The Intervals will not be resetted. So the measured values of the first next Interval 
	 * cycles are invalid.  
	 *  
	 * @param aValue the new value
	 */
	void setValueIfGreaterThanCurrentAsLong(long aValue);

	/**
	 * This method sets the given int value to be the current value of all registered 
	 * Intervals depending on the condition "current value < given value".
	 * Particularly, this includes the absolute value.<br> 
	 * ATTENTION: The Intervals will not be resetted. So the measured values of the first next Interval 
	 * cycles are invalid.  
	 *  
	 * @param aValue the new value
	 */
	void setValueIfGreaterThanCurrentAsInt(int aValue);

	/**
	 * This method sets the given double value to be the current value of all registered 
	 * Intervals depending on the condition "current value < given value".
	 * Particularly, this includes the absolute value.<br> 
	 * ATTENTION: The Intervals will not be resetted. So the measured values of the first next Interval 
	 * cycles are invalid.  
	 *  
	 * @param aValue the new value
	 */
	void setValueIfGreaterThanCurrentAsDouble(double aValue);

	/**
	 * This method sets the given long value to be the current value of all registered 
	 * Intervals depending on the condition "current value > given value".
	 * Particularly, this includes the absolute value.<br> 
	 * ATTENTION: The Intervals will not be resetted. So the measured values of the first next Interval 
	 * cycles are invalid.  
	 *  
	 * @param aValue the new value
	 */
	void setValueIfLesserThanCurrentAsLong(long aValue);

	/**
	 * This method sets the given int value to be the current value of all registered 
	 * Intervals depending on the condition "current value > given value".
	 * Particularly, this includes the absolute value.<br> 
	 * ATTENTION: The Intervals will not be resetted. So the measured values of the first next Interval 
	 * cycles are invalid.  
	 *  
	 * @param aValue the new value
	 */
	void setValueIfLesserThanCurrentAsInt(int aValue);

	/**
	 * This method sets the given double value to be the current value of all registered 
	 * Intervals depending on the condition "current value > given value".
	 * Particularly, this includes the absolute value.<br> 
	 * ATTENTION: The Intervals will not be resetted. So the measured values of the first next Interval 
	 * cycles are invalid.  
	 *  
	 * @param aValue the new value
	 */
	void setValueIfLesserThanCurrentAsDouble(double aValue);
	
	/**
	 * Sets the value as String. This method is mainly needed for pure informative values like system name.
	 * @param aValue
	 */
	void setValueAsString(String aValue);
	
	String getValueAsString();
	
	String getValueAsString(String anIntervalName);

	/**
	 * Called when the owner stats object is removed from the system.
	 * Upon this call the StatValue should clean up any references it still holds and unregister itself from events.
	 */
	void destroy();

}