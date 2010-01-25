package net.java.dev.moskito.webcontrol.repository;

import net.java.dev.moskito.webcontrol.repository.formulas.Formula;
import net.java.dev.moskito.webcontrol.repository.formulas.FormulaFactory;


public class FormulaAttribute extends Attribute {

	private Object value;
	
	public FormulaAttribute(String name, String formulaClass, Attribute... inputs) {
		super(name);
		Formula f = FormulaFactory.getFormula(formulaClass);
		if(checkInputs(f, inputs)) {
//			Number[] values = new Number[inputs.length];
//			for (int i = 0; i < inputs.length; i++) {
//				values[i] = inputs[i].getValue();
//			}
			value = f.calculate(inputs);
		}
	}
	
	private boolean checkInputs(Formula f, Attribute[] inputs) {
		if (inputs != null && f.isArgumentsAcceptable(inputs)) {
			for (Attribute a : inputs) {
				if (a instanceof FormulaAttribute) {
					//TODO ensure there is no cyclic dependency
				}
			}
			return true;
		}
		return false;
	}

	@Override public String getValueString() {
		return ""+value;
	}
	
	@Override public Object getValue() {
		return value;
	}

}
