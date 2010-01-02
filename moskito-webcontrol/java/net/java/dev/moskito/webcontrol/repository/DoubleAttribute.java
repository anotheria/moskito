package net.java.dev.moskito.webcontrol.repository;


public class DoubleAttribute extends Attribute{

	private double value;
	
	public DoubleAttribute(String name, double aValue){
		super(name);
		value = aValue;
	}
	
	public DoubleAttribute(String name, String aValue){
		this(name, Double.parseDouble(aValue));
	}
	
	@Override public String getValueString() {
		return ""+value;
	}
	
	@Override public Double getValue() {
		return Double.valueOf(value);
	}

}
