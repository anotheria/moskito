package net.anotheria.moskito.core.errorhandling;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 02.06.17 12:12
 */
public class ErrorTestServiceImpl implements ErrorTestService {
	@Override
	public void echo() {
		throw new IllegalArgumentException("Whatever");
	}
}
