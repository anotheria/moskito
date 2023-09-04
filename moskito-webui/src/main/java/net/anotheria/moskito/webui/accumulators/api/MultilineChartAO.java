package net.anotheria.moskito.webui.accumulators.api;

//import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * This object contains multiple charts.
 *
 * @author lrosenberg
 * @since 27.06.15 16:19
 */
@XmlRootElement(name = "MultilineChart")
@XmlAccessorType(XmlAccessType.FIELD)
public class MultilineChartAO implements Serializable{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = -5019792278106803782L;

	/**
	 * Chart data.
	 */
	@XmlElement
	private List<AccumulatedValueAO> data;
	/**
	 * Line names.
	 */
	@XmlElement
	private List<String> names;
	/**
	 * Collection of charts.
	 */
	@XmlElement
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
		if (names != null)
			Collections.sort(names);
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
	@JsonIgnore
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		MultilineChartAO that = (MultilineChartAO) o;

		return names.equals(that.names);
	}

	@Override
	public int hashCode() {
		return names.hashCode();
	}
}
