package net.java.dev.moskito.webui.bean;

import java.net.URLEncoder;

public class RecordedUseCaseListItemBean {
	private String name;
	private String date;
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	
	}

	@SuppressWarnings("deprecation")
	public String getNameEncoded(){
		return URLEncoder.encode(getName());
	}
	
	
}
