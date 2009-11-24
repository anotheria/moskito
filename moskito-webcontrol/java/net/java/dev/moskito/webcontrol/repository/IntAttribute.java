package net.java.dev.moskito.webcontrol.repository;

public class IntAttribute extends Attribute{
	private int value;
	
	public IntAttribute(String aName, int aValue){
		super(aName);
		value = aValue;
	}
	
	public String getValueString(){
		return ""+value;
	}
}
