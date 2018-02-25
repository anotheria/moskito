package net.anotheria.moskito.core.errorhandling;

import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.errorhandling.ErrorCatcherConfig;
import net.anotheria.moskito.core.config.errorhandling.ErrorCatcherTarget;
import net.anotheria.moskito.core.config.errorhandling.ErrorHandlingConfig;
import net.anotheria.moskito.core.context.MoSKitoContext;
import net.anotheria.moskito.core.dynamic.ProxyUtils;
import net.anotheria.moskito.core.stats.impl.IntervalRegistry;
import net.anotheria.moskito.core.timing.IUpdateable;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 01.06.17 17:01
 */
public class BuiltInErrorProducerTest {
	@Test
	public void testBasicCount(){
		BuiltInErrorProducer.getInstance().notifyError(new IllegalArgumentException());
		BuiltInErrorProducer.getInstance().notifyError(new IllegalArgumentException());
		BuiltInErrorProducer.getInstance().notifyError(new UnsupportedOperationException());

		assertEquals(2, BuiltInErrorProducer.getInstance().testingGetStatsForError(IllegalArgumentException.class).getTotal());
		assertEquals(1, BuiltInErrorProducer.getInstance().testingGetStatsForError(UnsupportedOperationException.class).getTotal());
		assertNull(BuiltInErrorProducer.getInstance().testingGetStatsForError(RuntimeException.class));

		assertEquals(3, BuiltInErrorProducer.getInstance().testingGetCumulatedStats().getTotal());

	}

	@Test
	public void testStatsObjectCount(){
		BuiltInErrorProducer.getInstance().notifyError(new IllegalArgumentException());
		assertEquals(2, BuiltInErrorProducer.getInstance().getStats().size());

	}


	@Test public void testClassExecution(){
		ErrorTestService service = ProxyUtils.createServiceInstance(new ErrorTestServiceImpl(), "foo", "foo", ErrorTestService.class );
		try{
			service.echo();
			fail("IllegalArgumentException should be thrown");
		}catch(IllegalArgumentException e){

		}

		//Ensure there are two entries, cumulated and IllegalArgumentException.
		assertEquals(2, BuiltInErrorProducer.getInstance().getStats().size());
		//Ensure there is one error for illegal argument exception.
		assertEquals(1, BuiltInErrorProducer.getInstance().testingGetStatsForError(IllegalArgumentException.class).getTotal());
		//Ensure there is one cumulated error.
		assertEquals(1, BuiltInErrorProducer.getInstance().testingGetCumulatedStats().getTotal());

	}

	@Test public void testCascadingExecutionWithCatch(){
		ErrorTestSecondService service = ProxyUtils.createServiceInstance(new ErrorTestSecondServiceImpl(), "foo", "foo", ErrorTestSecondService.class );
		service.catchingEcho();

		//there should only be the error from ErrorTestService.

		//Ensure there are two entries, cumulated and IllegalArgumentException.
		assertEquals(2, BuiltInErrorProducer.getInstance().getStats().size());
		//Ensure there is one error for illegal argument exception.
		assertEquals(1, BuiltInErrorProducer.getInstance().testingGetStatsForError(IllegalArgumentException.class).getTotal());
		//Ensure there is one cumulated error.
		assertEquals(1, BuiltInErrorProducer.getInstance().testingGetCumulatedStats().getTotal());

	}

	@Test public void testCascadingExecutionWithoutCatch(){

		MoskitoConfigurationHolder.getConfiguration().getErrorHandlingConfig().setCountRethrows(true);

		ErrorTestSecondService service = ProxyUtils.createServiceInstance(new ErrorTestSecondServiceImpl(), "foo", "foo", ErrorTestSecondService.class );
		try {
			service.nonCatchingEcho();
			fail("IllegalArgumentException should be thrown");
		}catch(IllegalArgumentException e){}

		//there should only be the error from ErrorTestService.

		//Ensure there are two entries, cumulated and IllegalArgumentException.
		assertEquals(2, BuiltInErrorProducer.getInstance().getStats().size());
		//Ensure there is two errors for illegal argument exception.
		assertEquals(1, BuiltInErrorProducer.getInstance().testingGetStatsForError(IllegalArgumentException.class).getTotal());
		//rethrown should count by 1
		assertEquals(1, BuiltInErrorProducer.getInstance().testingGetStatsForError(IllegalArgumentException.class).getRethrown());
		//Ensure there is two cumulated error.
		assertEquals(1, BuiltInErrorProducer.getInstance().testingGetCumulatedStats().getTotal());

	}

	@Test public void testInitialAndTotalCountWithCascading(){
		MoskitoConfigurationHolder.getConfiguration().getErrorHandlingConfig().setCountRethrows(true);

		ErrorTestSecondService service = ProxyUtils.createServiceInstance(new ErrorTestSecondServiceImpl(), "foo", "foo", ErrorTestSecondService.class );
		try {
			service.nonCatchingEcho();
			fail("IllegalArgumentException should be thrown");
		}catch(IllegalArgumentException e){}


		//Ensure there are one error in total and one rethrown
		assertEquals(1, BuiltInErrorProducer.getInstance().testingGetStatsForError(IllegalArgumentException.class).getTotal());
		assertEquals(1, BuiltInErrorProducer.getInstance().testingGetStatsForError(IllegalArgumentException.class).getRethrown());
		//Ensure there is one initial error
		assertEquals(1, BuiltInErrorProducer.getInstance().testingGetStatsForError(IllegalArgumentException.class).getInitial());

	}

	@Test public void testInitialAndTotalInMultipleRequests(){
		testInitialAndTotalCountWithSequentialCalls();
		before();
		testInitialAndTotalCountWithSequentialCalls();
		
	}

	@Test public void testInitialAndTotalCountWithSequentialCalls(){
		ErrorTestService service = ProxyUtils.createServiceInstance(new ErrorTestServiceImpl(), "foo", "foo", ErrorTestService.class );
		//make 3 calls.
		try {
			service.echo();
			fail("IllegalArgumentException should be thrown");
		}catch(IllegalArgumentException e){}
		try {
			service.echo();
			fail("IllegalArgumentException should be thrown");
		}catch(IllegalArgumentException e){}
		try {
			service.echo();
			fail("IllegalArgumentException should be thrown");
		}catch(IllegalArgumentException e){}


		//Ensure there are two errors in total
		assertEquals(3, BuiltInErrorProducer.getInstance().testingGetStatsForError(IllegalArgumentException.class).getTotal());
		//Ensure there is one initial error
		assertEquals(1, BuiltInErrorProducer.getInstance().testingGetStatsForError(IllegalArgumentException.class).getInitial());

	}

	@Test public void testInMemoryExceptionCatch(){
		ErrorTestService service = ProxyUtils.createServiceInstance(new ErrorTestServiceImpl(), "foo", "foo", ErrorTestService.class );

		ErrorHandlingConfig config = MoskitoConfigurationHolder.getConfiguration().getErrorHandlingConfig();
		ErrorCatcherConfig[] catchers = new ErrorCatcherConfig[1];
		ErrorCatcherConfig myCatcher = new ErrorCatcherConfig();
		myCatcher.setExceptionClazz(IllegalArgumentException.class.getName());
		myCatcher.setTarget(ErrorCatcherTarget.MEMORY);
		catchers[0] = myCatcher;
		config.setCatchers(catchers);
		config.afterConfiguration();

		BuiltInErrorProducer.getInstance().notifyError(new RuntimeException());
		BuiltInErrorProducer.getInstance().notifyError(new IllegalArgumentException());

		//runtime exception isn't caught.
		assertNull(BuiltInErrorProducer.getInstance().getCatcher(RuntimeException.class));
		//illegal argument exception is caught
		assertNotNull(BuiltInErrorProducer.getInstance().getCatcher(IllegalArgumentException.class));
		assertEquals(1, BuiltInErrorProducer.getInstance().getCatcher(IllegalArgumentException.class).getErrorList().size());

	}

	@Before public void before(){
		BuiltInErrorProducer.getInstance().testingReset();
		MoskitoConfigurationHolder.resetConfiguration();
		MoSKitoContext.get().reset();
	}

	@Test public void testMaxReached(){
		BuiltInErrorProducer.getInstance().notifyError(new IllegalArgumentException());
		BuiltInErrorProducer.getInstance().notifyError(new IllegalArgumentException());
		BuiltInErrorProducer.getInstance().notifyError(new UnsupportedOperationException());

		assertEquals(0, BuiltInErrorProducer.getInstance().testingGetCumulatedStats().getTotal("1m"));
		assertEquals(3, BuiltInErrorProducer.getInstance().testingGetCumulatedStats().getTotal());

		//force interval update
		((IUpdateable) IntervalRegistry.getInstance().getInterval("1m")).update();

		assertEquals(3, BuiltInErrorProducer.getInstance().testingGetCumulatedStats().getTotal("1m"));
		assertEquals(3, BuiltInErrorProducer.getInstance().testingGetCumulatedStats().getTotal());

		assertEquals(3, BuiltInErrorProducer.getInstance().testingGetCumulatedStats().getMaxTotal());

		//force interval update
		((IUpdateable) IntervalRegistry.getInstance().getInterval("1m")).update();

		//this should not change (3) because there were no errors sofar.
		assertEquals(3, BuiltInErrorProducer.getInstance().testingGetCumulatedStats().getMaxTotal());

		//now create 100 more errors
		for (int i=0; i<100; i++){
			BuiltInErrorProducer.getInstance().notifyError(new IllegalArgumentException());
		}
		//force interval update
		((IUpdateable) IntervalRegistry.getInstance().getInterval("1m")).update();
		assertEquals(100, BuiltInErrorProducer.getInstance().testingGetCumulatedStats().getTotal("1m"));
		assertEquals(100, BuiltInErrorProducer.getInstance().testingGetCumulatedStats().getMaxTotal());

	}


}
