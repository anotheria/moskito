package net.anotheria.moskito.webui.accumulators.api;

import net.anotheria.util.StringUtils;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * This bean contains data for a single graph in multiple graph mode.
 * @author lrosenberg
 *
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AccumulatedSingleGraphAO {
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
	 * This is a helper map that contains characters an accumulator name can contains, that are prohibited in js variables and therefore have to be mapped.
	 */
	private static final HashMap<String, String> jsReplaceMap;
	static{
		jsReplaceMap = new HashMap<String, String>();
		jsReplaceMap.put(" ", "_");
		jsReplaceMap.put("-", "_");
		jsReplaceMap.put("+", "_");
		jsReplaceMap.put(".", "_");
	}
	
	public AccumulatedSingleGraphAO(String aName){
		name = aName;
		data = new ArrayList<AccumulatedValueAO>();
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
}
