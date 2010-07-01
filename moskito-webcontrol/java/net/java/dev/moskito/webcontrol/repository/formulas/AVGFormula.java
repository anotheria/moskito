package net.java.dev.moskito.webcontrol.repository.formulas;

import net.java.dev.moskito.webcontrol.repository.Attribute;
import net.java.dev.moskito.webcontrol.repository.NumberAttribute;

public class AVGFormula implements Formula {

	static final Formula instance = new AVGFormula();

	private AVGFormula() {
	}

	@Override
	public Object calculate(Attribute... inputs) {
		double result = 0.0;
		for (Attribute input : inputs) {
			result += ((NumberAttribute<?>) input).getValue().doubleValue();
		}
		return result / inputs.length;
	}

	@Override
	public boolean isArgumentsAcceptable(Attribute... args) {
		if (args.length > 1) {
			for (Attribute arg : args) {
				if (!(arg instanceof NumberAttribute<?>)) {
					return false;
				}
			}
		}
		return true;
	}

}
