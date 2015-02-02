package net.anotheria.moskito.core.stats;
/**
 * TimeUnit is used to transform internal stored time into nanos and forth.
 * @author lrosenberg
 */
public enum TimeUnit {
	/**
	 * Nanoseconds, native measurement unit.
	 */
	NANOSECONDS(1),
	/**
	 * Microseconds.
	 */
	MICROSECONDS(1000),
	/**
	 * Milliseconds, 1 ms = 1000 micros.
	 */
	MILLISECONDS(1000L*MICROSECONDS.factor),
	/**
	 * Seconds.
	 */
	SECONDS(1000L*MILLISECONDS.factor);
	/**
	 * Transformation factor.
	 */
	private long factor;
	/**
	 * Creates a new TimeUnit with given transformation factor.
	 * @param aFactor
	 */
	private TimeUnit(long aFactor){
		factor = aFactor;
	}
	/**
	 * Transforms nanos to internal format (/factor).
	 * @param nanos
	 * @return
	 */
	public long transformNanos(long nanos){
		return nanos / factor;
	}

    /**
     * Transforms (double) milliseconds to internal format.
     * @param millis
     * @return
     */
    public double transformMillis(double millis){
        switch (this) {
            case NANOSECONDS:
                return millis * MILLISECONDS.factor;
            case MICROSECONDS:
                return millis * MICROSECONDS.factor;
            case SECONDS:
                return millis / 1000;
            default:
                return millis;

        }
    }

    /**
     * Transforms (long) milliseconds to internal format.
     * @param millis
     * @return
     */
    public long transformMillis(long millis){
        switch (this) {
            case NANOSECONDS:
                return millis * MILLISECONDS.factor;
            case MICROSECONDS:
                return millis * MICROSECONDS.factor;
            case SECONDS:
                return millis / 1000;
            default:
                return millis;

        }
    }
	
	/**
	 * Helper method to create a TimeUnit from potentially incorrect textual representation (nanos, nanoseconds, seconds, millis, etc work).
	 * @param str
	 * @return
	 */
	public static final TimeUnit fromString(String str){
		if (str==null)
			throw new IllegalArgumentException("TimeUnit name can't be null");
		str = str.toUpperCase();
		String orig = str;
		try{
			TimeUnit ret = TimeUnit.valueOf(str);
			return ret;
		}catch(IllegalArgumentException e){
			if (!str.endsWith("S"))
				str+="S";
			str += "ECONDS";
			try{
				TimeUnit ret = TimeUnit.valueOf(str);
				return ret;
			}catch(IllegalArgumentException e2){
				throw new IllegalArgumentException("No enum const class net.anotheria.moskito.core.stats.TimeUnit, tried "+orig+" and "+str);
			}
		}
	}
}
