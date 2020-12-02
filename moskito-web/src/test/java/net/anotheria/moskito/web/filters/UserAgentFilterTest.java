package net.anotheria.moskito.web.filters;

import net.anotheria.moskito.core.predefined.FilterStats;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.registry.ProducerRegistryAPIFactory;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.web.TestingUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserAgentFilterTest {
	static{
		System.setProperty("JUNITTEST", "true");
	}
	
	public static final String[] AGENTS = {"chrome", "chrome", "firefox", "firefox", "chrome"};
	
	@Before public void cleanup(){
		ProducerRegistryFactory.getProducerRegistryInstance().cleanup();
	}
	
	@Test public void basicTestForMethodCall() throws Exception {
		UserAgentFilter filter = new UserAgentFilter();
		
		filter.init(TestingUtil.createFilterConfig());
		FilterChain chain = TestingUtil.createFilterChain();

		HttpServletRequest req = mock(HttpServletRequest.class);
		when(req.getHeader(Mockito.anyString())).thenAnswer(new Answer() {
			private int last = 0;

			public Object answer(InvocationOnMock invocation){
				return AGENTS[last++];
			}
		});

		for (int i=0; i<AGENTS.length; i++){
			filter.doFilter(req, null, chain);
		}
		
		List<IStats> stats = new ProducerRegistryAPIFactory().createProducerRegistryAPI().getProducer(filter.getProducerId()).getStats();
		
		assertEquals("expect 3 stat entries ", 3, stats.size());
		assertEquals(AGENTS.length, ((FilterStats)stats.get(0)).getTotalRequests());

		assertEquals(3, ((FilterStats)stats.get(1)).getTotalRequests());
		assertEquals("chrome", stats.get(1).getName());

		assertEquals(2, ((FilterStats)stats.get(2)).getTotalRequests());
		assertEquals("firefox", stats.get(2).getName());
		
	}
}
