package net.java.dev.moskitodemo.usecases.fibonacci.presentation.action;

import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import net.java.dev.moskito.web.MoskitoAction;
import net.java.dev.moskitodemo.usecases.fibonacci.business.FibonacciCalculatorFactory;
import net.java.dev.moskitodemo.usecases.fibonacci.business.IFibonacciCalculator;

public class FibonacciCalculatorAction extends MoskitoAction{

	private IFibonacciCalculator calculator;
	private AtomicInteger counter;
	
	public FibonacciCalculatorAction(){
		calculator = FibonacciCalculatorFactory.createFibonacciCalculator();
		counter = new AtomicInteger(0);
	}

	@Override
	public ActionForward moskitoExecute(ActionMapping mapping, ActionForm af, HttpServletRequest req, HttpServletResponse res) throws Exception {

		
		String pOrder = req.getParameter("pOrder");
		if (pOrder==null || pOrder.length()==0){
			req.setAttribute("useCaseName", "fibonacci-"+counter.incrementAndGet());
			return mapping.findForward("dialog");
		}
		
		int order = 0;
		try{
			order = Integer.parseInt(req.getParameter("pOrder"));
		}catch(Exception ignored){}
		
		if (order<0 || order>20)
			throw new RuntimeException("Order should be between 0 and 20.");
		
		String useCaseName = req.getParameter("mskUseCaseName");
		
		long result = calculator.calculateFibonacciNumber(order);
		
		req.setAttribute("result", result);
		req.setAttribute("order", order);
		req.setAttribute("useCaseName", useCaseName);
		
		return mapping.findForward("success");
	}

}
