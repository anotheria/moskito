package net.anotheria.moskito.webcontrol.repository;

public final class AttributeFactory {

	public static Attribute create(AttributeType type, String name, String value) {
		switch (type) {
			case LONG:
				return new LongAttribute(name, value);
			case INT:
				return new IntAttribute(name, value);
			case DOUBLE:
				return new DoubleAttribute(name, value);
			case STRING:
				return new StringAttribute(name, value);
			case NOT_FOUND:
				return new NotFoundAttribute(name);
		}
		throw new IllegalArgumentException("Unsupported attribute type: " + type);
	}

	public static FormulaAttribute createFormula(String name, String formulaClass, Attribute... inputs) {
		return new FormulaAttribute(name, formulaClass, inputs);
	}

	private AttributeFactory() {
	}
}
