package net.anotheria.moskito.core.threshold.guard;

/**
 * Describes possible direction which can be bypassed by the threshold.
 * @author lrosenberg
 *
 */
public enum GuardedDirection {
	/**
	 * The new value is lower than the guarded value.
	 */
	DOWN{
		protected int getExpectedCompareResult(){
			return -1;
		}
	},
	/**
	 * The new value is higher than the guarded value.
	 */
	UP{
		protected int getExpectedCompareResult(){
			return 1;
		}
	};
	
	abstract int getExpectedCompareResult();
	
	/**
	 * Returns true if the threshold guard is passed (and the threshold guard should fire).
	 * @param value
	 * @param limit
	 * @return
	 */
	public boolean brokeThrough(Number value, Number limit){
		int compareResult = 0;
		if (value instanceof Double){
			compareResult = ((Double)value).compareTo((Double)limit);
		}
		if (value instanceof Long){
			compareResult = ((Long)value).compareTo((Long)limit);
		}
		
		if (compareResult>1)
			compareResult = 1;
		if (compareResult<-1)
			compareResult = -1;
		
		//the second part of the return statement ensures that if we are at the edge, we are still get a hit. 
		return getExpectedCompareResult() == compareResult || 
			(compareResult == 0 && getExpectedCompareResult() == 1);
	}
}
