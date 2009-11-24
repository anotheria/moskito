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
}
