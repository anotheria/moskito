package net.java.dev.moskitodemo.usecases.fibonacci.presentation.action;

import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.anotheria.maf.action.AbstractAction;
import net.anotheria.maf.action.ActionCommand;
import net.anotheria.maf.action.ActionMapping;
import net.anotheria.maf.bean.FormBean;
import net.java.dev.moskitodemo.usecases.fibonacci.business.FibonacciCalculatorFactory;
import net.java.dev.moskitodemo.usecases.fibonacci.business.IFibonacciCalculator;

public class FibonacciCalculatorAction extends AbstractAction{

	private IFibonacciCalculator calculator;
	private AtomicInteger counter;
	
	public FibonacciCalculatorAction(){
		calculator = FibonacciCalculatorFactory.createFibonacciCalculator();
		counter = new AtomicInteger(0);
	}

	@Override
	public ActionCommand execute(ActionMapping mapping, FormBean af, HttpServletRequest req, HttpServletResponse res) throws Exception {

		
		String pOrder = req.getParameter("pOrder");
		if (pOrder==null || pOrder.length()==0){
			req.setAttribute("useCaseName", "fibonacci-"+counter.incrementAndGet());
			return mapping.dialog();
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
		
		return mapping.success();
	}

}
