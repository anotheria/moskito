package net.anotheria.moskitodemo.counter;

import net.anotheria.moskito.annotation.CountByParameter;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 19.11.12 10:24
 */
public class PaymentCounterByParameter {
	public void pay(String method, boolean success){
		//do some important payment stuff

		//of course we will decide on the previous important method stuff if we are successful or not.
		if (success)
			success(method);
		else
			failure(method);

	}

	@CountByParameter public void success(String method){};
	@CountByParameter public void failure(String method){};
}
