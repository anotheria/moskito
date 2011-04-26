package net.java.dev.moskito.webui.bean;

import java.util.ArrayList;
import java.util.List;

public class AccumulatorDataBean {
	private String description;
	private List<AccumulatedValueBean> values;
	
	public AccumulatorDataBean (){
		values = new ArrayList<AccumulatedValueBean>();
	}
	
	public void addValue(String value, String time){
		addValue(new AccumulatedValueBean(value, time));
	}
	
	public void addValue(AccumulatedValueBean bean){
		values.add(bean);
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<AccumulatedValueBean> getValues() {
		return values;
	}
}
