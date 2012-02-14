package net.java.dev.moskito.webui.bean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalyzeProducerCallsMapBean {
	private Map<String, AnalyzeProducerCallsBean> beans;
	private String name;
	
	public AnalyzeProducerCallsMapBean(String aName){
		name = aName;
		beans = new HashMap<String, AnalyzeProducerCallsBean>();
	}
	
	public String toString(){
		return name + " "+beans;
	}
	
	public void addProducerCall(String producerId, long duration){
		AnalyzeProducerCallsBean bean = beans.get(producerId);
		if (bean==null){
			bean = new AnalyzeProducerCallsBean(producerId);
			beans.put(producerId, bean);
		}
		bean.addCall(duration);
	}
	
	public String getName(){
		return name;
	}
	
	public List<AnalyzeProducerCallsBean> getProducerCallsBeans(){
		ArrayList<AnalyzeProducerCallsBean> ret = new ArrayList<AnalyzeProducerCallsBean>();
		ret.addAll(beans.values());
		return ret;
	}
	
	public boolean isEmpty(){
		return beans == null || beans.size() == 0;
	}
}
