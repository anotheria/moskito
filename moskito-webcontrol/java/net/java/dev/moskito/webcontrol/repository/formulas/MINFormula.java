package net.java.dev.moskito.webcontrol.repository.formulas;

import net.java.dev.moskito.webcontrol.repository.Attribute;
import net.java.dev.moskito.webcontrol.repository.NumberAttribute;

public class MINFormula implements Formula {

	static final Formula instance = new MINFormula();

	private MINFormula() {
	}

	@Override
	public Object calculate(Attribute... inputs) {
		Attribute min = inputs[0];
		for (int i=1; i<inputs.length; i++) {
			Attribute curr = inputs[i];
			if (Math.min(((NumberAttribute<?>) min).getValue().doubleValue(), ((NumberAttribute<?>) curr).getValue().doubleValue()) != ((NumberAttribute<?>) min).getValue().doubleValue()) {
				min = curr;
			}
		}
		return ((NumberAttribute<?>) min).getValue().doubleValue();
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
