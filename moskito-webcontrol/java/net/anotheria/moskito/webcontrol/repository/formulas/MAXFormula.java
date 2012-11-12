package net.anotheria.moskito.webcontrol.repository.formulas;

import net.anotheria.moskito.webcontrol.repository.Attribute;
import net.anotheria.moskito.webcontrol.repository.NumberAttribute;

public class MAXFormula implements Formula {

	static final Formula instance = new MAXFormula();

	private MAXFormula() {
	}

	@Override
	public Object calculate(Attribute... inputs) {
		Attribute max = inputs[0];
		for (int i=1; i<inputs.length; i++) {
			Attribute curr = inputs[i];
			if (Math.max(((NumberAttribute<?>) max).getValue().doubleValue(), ((NumberAttribute<?>) curr).getValue().doubleValue()) != ((NumberAttribute<?>) max).getValue().doubleValue()) {
				max = curr;
			}
		}
		return ((NumberAttribute<?>) max).getValue().doubleValue();
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
