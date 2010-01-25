package net.java.dev.moskito.webcontrol.repository;

public abstract class NumberAttribute<T extends Number> extends Attribute {

	NumberAttribute(String aName) {
		super(aName);
		// TODO Auto-generated constructor stub
	}
	
	@Override public String getValueString(){
		return "" + getValue();
	}
	
	@Override public abstract T getValue();
	
	// TODO calculation does not count on overflow
	public Double makeCalculation(NumberAttribute<?> attribute, Operation operation) {
		double result = 0.0;
		switch (operation) {
			case ADD: result = getValue().doubleValue() + attribute.getValue().doubleValue(); break;
			case SUBSTRACT: result = getValue().doubleValue() - attribute.getValue().doubleValue(); break;
			case DIVIDE: result = getValue().doubleValue() / attribute.getValue().doubleValue(); break;
			case MULTIPLY: result = getValue().doubleValue() * attribute.getValue().doubleValue(); break;
		}
		return result;
	}
	
	public static enum Operation {
		ADD, SUBSTRACT, DIVIDE, MULTIPLY
	}

}
