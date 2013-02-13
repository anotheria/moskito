package net.anotheria.moskito.core.producers;

import java.util.List;

public interface StatsMXBean {
	
	/**
	 * Returns the names of all contained stats.
	 * @return
	 */
	List<String> getAvailableValueNames();

}
