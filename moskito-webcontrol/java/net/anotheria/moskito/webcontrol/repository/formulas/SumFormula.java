package net.anotheria.moskito.webcontrol.repository.formulas;

import net.anotheria.moskito.webcontrol.repository.Attribute;
import net.anotheria.moskito.webcontrol.repository.NumberAttribute;

public class SumFormula implements Formula {

	static final Formula instance = new SumFormula();

	private SumFormula() {
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

	@Override
	public Number calculate(Attribute... inputs) {
		double result = 0.0;
		for (Attribute input : inputs) {		
			result += ((NumberAttribute<?>) input).getValue().doubleValue();
		}
		return result;
	}

}