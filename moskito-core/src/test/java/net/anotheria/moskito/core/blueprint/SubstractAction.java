package net.anotheria.moskito.core.blueprint;

public class SubstractAction implements Action{

	@Override
	public int perform(int a, int b) {
		return a-b;
	}
	
}
