package net.anotheria.moskitodemo.counter;

import net.anotheria.moskito.annotation.Count;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 19.11.12 09:15
 */
public class PaymentCounterMethodBased {

	public void pay(String method){
		//do some important payment stuff

		//count up
		if (method.equals("ec")){
			ec();
		}
		if (method.equals("cc")){
			cc();
		}
		if (method.equals("paypal")){
			paypal();
		}
	}

	@Count
	private void ec(){

	}

	@Count
	private void cc(){

	}

	@Count
	private void paypal(){

	}
}
