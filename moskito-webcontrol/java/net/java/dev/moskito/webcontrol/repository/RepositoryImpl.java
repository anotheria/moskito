package net.java.dev.moskito.webcontrol.repository;

import java.util.List;
import java.util.concurrent.ConcurrentMap;

public class RepositoryImpl {
	private ConcurrentMap<String, Container> containers;
	
	public List<Snapshot> getSnapshots(String containerName){
		return getContainer(containerName).getSnapshots();
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
}
