package net.java.dev.moskito.webcontrol.repository.formulas;

import net.java.dev.moskito.webcontrol.repository.Attribute;
import net.java.dev.moskito.webcontrol.repository.NumberAttribute;
import net.java.dev.moskito.webcontrol.repository.NumberAttribute.Operation;

public final class PercentFormula implements Formula {

	static final Formula instance = new PercentFormula();

	private PercentFormula() {
	};

	public boolean isArgumentsAcceptable(Attribute... args) {
		return args != null && args.length == 2 && args[0] instanceof NumberAttribute<?> && args[1] instanceof NumberAttribute<?>;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Number calculate(Attribute... inputs) {
		return ((NumberAttribute) inputs[0]).makeCalculation((NumberAttribute) inputs[1], Operation.DIVIDE);
	}

}
