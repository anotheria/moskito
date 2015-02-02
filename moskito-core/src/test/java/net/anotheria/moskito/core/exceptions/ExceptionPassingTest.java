package net.anotheria.moskito.core.exceptions;

import net.anotheria.moskito.core.dynamic.MoskitoInvokationProxy;
import net.anotheria.moskito.core.predefined.ServiceStatsCallHandler;
import net.anotheria.moskito.core.predefined.ServiceStatsFactory;
import org.junit.Test;
import static org.junit.Assert.*;

public class ExceptionPassingTest {

	@Test public void testUnmonitoredInstance(){
		test(new ExcServiceImpl());
	}

	@Test public void testMonitoredInstance(){
		IExcService service = new ExcServiceImpl();
		MoskitoInvokationProxy proxy = new MoskitoInvokationProxy(
				service,
				new ServiceStatsCallHandler(),
				new ServiceStatsFactory(),
				IExcService.class
		);

		test ((IExcService)proxy.createProxy());
	}


	private void test(IExcService service){

		try{
			service.throwDeclaredException();
			fail("Exception should be thrown.");
		}catch(ExcServiceException e){
		}catch(RuntimeException e){
			fail("Expected exception of type "+ExcServiceDetailedException.class+", got RuntimeException "+e.getClass());
		}

		try{
			service.throwRuntimeException();
			fail("Exception should be thrown.");
		}catch(ExcServiceException e){
			fail("Expected runtime exception, got exception of type "+ExcServiceDetailedException.class);
		}catch(RuntimeException e){
		}
	}
}
