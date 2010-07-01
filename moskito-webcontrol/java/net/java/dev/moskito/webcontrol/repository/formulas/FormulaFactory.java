package net.java.dev.moskito.webcontrol.repository.formulas;

import java.util.HashMap;

public final class FormulaFactory {
	
	private FormulaFactory(){};
	
	private static HashMap<String,Formula> formulas = new HashMap<String,Formula>();

	static {
		formulas.put(PercentFormula.class.getSimpleName(), PercentFormula.instance);
		formulas.put(SumFormula.class.getSimpleName(), SumFormula.instance);
		formulas.put(AVGFormula.class.getSimpleName(), AVGFormula.instance);
		formulas.put(MINFormula.class.getSimpleName(), MINFormula.instance);
		formulas.put(MAXFormula.class.getSimpleName(), MAXFormula.instance);
	}
	
	public static Formula getFormula(String formulaName) {
		return formulas.get(formulaName);
	}

}
