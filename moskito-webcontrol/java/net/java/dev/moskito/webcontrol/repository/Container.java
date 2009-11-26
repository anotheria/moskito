package net.java.dev.moskito.webcontrol.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Container {
	private String name;
	private ConcurrentMap<SnapshotSource, Snapshot> snapshots;
	
	Container(String aName){
		name = aName;
		snapshots = new ConcurrentHashMap<SnapshotSource, Snapshot>();
	}
	
	List<Snapshot> getSnapshots(){
		ArrayList<Snapshot> list = new ArrayList<Snapshot>(snapshots.size());
		list.addAll(snapshots.values());
		return list;
	}
	
	void addSnapshot(Snapshot s){
		snapshots.put(s.getSource(), s);
	}
	
	public String getName(){
		return name;
	}
	
	@Override public boolean equals(Object o){
		return o instanceof Container && name.equals(((Container)o).name);
	}
	
	@Override public int hashCode(){
		return name.hashCode();
	}
}
