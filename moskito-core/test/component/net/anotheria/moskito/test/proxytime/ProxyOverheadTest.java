package net.anotheria.moskito.test.proxytime;


import net.anotheria.moskito.core.dynamic.MoskitoInvokationProxy;
import net.anotheria.moskito.core.predefined.ServiceStatsCallHandler;
import net.anotheria.moskito.core.predefined.ServiceStatsFactory;

public class ProxyOverheadTest {
	
	public static final int ITERATIONS = 10000000;
	
	public static void main(String a[]){
		Test impl1 = new TestImpl();
	    MoskitoInvokationProxy proxy = new MoskitoInvokationProxy(
	    		impl1,
	            new ServiceStatsCallHandler(),
	            new ServiceStatsFactory(),
	            "ExceptionPassingTest",
	            "test",
	            "test",
	            Test.class
	        );
	    Test impl2 =(Test) proxy.createProxy();
	        
	    test(impl1, 40000);
	    test(impl2, 40000);

	    long time1 = test(impl1, ITERATIONS);
	    long time2 = test(impl2, ITERATIONS);
	    
	    time1 /= 1000;
	    time2 /= 1000;
	    
	    System.out.println("Time1:"+time1);
	    System.out.println("Time2:"+time2);
	    
	}
	
	private static long test(Test target, int iterations){
		long start = System.nanoTime();
		for (int i=0; i<iterations; i++)
			target.doSomething();
		long end = System.nanoTime();
		return (end-start);
	}
	
}
