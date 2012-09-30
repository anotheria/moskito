package net.anotheria.moskito.web.filters;

import net.anotheria.anoprise.mocking.MockFactory;
import net.anotheria.anoprise.mocking.Mocking;
import net.anotheria.moskito.core.predefined.FilterStats;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.IProducerRegistryAPI;
import net.anotheria.moskito.core.registry.ProducerRegistryAPIFactory;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.web.TestingUtil;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MethodFilterTest {
	
	static{
		System.setProperty("JUNITTEST", "true");
	}
	
	public static final String[] METHODS = {"GET", "GET", "GET", "POST", "GET"};
	
	@Before public void cleanup(){
		ProducerRegistryFactory.getProducerRegistryInstance().cleanup();
	}
	
	@Test public void testForNull() throws Exception{
		MethodFilter filter = new MethodFilter();
		filter.init(TestingUtil.createFilterConfig());
		
		FilterChain chain = TestingUtil.createFilterChain();
		
		for (int i=0; i<METHODS.length; i++){
			filter.doFilter(null, null, chain);
		}
		
		IProducerRegistryAPI api = new ProducerRegistryAPIFactory().createProducerRegistryAPI();
		IStatsProducer producer = api.getProducer(filter.getProducerId());
		List<IStats> stats = producer.getStats();
		
		assertEquals("expect 2 stat entries ", 2, stats.size());
		assertEquals(METHODS.length, ((FilterStats)stats.get(0)).getTotalRequests());
		assertEquals(METHODS.length, ((FilterStats)stats.get(1)).getTotalRequests());

		assertEquals("nonhttp", ((FilterStats)stats.get(1)).getName());
		
	}
	
	@Test public void basicTestForMethodCall() throws Exception {
		MethodFilter filter = new MethodFilter();
		
		filter.init(TestingUtil.createFilterConfig());
		HttpServletRequest req = MockFactory.createMock(HttpServletRequest.class, new GetMethod());
		FilterChain chain = TestingUtil.createFilterChain();
		
		for (int i=0; i<METHODS.length; i++){
			filter.doFilter(req, null, chain);
		}
		
		IProducerRegistryAPI api = new ProducerRegistryAPIFactory().createProducerRegistryAPI();
		IStatsProducer producer = api.getProducer(filter.getProducerId());
		List<IStats> stats = producer.getStats();
		
		assertEquals("expect 3 stat entries ", 3, stats.size());
		assertEquals(METHODS.length, ((FilterStats)stats.get(0)).getTotalRequests());

		assertEquals(4, ((FilterStats)stats.get(1)).getTotalRequests());
		assertEquals("GET", ((FilterStats)stats.get(1)).getName());

		assertEquals(1, ((FilterStats)stats.get(2)).getTotalRequests());
		assertEquals("POST", ((FilterStats)stats.get(2)).getName());
	}
	
	public static class GetMethod implements Mocking{
		
		private int last = 0;
		
		public String getMethod(){
			return METHODS[last++];
		}
	}
}
