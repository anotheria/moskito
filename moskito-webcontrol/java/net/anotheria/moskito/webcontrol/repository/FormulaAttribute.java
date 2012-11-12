package net.anotheria.moskito.webcontrol.repository;

import net.anotheria.moskito.webcontrol.repository.formulas.Formula;
import net.anotheria.moskito.webcontrol.repository.formulas.FormulaFactory;

public class FormulaAttribute extends Attribute {

	private Object value;

	public FormulaAttribute(String name, String formulaClass, Attribute... inputs) {
		super(name);
		Formula f = FormulaFactory.getFormula(formulaClass);
		if (f == null) {
			throw new RuntimeException("formula `" + formulaClass + "` does not exist");
		}
		if (checkInputs(f, inputs)) {
			value = f.calculate(inputs);
		}
	}

	private boolean checkInputs(Formula f, Attribute... inputs) {
		if (inputs != null && f.isArgumentsAcceptable(inputs)) {
			for (Attribute a : inputs) {
				if (a instanceof FormulaAttribute) {
					// TODO ensure there is no cyclic dependency
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public String getValueString() {
		return String.valueOf(value);
	}

	@Override
	public Object getValue() {
		return value;
	}

}
