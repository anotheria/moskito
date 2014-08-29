package net.anotheria.moskito.core.blueprint;

public class AddAction implements Action{

	@Override
	public int perform(int a, int b) {
		return a+b;
	}
	
}
