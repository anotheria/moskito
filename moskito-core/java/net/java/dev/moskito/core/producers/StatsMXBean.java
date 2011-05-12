package net.java.dev.moskito.core.producers;

import java.util.List;

public interface StatsMXBean {
	
	String getValueByNameAsString(String valueName, String intervalName, String timeUnit);
	/**
	 * Returns the names of all contained stats.
	 * @return
	 */
	List<String> getAvailableValueNames();

}
