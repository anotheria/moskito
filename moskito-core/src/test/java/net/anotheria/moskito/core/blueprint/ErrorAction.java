package net.anotheria.moskito.core.blueprint;

public class ErrorAction implements Action{

	@Override
	public int perform(int a, int b) {
		throw new RuntimeException("Error");
	}
	
}
