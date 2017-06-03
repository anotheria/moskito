package net.anotheria.moskito.core.util;

import net.anotheria.moskito.core.context.MoSKitoContext;
import net.anotheria.moskito.core.dynamic.ProxyUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
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
		ErrorTestSecondService service = ProxyUtils.createServiceInstance(new ErrorTestSecondServiceImpl(), "foo", "foo", ErrorTestSecondService.class );
		try {
			service.nonCatchingEcho();
			fail("IllegalArgumentException should be thrown");
		}catch(IllegalArgumentException e){}

		//there should only be the error from ErrorTestService.

		//Ensure there are two entries, cumulated and IllegalArgumentException.
		assertEquals(2, BuiltInErrorProducer.getInstance().getStats().size());
		//Ensure there is two errors for illegal argument exception.
		assertEquals(2, BuiltInErrorProducer.getInstance().testingGetStatsForError(IllegalArgumentException.class).getTotal());
		//Ensure there is two cumulated error.
		assertEquals(2, BuiltInErrorProducer.getInstance().testingGetCumulatedStats().getTotal());

	}

	@Test public void testInitialAndTotalCountWithCascading(){
		ErrorTestSecondService service = ProxyUtils.createServiceInstance(new ErrorTestSecondServiceImpl(), "foo", "foo", ErrorTestSecondService.class );
		try {
			service.nonCatchingEcho();
			fail("IllegalArgumentException should be thrown");
		}catch(IllegalArgumentException e){}


		//Ensure there are two errors in total
		assertEquals(2, BuiltInErrorProducer.getInstance().testingGetStatsForError(IllegalArgumentException.class).getTotal());
		//Ensure there is one initial error
		assertEquals(1, BuiltInErrorProducer.getInstance().testingGetStatsForError(IllegalArgumentException.class).getInitial());

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

	@Before public void before(){
		BuiltInErrorProducer.getInstance().testingReset();
		MoSKitoContext.get().reset();
	}


}
