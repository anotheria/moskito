package net.java.dev.moskito.webui.bean;

import java.util.ArrayList;
import java.util.List;

public enum NaviItem {
	PRODUCERS("Producers"),
	USECASES("Use Cases"),
	SESSIONS("Monitoring Sessions"),
	NONE("None");
	
	private static ArrayList<NaviItem> menu;
	static{
		menu = new ArrayList<NaviItem>();
		menu.add(PRODUCERS);
		menu.add(USECASES);
		menu.add(SESSIONS);
	}
	
	private String caption;
	
	private NaviItem(String aCaption){
		caption = aCaption;
	}
	
	public String getId(){
		return toString().toLowerCase();
	}
	
	public String getCaption(){
		return caption;
	}
	
	public static final List<NaviItem> getMenu(){
		return menu;
	}
}
