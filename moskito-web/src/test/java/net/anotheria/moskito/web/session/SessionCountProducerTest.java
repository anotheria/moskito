package net.anotheria.moskito.web.session;

import net.anotheria.moskito.core.util.session.SessionCountStats;
import org.junit.Test;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import java.util.concurrent.CountDownLatch;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

public class SessionCountProducerTest {
	
	private static final int THREADS = 5, EVENTS_PER_THREAD = 10000;
	
	@Test public void testSessionProducer() throws Exception{
		final SessionCountProducer producer = new SessionCountProducer();
		
		final CountDownLatch prepare = new CountDownLatch(THREADS);
		final CountDownLatch start = new CountDownLatch(1);
		final CountDownLatch finish = new CountDownLatch(THREADS);
		
		final HttpSession session = mock(HttpSession.class);
		
		Thread[] workers = new Thread[THREADS];
		for (int i=0; i<THREADS; i++){
			workers[i] = new Thread(){
				public void run(){
					try{
						prepare.countDown();
						start.await();
						for (int i=0; i<EVENTS_PER_THREAD; i++){
							producer.sessionCreated(new HttpSessionEvent(session));
						}
						finish.countDown();
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}
			};
			workers[i].start();
		}
		prepare.await();
		start.countDown();
		finish.await();
		
		SessionCountStats stats = producer.getStats().get(0);
		assertEquals(EVENTS_PER_THREAD*THREADS, stats.getCurrentSessionCount(null));
		assertEquals(EVENTS_PER_THREAD*THREADS, stats.getCreatedSessionCount(null));
		assertEquals(EVENTS_PER_THREAD*THREADS, stats.getMaxSessionCount(null));

		final CountDownLatch prepare2 = new CountDownLatch(THREADS);
		final CountDownLatch start2 = new CountDownLatch(1);
		final CountDownLatch finish2 = new CountDownLatch(THREADS);
		
		for (int i=0; i<THREADS; i++){
			workers[i] = new Thread(){
				public void run(){
					try{
						prepare2.countDown();
						start2.await();
						for (int i=0; i<EVENTS_PER_THREAD; i++){
							producer.sessionDestroyed(new HttpSessionEvent(session));
						}
						finish2.countDown();
					}catch(InterruptedException e){
						e.printStackTrace();
					}
				}
			};
			workers[i].start();
		}
		prepare2.await();
		start2.countDown();
		finish2.await();
		
		assertEquals(0, stats.getCurrentSessionCount(null));
		assertEquals(EVENTS_PER_THREAD*THREADS, stats.getMaxSessionCount(null));
		assertEquals(EVENTS_PER_THREAD*THREADS, stats.getCreatedSessionCount(null));
		assertEquals(EVENTS_PER_THREAD*THREADS, stats.getDestroyedSessionCount(null));


}
}
