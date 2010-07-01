package net.java.dev.moskito.webcontrol.repository;

import net.java.dev.moskito.webcontrol.repository.formulas.AVGFormula;
import net.java.dev.moskito.webcontrol.repository.formulas.MAXFormula;
import net.java.dev.moskito.webcontrol.repository.formulas.MINFormula;
import net.java.dev.moskito.webcontrol.repository.formulas.SumFormula;

public enum TotalFormulaType {
	
	SUM(SumFormula.class.getSimpleName()),
	AVG(AVGFormula.class.getSimpleName()),
	MIN(MINFormula.class.getSimpleName()),
	MAX(MAXFormula.class.getSimpleName()),
	EMPTY("");
	
	private String formulaClass;
	
	private TotalFormulaType(String formulaClass) {
		this.formulaClass = formulaClass;
	}
	
	public static TotalFormulaType convert(String v) {
		for (TotalFormulaType value : values()) {
			if (value.name().equalsIgnoreCase(v)) {
				return value;
			}
		}
		return EMPTY;
	}

	public String getFormulaClass() {
		return formulaClass;
	}
	
}
