package net.java.dev.moskito.webcontrol.repository.formulas;

import net.java.dev.moskito.webcontrol.repository.Attribute;

public interface Formula {

	public Object calculate(Attribute... inputs);
	
	public boolean isArgumentsAcceptable(Attribute... args);
	
}
