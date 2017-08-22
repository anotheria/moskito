package net.anotheria.moskito.extensions.analyze.connector;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a single stat.
 *
 * @author esmakula
 */
public class Stat {

	/**
	 * Stat values.
	 */
	private String statName;

	/**
	 * Stat values.
	 */
	private Map<String, String> values = new HashMap<>();

	public String getStatName() {
		return statName;
	}

	public void setStatName(String statName) {
		this.statName = statName;
	}

	public Map<String, String> getValues() {
		if (values == null)
			values = new HashMap<>();
		return values;
	}

	public void setValues(Map<String, String> values) {
		this.values = values;
	}
}
