package net.anotheria.moskito.webcontrol.repository.formulas;

import net.anotheria.moskito.webcontrol.repository.Attribute;

public interface Formula {

	public Object calculate(Attribute... inputs);
	
	public boolean isArgumentsAcceptable(Attribute... args);
	
}
