package net.anotheria.moskito.webui.accumulators.api;

import net.anotheria.util.StringUtils;
import net.anotheria.util.log.LogMessageUtil;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.LoggerFactory;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This bean contains data for a single graph in multiple graph mode.
 * @author lrosenberg
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AccumulatedSingleGraphAO implements Serializable{
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 8718130806694168007L;

	/**
	 * Name of the graph.
	 */
	@XmlElement
	private String name;
	/**
	 * Graph data.
	 */
	@XmlElement
	private List<AccumulatedValueAO> data;
	/**
	 * Graph color.
	 * Html color value, e.g. #RRGGBB.
	 */
	@XmlElement
	private String color;

	/**
	 * This is a helper map that contains characters an accumulator name can contains, that are prohibited in js variables and therefore have to be mapped.
	 */
	private static final Map<String, String> jsReplaceMap;
	static{
		jsReplaceMap = new HashMap<>(4);
		jsReplaceMap.put(" ", "_");
		jsReplaceMap.put("-", "_");
		jsReplaceMap.put("+", "_");
		jsReplaceMap.put(".", "_");
	}
	
	public AccumulatedSingleGraphAO(String aName){
		name = aName;
		data = new ArrayList<>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<AccumulatedValueAO> getData() {
		return data;
	}
	public void setData(List<AccumulatedValueAO> data) {
		this.data = data;
	}

	public void add(AccumulatedValueAO valueBean) {
		data.add(valueBean);
	}
	
	public String getNameForJS(){
		String ret = name;
		ret = StringUtils.replace(ret, jsReplaceMap);
		return ret;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	/**
	 * Maps accumulator's color and name to JSON object.
	 *
	 * @return JSON object with accumulator color
	 */
	public JSONObject mapColorDataToJSON() {
		final JSONObject jsonObject = new JSONObject();

		try {
			jsonObject.put("name", name);
			jsonObject.put("color", color);
		} catch (JSONException e) {
			final String message = LogMessageUtil.failMsg(e);
			LoggerFactory.getLogger(AccumulatedSingleGraphAO.class).warn(message, e);
		}

		return jsonObject;
	}

	@Override
	public String toString() {
		return "AccumulatedSingleGraphAO{" +
				"data=" + data +
				", name='" + name + '\'' +
				", color='" + color + '\'' +
				'}';
	}
}
