package net.anotheria.moskito.extensions.sampling;

import java.util.Map;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 24.04.15 18:02
 */
public class Sample {
	private String producerId;
	private String statMapperId;
	private Map<String,String> values;

	public String getProducerId() {
		return producerId;
	}

	public void setProducerId(String producerId) {
		this.producerId = producerId;
	}

	public String getStatMapperId() {
		return statMapperId;
	}

	public void setStatMapperId(String statMapperId) {
		this.statMapperId = statMapperId;
	}

	public Map<String, String> getValues() {
		return values;
	}

	public void setValues(Map<String, String> values) {
		this.values = values;
	}

	@Override
	public String toString() {
		return "Sample{" +
				"producerId='" + producerId + '\'' +
				", statMapperId='" + statMapperId + '\'' +
				", values=" + values +
				'}';
	}
}
