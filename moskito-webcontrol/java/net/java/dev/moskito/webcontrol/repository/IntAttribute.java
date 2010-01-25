package net.java.dev.moskito.webcontrol.repository;

public class IntAttribute extends NumberAttribute<Integer> {
	
	private int value;
	
	public IntAttribute(String aName, int aValue){
		super(aName);
		value = aValue;
	}
	
	public IntAttribute(String aName, String aValue){
		this(aName, Integer.parseInt(aValue));
	}
	
	@Override public Integer getValue() {
		return Integer.valueOf(value);
	}
	
}
