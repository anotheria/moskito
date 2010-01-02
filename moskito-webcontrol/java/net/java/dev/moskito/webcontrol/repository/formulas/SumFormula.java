package net.java.dev.moskito.webcontrol.repository.formulas;


public class SumFormula implements Formula {

	static final Formula instance = new SumFormula();
	
	private SumFormula(){};
	
	@Override public boolean isArgumentNumberAcceptable(int num) {
		return num > 1;
	}
	
	@Override public Number calculate(Number... inputs) {
		// TODO Auto-generated method stub
		return null;
	}


}
