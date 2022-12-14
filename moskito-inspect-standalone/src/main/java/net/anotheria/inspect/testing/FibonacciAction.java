package net.anotheria.inspect.testing;

import net.anotheria.maf.action.Action;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.anotheria.moskito.aop.annotation.Monitor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 23.04.14 16:33
 */
@Monitor
public class FibonacciAction implements Action {

	private FibonacciCalculator calculator = new FibonacciCalculator();

	@Override
	public void preProcess(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {

	}

	@Override
	public ActionCommand execute(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {
		int order = 10;
		try{
			order = Integer.parseInt(req.getParameter("order"));
		}catch(NumberFormatException e){}
		long result = calculator.calculateFibonacciNumber(order);
		res.getOutputStream().write(("Fibonacci of order " + order + " = " + result).getBytes());
		res.getOutputStream().flush();
		return null;
	}

	@Override
	public void postProcess(ActionMapping mapping, HttpServletRequest req, HttpServletResponse res) throws Exception {

	}
}
