package net.java.dev.moskito.webcontrol.repository;

public class DoubleAttribute extends NumberAttribute<Double> {

	private double value;

	public DoubleAttribute(String name, double aValue) {
		super(name);
		value = aValue;
	}

	public DoubleAttribute(String name, String aValue) {
		this(name, Double.parseDouble(aValue));
	}

	@Override public Double getValue() {
		return Double.valueOf(value);
	}

}
