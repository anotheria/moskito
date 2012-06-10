package net.java.dev.moskito.webui.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.anotheria.util.StringUtils;
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
