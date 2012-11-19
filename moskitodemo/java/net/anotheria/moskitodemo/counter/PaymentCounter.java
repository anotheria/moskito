package net.anotheria.moskitodemo.counter;

import net.anotheria.moskito.annotation.Count;

/**
 * Demonstration of a payment counter with @Count annotation on class level.
 *
 * @author lrosenberg
 * @since 19.11.12 09:14
 */
@Count
public class PaymentCounter {
	/**
	 * Electronic card payment (lastchrifteinzug in germany).
	 */
	public void electroniccash(){}

	/**
	 * Credit card payment.
	 */
	public void creditcard(){}

	/**
	 * Payment via paypal.
	 */
	public void paypal(){}
}
