package net.java.dev.moskito.webcontrol.repository;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public enum Repository {
	INSTANCE;
	
	private ConcurrentMap<String, Container> containers;
	
	Repository() {
		containers = new ConcurrentHashMap<String, Container>();
	}
	
	public List<Snapshot> getSnapshots(String containerName){
		return getContainer(containerName).getSnapshots();
	}

	//in the future add a method which supplies a maximum age for a snapshot 
	public Snapshot getSnapshot(String containerName, SnapshotSource source){
		Snapshot s = getContainer(containerName).getSnapshot(source);
		if (s==null)
			throw new NullPointerException("Snapshot "+containerName+" from "+source+" not found.");
		return s;
	}

	public void addSnapshot(String name, Snapshot snapshot){
		getContainer(name).addSnapshot(snapshot);
	}
	
	private Container getContainer(String name){
		Container c = containers.get(name);
		if (c==null){
			c = new Container(name);
			containers.putIfAbsent(name, c);
		}
		return containers.get(name);
	}
	
	void clear(){
		containers.clear();
	}
}
