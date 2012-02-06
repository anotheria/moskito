package net.java.dev.moskito.core.predefined;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import net.java.dev.moskito.core.calltrace.CurrentlyTracedCall;
import net.java.dev.moskito.core.calltrace.RunningTraceContainer;
import net.java.dev.moskito.core.dynamic.IOnDemandCallHandler;
import net.java.dev.moskito.core.dynamic.MoskitoInvokationProxy;
import net.java.dev.moskito.core.producers.IStatsProducer;

import org.junit.Test;

public class ServiceStatsCallHandlerTest {
	
	@Test public void testBasicFunctionallity(){
		_testBasicFunctionallity(new ServiceStatsCallHandler());
		_testBasicFunctionallity(new ServiceStatsCallHandlerWithCallSysout());
	}
	
	private void _testBasicFunctionallity(IOnDemandCallHandler handler){
		TestServiceImpl impl = new TestServiceImpl();
		MoskitoInvokationProxy proxy = new MoskitoInvokationProxy(impl, new ServiceStatsCallHandler(), new ServiceStatsFactory(), TestService.class);
		TestService service = (TestService)proxy.createProxy();
	
	
		for (int i=0; i<10; i++)
			service.increase();
		
		assertEquals("count doesn't match", 10, impl.getCount());
		
		IStatsProducer p = proxy.getProducer();
		ServiceStats stats = (ServiceStats)p.getStats().get(0);
		
		assertEquals("monitored count doesn't match", 10, stats.getTotalRequests());
		
		
	}

	
	
	@Test public void testErrors(){
		_testErrors(new ServiceStatsCallHandler());
		_testErrors(new ServiceStatsCallHandlerWithCallSysout());
	}
	
	private void _testErrors(IOnDemandCallHandler handler){
		TestServiceImpl impl = new TestServiceImpl();
		MoskitoInvokationProxy proxy = new MoskitoInvokationProxy(impl, new ServiceStatsCallHandler(), new ServiceStatsFactory(), TestService.class);
		TestService service = (TestService)proxy.createProxy();
	
	
		for (int i=0; i<10; i++)
			service.increase();
		try{
			service.throwException();
			fail("Exception expected");
		}catch(Exception e){}
		try{
			service.throwException();
			fail("Exception expected");
		}catch(Exception e){}
		
		assertEquals("count doesn't match", 10, impl.getCount());
		
		IStatsProducer p = proxy.getProducer();
		ServiceStats stats = (ServiceStats)p.getStats().get(0);
		
		assertEquals("monitored count doesn't match", 12, stats.getTotalRequests());
		assertEquals("monitored count doesn't match", 2, stats.getErrors());
		
		
	}
	
	@Test public void testInUseCase(){
		_testInUseCase(new ServiceStatsCallHandler());
//		_testInUseCase(new ServiceStatsCallHandlerWithCallSysout());
	}
	
	private void _testInUseCase(IOnDemandCallHandler handler){
		RunningTraceContainer.startTracedCall("test");
		TestServiceImpl impl = new TestServiceImpl();
		MoskitoInvokationProxy proxy = new MoskitoInvokationProxy(impl, handler, new ServiceStatsFactory(), TestService.class);
		TestService service = (TestService)proxy.createProxy();
		CurrentlyTracedCall useCase = (CurrentlyTracedCall) RunningTraceContainer.getCurrentlyTracedCall();
		service.increase();
		assertFalse(useCase.getLastStep().isAborted());
		try{
			service.throwException();
			fail("Exception expected");
		}catch(Exception e){}
		assertTrue(useCase.callTraced());
		
		assertTrue(useCase.getLastStep().isAborted());
		
		String ret = service.operationWithParameter(1111, "HelloWorld" , false);
		String useCase2string = useCase.getLastStep().toString();
		assertTrue(useCase2string.indexOf(ret)>-1);
		assertTrue(useCase2string.indexOf("HelloWorld")>-1);
		assertTrue(useCase2string.indexOf("1111")>-1);
		assertTrue(useCase2string.indexOf("false")>-1);
		
	}
}
