package net.java.dev.moskito.web.filters;

import static org.junit.Assert.assertEquals;

import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;

import net.anotheria.anoprise.mocking.MockFactory;
import net.anotheria.anoprise.mocking.Mocking;
import net.java.dev.moskito.core.predefined.FilterStats;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.registry.ProducerRegistryAPIFactory;
import net.java.dev.moskito.core.registry.ProducerRegistryFactory;
import net.java.dev.moskito.web.filters.MethodFilterTest.GetMethod;

import org.junit.Before;
import org.junit.Test;

public class DomainFilterTest {
	static{
		System.setProperty("JUNITTEST", "true");
	}
	
	public static final String[] DOMAINS = {"www.example.com", "www.example.com", "www.example.com", "www.google.com", "www.example.com"};
	
	@Before public void cleanup(){
		ProducerRegistryFactory.getProducerRegistryInstance().cleanup();
	}
	
	@Test public void basicTestForMethodCall() throws Exception {
		DomainFilter filter = new DomainFilter();
		
		filter.init(TestingUtil.createFilterConfig());
		HttpServletRequest req = MockFactory.createMock(HttpServletRequest.class, new GetDomain());
		FilterChain chain = TestingUtil.createFilterChain();
		
		for (int i=0; i<DOMAINS.length; i++){
			filter.doFilter(req, null, chain);
		}
		
		List<IStats> stats = new ProducerRegistryAPIFactory().createProducerRegistryAPI().getProducer(filter.getProducerId()).getStats();
		
		assertEquals("expect 3 stat entries ", 3, stats.size());
		assertEquals(DOMAINS.length, ((FilterStats)stats.get(0)).getTotalRequests());

		assertEquals(4, ((FilterStats)stats.get(1)).getTotalRequests());
		assertEquals("www.example.com", ((FilterStats)stats.get(1)).getName());

		assertEquals(1, ((FilterStats)stats.get(2)).getTotalRequests());
		assertEquals("www.google.com", ((FilterStats)stats.get(2)).getName());
		
	}
	
	public static class GetDomain implements Mocking{
		
		private int last = 0;
		
		public String getServerName(){
			return DOMAINS[last++];
		}
	}

}
