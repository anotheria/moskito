package net.anotheria.moskito.webui.accumulators.api;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

/**
 * This object contains multiple charts.
 *
 * @author lrosenberg
 * @since 27.06.15 16:19
 */
public class MultilineChartAO implements Serializable{
	/**
	 * Chart data.
	 */
	private List<AccumulatedValueAO> data;
	/**
	 * Line names.
	 */
	private List<String> names;
	/**
	 * Collection of charts.
	 */
	private List<AccumulatedSingleGraphAO> singleGraphAOs;

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

	public List<AccumulatedSingleGraphAO> getSingleGraphAOs() {
		return singleGraphAOs;
	}

	public void setSingleGraphAOs(List<AccumulatedSingleGraphAO> singleGraphAOs) {
		this.singleGraphAOs = singleGraphAOs;
	}

	/**
	 * Maps collection of {@link AccumulatedSingleGraphAO} to JSON representation.
	 * Accumulator will be mapped only if accumulator has the preconfigured color.
	 *
	 * @return JSON array with accumulators colors
	 */
	public JSONArray getAccumulatorsColorsDataJSON() {
		final JSONArray jsonArray = new JSONArray();
		if (singleGraphAOs == null || singleGraphAOs.isEmpty())
			return jsonArray;

		for (AccumulatedSingleGraphAO graphAO : singleGraphAOs) {
			if (StringUtils.isEmpty(graphAO.getName()) || StringUtils.isEmpty(graphAO.getColor()))
				continue;

			final JSONObject jsonObject = graphAO.mapColorDataToJSON();
			jsonArray.put(jsonObject);
		}

		return jsonArray;
	}

	@Override
	public String toString() {
		return "MultilineChartAO{" +
				"data=" + data +
				", names=" + names +
				", singleGraphAOs=" + singleGraphAOs +
				'}';
	}
}
