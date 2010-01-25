package net.java.dev.moskito.webcontrol.repository;

public abstract class Attribute {
	private String name;
	
	Attribute(String aName){
		name = aName;
	}
	
	@Override public boolean equals(Object anotherAttribute){
		return anotherAttribute instanceof Attribute && 
			name.equals(((Attribute)anotherAttribute).name);
	}
	
	@Override public int hashCode(){
		return name.hashCode();
	}
	
	public abstract String getValueString();
	
	public abstract Object getValue();
	
	public String getName(){
		return name;
	}
	
	public String toString(){
		return getName()+" = "+getValueString();
	}
}
