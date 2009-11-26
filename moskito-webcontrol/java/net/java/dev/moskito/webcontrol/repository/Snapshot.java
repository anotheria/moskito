package net.java.dev.moskito.webcontrol.repository;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Snapshot {
	private SnapshotSource source;
	private long created;
	private ConcurrentMap<String, Attribute> attributes;
	
	public Snapshot(SnapshotSource aSource){
		source = aSource;
		created = System.currentTimeMillis();
		attributes = new ConcurrentHashMap<String, Attribute>();
	}
	
	public SnapshotSource getSource(){
		return source;
	}
	
	public long getCreatedTimestamp(){
		return created;
	}
	
	public Attribute addAttribute(Attribute a){
		return attributes.put(a.getName(), a);
	}
	
	public Attribute getAttribute(String attributeName){
		return attributes.get(attributeName);
	}
	
	public void removeAttribute(Attribute a){
		attributes.remove(a.getName());
	}
	
	@Override public String toString(){
		return source+" "+created+" "+attributes;
	}
}
