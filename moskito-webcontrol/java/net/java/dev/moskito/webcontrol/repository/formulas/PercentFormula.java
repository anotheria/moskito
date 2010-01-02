package net.java.dev.moskito.webcontrol.repository.formulas;


public final class PercentFormula implements Formula {

	static final Formula instance = new PercentFormula();
	
	private PercentFormula(){};
	
	public boolean isArgumentNumberAcceptable(int num) {
		return num == 2;
	}
	
	@Override public Number calculate(Number... inputs) {
		//TODO add check for NULL values and for 0
		return inputs[0].doubleValue() / inputs[1].doubleValue();
	}

}
