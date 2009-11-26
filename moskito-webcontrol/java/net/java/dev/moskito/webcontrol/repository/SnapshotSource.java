package net.java.dev.moskito.webcontrol.repository;

public class SnapshotSource {
	private String name;
	
	public SnapshotSource(String aName){
		name = aName;
	}
	
	@Override public boolean equals(Object o){
		return o instanceof SnapshotSource &&
			name.equals((SnapshotSource)o);
	}
	
	@Override public int hashCode(){
		return name.hashCode();
	}
	
	public String toString(){
		return name;
	}
}
