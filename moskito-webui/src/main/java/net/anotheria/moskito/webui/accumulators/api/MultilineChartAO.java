package net.anotheria.moskito.webui.accumulators.api;

import java.util.List;

/**
 * This object contains multiple charts.
 *
 * @author lrosenberg
 * @since 27.06.15 16:19
 */
public class MultilineChartAO {
	private List<AccumulatedValueAO> data;
	private List<String> names;

	public List<AccumulatedValueAO> getData() {
		return data;
	}

	public void setData(List<AccumulatedValueAO> data) {
		this.data = data;
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}
}
