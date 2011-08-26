package net.java.dev.moskito.web.filters;

import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;

import net.anotheria.anoprise.mocking.MockFactory;
import net.anotheria.anoprise.mocking.Mocking;
import net.java.dev.moskito.core.predefined.FilterStats;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.registry.ProducerRegistryAPIFactory;
import net.java.dev.moskito.core.registry.ProducerRegistryFactory;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MethodFilterTest {
	
	static{
		System.setProperty("JUNITTEST", "true");
	}
	
	public static final String[] METHODS = {"GET", "GET", "GET", "POST", "GET"};
	
	@Before public void cleanup(){
		ProducerRegistryFactory.getProducerRegistryInstance().cleanup();
	}
	
	@Test public void basicTestForMethodCall() throws Exception {
		MethodFilter filter = new MethodFilter();
		
		filter.init(TestingUtil.createFilterConfig());
		HttpServletRequest req = MockFactory.createMock(HttpServletRequest.class, new GetMethod());
		FilterChain chain = TestingUtil.createFilterChain();
		
		for (int i=0; i<METHODS.length; i++){
			filter.doFilter(req, null, chain);
		}
		
		List<IStats> stats = new ProducerRegistryAPIFactory().createProducerRegistryAPI().getProducer(filter.getProducerId()).getStats();
		
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
