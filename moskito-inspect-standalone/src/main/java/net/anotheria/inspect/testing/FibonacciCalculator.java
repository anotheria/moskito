package net.anotheria.inspect.testing;

import net.anotheria.moskito.aop.annotation.Monitor;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 23.04.14 16:31
 */
@Monitor
public class FibonacciCalculator {
	public long calculateFibonacciNumber(int order) {
		return order > 2 ?
				calculateFibonacciNumber(order-1) + calculateFibonacciNumber(order-2) :
				order == 1 || order == 2 ? 1 : 0
				;
	}
}
