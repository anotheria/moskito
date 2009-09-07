package net.java.dev.moskito.core.predefined;

public interface IMemoryPoolStats {
	public long getInit(String intervalName);
	
	public long getUsed(String intervalName);
	public long getMinUsed(String intervalName);
	public long getMaxUsed(String intervalName);

	
	public long getCommited(String intervalName);
	public long getMinCommited(String intervalName);
	public long getMaxCommited(String intervalName);

	public long getMax(String intervalName);
	
	public long getFree(String intervalName);
}
