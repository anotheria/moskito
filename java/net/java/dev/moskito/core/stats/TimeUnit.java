package net.java.dev.moskito.core.stats;

public enum TimeUnit {
	NANOSECONDS(1),
	MICROSECONDS(1000),
	MILLISECONDS(1000L*MICROSECONDS.factor),
	SECONDS(1000L*MILLISECONDS.factor);

	private long factor;
	
	private TimeUnit(long aFactor){
		factor = aFactor;
	}
	
	public long transformNanos(long nanos){
		return nanos / factor;
	}
	
	public static void main(String a[]){
		TimeUnit testUnit = TimeUnit.MILLISECONDS;
		System.out.println(testUnit.transformNanos(2723963744L));
	}
}
