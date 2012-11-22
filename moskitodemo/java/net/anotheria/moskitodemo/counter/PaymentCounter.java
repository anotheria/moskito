package net.anotheria.moskitodemo.counter;

import net.anotheria.moskito.aop.annotation.Count;

/**
 * Demonstration of a payment counter with @Count aop on class level.
 *
 * @author lrosenberg
 * @since 19.11.12 09:14
 */
@Count
public class PaymentCounter {
	/**
	 * Electronic card payment (lastchrifteinzug in germany).
	 */
	public void ec(){}

	/**
	 * Credit card payment.
	 */
	public void cc(){}

	/**
	 * Payment via paypal.
	 */
	public void paypal(){}
}
