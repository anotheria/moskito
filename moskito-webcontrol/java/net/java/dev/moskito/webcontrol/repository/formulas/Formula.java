package net.java.dev.moskito.webcontrol.repository.formulas;

public interface Formula {

	public Number calculate(Number... inputs);
	
	public boolean isArgumentNumberAcceptable(int num);
	
}
