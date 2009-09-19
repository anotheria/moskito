package net.java.dev.moskitodemo.usecases.fibonacci.business;

public class FibonacciCalculatorImpl implements IFibonacciCalculator{

	private IFibonacciCalculator outerInstance;
	
	public long calculateFibonacciNumber(int order) {
		return order > 2 ? 
				outerInstance.calculateFibonacciNumber(order-1) + outerInstance.calculateFibonacciNumber(order-2) :
					order == 1 || order == 2 ? 1 : 0
				;
	}
	
	void setOuterInstance(IFibonacciCalculator anInstance){
		outerInstance = anInstance;
	}
	
	public static void main(String a[]){
		FibonacciCalculatorImpl impl = new FibonacciCalculatorImpl();
		for (int i=0; i<=30; i++){
			System.out.println("f("+i+") = " +impl.calculateFibonacciNumber(i));
		}
	}

}
