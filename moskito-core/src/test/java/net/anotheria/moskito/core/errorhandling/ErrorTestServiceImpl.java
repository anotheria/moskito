package net.anotheria.moskito.core.errorhandling;


public class ErrorTestServiceImpl implements ErrorTestService {
	@Override
	public void echo() {
		throw new IllegalArgumentException("Whatever");
	}
}
