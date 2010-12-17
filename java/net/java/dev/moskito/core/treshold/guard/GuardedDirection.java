package net.java.dev.moskito.core.treshold.guard;

public enum GuardedDirection {
	DOWN{
		public boolean brokeThrough(long value, long limit){
			return value <= limit;
		}
	},
	UP{
		public boolean brokeThrough(long value, long limit){
			return value >= limit;
		}
	};
	
	public abstract boolean brokeThrough(long value, long limit);
}
