package net.java.dev.moskito.webcontrol.configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

public enum ConfigurationRepository {
	INSTANCE;
	
	private ConcurrentMap<String, ViewConfiguration> views;
	private List<SourceConfiguration> sources;
	
	private ConfigurationRepository(){
		views = new ConcurrentHashMap<String, ViewConfiguration>();
		sources = new CopyOnWriteArrayList<SourceConfiguration>();
	}
	
	
	public void addView(ViewConfiguration toAdd){
		views.put(toAdd.getName(), toAdd);
	}
	
	public List<ViewConfiguration> getAllViews(){
		ArrayList<ViewConfiguration> ret = new ArrayList<ViewConfiguration>();
		ret.addAll(views.values());
		return ret;
	}
	
	public ViewConfiguration getView(String viewName){
		ViewConfiguration ret = views.get(viewName);
		if (ret==null)
			throw new IllegalArgumentException("Unknown view: "+viewName);
		return ret;
	}
	
	public List<String> getViewNames(){
		ArrayList<String> ret = new ArrayList<String>();
		ret.addAll(views.keySet());
		Collections.sort(ret);
		return ret;
	}
	
	public List<SourceConfiguration> getSources(){
		ArrayList<SourceConfiguration> ret = new ArrayList<SourceConfiguration>();
		ret.addAll(sources);
		return ret;
	}
	
	public void addSource(SourceConfiguration toAdd){
		sources.add(toAdd);
	}
}
