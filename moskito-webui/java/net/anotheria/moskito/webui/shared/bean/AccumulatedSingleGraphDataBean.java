package net.anotheria.moskito.webui.shared.bean;

import net.anotheria.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
/**
 * This bean contains data for a single graph in multiple graph mode.
 * @author lrosenberg
 *
 */
public class AccumulatedSingleGraphDataBean {
	/**
	 * Name of the graph.
	 */
	private String name;
	/**
	 * Graph data.
	 */
	private List<AccumulatedValueBean> data;

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
	
	public AccumulatedSingleGraphDataBean(String aName){
		name = aName;
		data = new ArrayList<AccumulatedValueBean>();
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<AccumulatedValueBean> getData() {
		return data;
	}
	public void setData(List<AccumulatedValueBean> data) {
		this.data = data;
	}

	public void add(AccumulatedValueBean valueBean) {
		data.add(valueBean);
	}
	
	public String getNameForJS(){
		String ret = name;
		ret = StringUtils.replace(ret, jsReplaceMap);
		return ret;
	}
}
