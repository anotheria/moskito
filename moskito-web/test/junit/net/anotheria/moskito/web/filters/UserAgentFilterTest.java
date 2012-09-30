package net.anotheria.moskito.web.filters;

import net.anotheria.anoprise.mocking.MockFactory;
import net.anotheria.anoprise.mocking.Mocking;
import net.anotheria.moskito.core.predefined.FilterStats;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.registry.ProducerRegistryAPIFactory;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.web.TestingUtil;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static org.junit.Assert.assertEquals;

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
		HttpServletRequest req = MockFactory.createMock(HttpServletRequest.class, new GetHeader());
		FilterChain chain = TestingUtil.createFilterChain();
		
		for (int i=0; i<AGENTS.length; i++){
			filter.doFilter(req, null, chain);
		}
		
		List<IStats> stats = new ProducerRegistryAPIFactory().createProducerRegistryAPI().getProducer(filter.getProducerId()).getStats();
		
		assertEquals("expect 3 stat entries ", 3, stats.size());
		assertEquals(AGENTS.length, ((FilterStats)stats.get(0)).getTotalRequests());

		assertEquals(3, ((FilterStats)stats.get(1)).getTotalRequests());
		assertEquals("chrome", ((FilterStats)stats.get(1)).getName());

		assertEquals(2, ((FilterStats)stats.get(2)).getTotalRequests());
		assertEquals("firefox", ((FilterStats)stats.get(2)).getName());
		
	}
	
	public static class GetHeader implements Mocking{
		
		private int last = 0;
		
		public String getHeader(String headerName){
			if (headerName==null || (!headerName.equals("user-agent")))
				throw new IllegalArgumentException("unsupported by mock");
			return AGENTS[last++];
		}
	}

}
