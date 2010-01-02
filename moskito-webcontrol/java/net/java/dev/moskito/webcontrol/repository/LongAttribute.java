package net.java.dev.moskito.webcontrol.repository;

public class LongAttribute extends Attribute {

	private long value;
	
	public LongAttribute(String name, long aValue){
		super(name);
		value = aValue;
	}
	
	public LongAttribute(String name, String aValue){
		this(name, Long.parseLong(aValue));
	}
	
	@Override public String getValueString() {
		return ""+value;
	}

	@Override public Long getValue() {
		return Long.valueOf(value);
	}
}
