package net.anotheria.moskitodemo.counter.action;

import net.anotheria.maf.action.Action;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskitodemo.counter.PaymentCounter;
import net.anotheria.moskitodemo.counter.PaymentCounterByParameter;
import net.anotheria.moskitodemo.counter.PaymentCounterMethodBased;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * This dummy action can be used to
 *
 * @author lrosenberg
 * @since 19.11.12 13:23
 */
public class PaymentAction implements Action {
	private static PaymentCounter paymentCounter1 = new PaymentCounter();
	private static PaymentCounterByParameter paymentCounter2 = new PaymentCounterByParameter();
	private static PaymentCounterMethodBased paymentCounter3 = new PaymentCounterMethodBased();

	@Override
	public void preProcess(ActionMapping actionMapping, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
	}

	@Override
	public ActionCommand execute(ActionMapping actionMapping, FormBean formBean, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

		//simply add some payment 1
		paymentCounter1.cc();paymentCounter1.cc();paymentCounter1.ec();paymentCounter1.paypal();

		//2
		paymentCounter2.pay("cc", true);paymentCounter2.pay("cc", true);paymentCounter2.pay("ec", true);paymentCounter2.pay("paypal", true);
		paymentCounter2.pay("cc", false);//one failed payment

		paymentCounter3.pay("cc");paymentCounter3.pay("cc");paymentCounter3.pay("ec");paymentCounter3.pay("paypal");paymentCounter3.pay("paypal");

		return null;  //To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void postProcess(ActionMapping actionMapping, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
	}
}
