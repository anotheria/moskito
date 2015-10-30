package net.anotheria.moskito.webui.producers.api;

import net.anotheria.moskito.core.decorators.value.StatValueAO;

import java.io.Serializable;
import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 25.03.14 15:37
 */
public class StatLineAO implements Serializable{
	private String statName;
	private List<StatValueAO> values;

	public String getStatName() {
		return statName;
	}

	public void setStatName(String statName) {
		this.statName = statName;
	}

	public List<StatValueAO> getValues() {
		return values;
	}

	public void setValues(List<StatValueAO> values) {
		this.values = values;
	}

	@Override
	public String toString() {
		return "StatLineAO{" +
				"statName='" + statName + '\'' +
				", values=" + values +
				'}';
	}
}
