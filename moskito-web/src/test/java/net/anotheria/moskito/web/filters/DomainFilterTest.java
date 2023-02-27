package net.anotheria.moskito.web.filters;

import net.anotheria.moskito.core.predefined.FilterStats;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.registry.ProducerRegistryAPIFactory;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.web.TestingUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
		HttpServletRequest req = mock(HttpServletRequest.class);//, new GetDomain());
		when(req.getServerName()).thenAnswer(new Answer() {
			private int last = 0;

			public Object answer(InvocationOnMock invocation){
				return DOMAINS[last++];
			}
		});

		FilterChain chain = mock(FilterChain.class);
		HttpServletResponse response = mock(HttpServletResponse.class);


		for (int i=0; i<DOMAINS.length; i++){
			filter.doFilter(req, response, chain);
		}
		
		List<IStats> stats = new ProducerRegistryAPIFactory().createProducerRegistryAPI().getProducer(filter.getProducerId()).getStats();
		
		assertEquals("expect 3 stat entries ", 3, stats.size());
		assertEquals(DOMAINS.length, ((FilterStats)stats.get(0)).getTotalRequests());

		assertEquals(4, ((FilterStats)stats.get(1)).getTotalRequests());
		assertEquals("www.example.com", stats.get(1).getName());

		assertEquals(1, ((FilterStats)stats.get(2)).getTotalRequests());
		assertEquals("www.google.com", stats.get(2).getName());

		verify(chain, times(DOMAINS.length)).doFilter(req, response);
		
	}
}
