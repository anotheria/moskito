package net.java.dev.moskito.core.predefined;

/**
 * Describes stats for a memory pool as a part of the garbage collector internal pools (old, new, eden, tenured, perm, etc).
 * @author lrosenberg
 *
 */
public interface IMemoryPoolStats {
	/**
	 * Returns the init size of the pool. Shouldn't change itself over the interval limits.
	 * @param intervalName
	 * @return
	 */
	long getInit(String intervalName);
	
	/**
	 * Returns the used memory for the given interval.
	 * @param intervalName
	 * @return
	 */
	long getUsed(String intervalName);
	/**
	 * Returns the min used memory in the given interval.
	 * @param intervalName
	 * @return
	 */
	long getMinUsed(String intervalName);
	/**
	 * Returns the max used memory in the given interval.
	 * @param intervalName
	 * @return
	 */
	long getMaxUsed(String intervalName);

	
	/**
	 * Returns the measured commited memory in the given interval.
	 * @param intervalName
	 * @return
	 */
	long getCommited(String intervalName);
	/**
	 * Returns the min measured commited memory in the given interval.
	 * @param intervalName
	 * @return
	 */
	long getMinCommited(String intervalName);
	/**
	 * Returns the max measured commited memory in the given interval.
	 * @param intervalName
	 * @return
	 */
	long getMaxCommited(String intervalName);

	/**
	 * Returns the max available memory in the given interval.
	 * @param intervalName
	 * @return
	 */
	long getMax(String intervalName);
	
	/**
	 * Returns the amount of free memory for given interval.
	 * @param intervalName
	 * @return
	 */
	long getFree(String intervalName);
}
