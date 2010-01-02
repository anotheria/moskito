package net.java.dev.moskito.webcontrol.repository.formulas;

import java.util.HashMap;

public final class FormulaFactory {
	
	private FormulaFactory(){};
	
	private static HashMap<String,Formula> formulas = new HashMap<String,Formula>();

	static {
		formulas.put("PercentFormula", PercentFormula.instance);
		formulas.put("SumFormula", SumFormula.instance);
	}
	
	public static Formula getFormula(String formulaName) {
		return formulas.get(formulaName);
	}

}
