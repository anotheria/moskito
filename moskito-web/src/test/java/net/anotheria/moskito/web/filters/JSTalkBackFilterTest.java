package net.anotheria.moskito.web.filters;

import net.anotheria.anoprise.mocking.MockFactory;
import net.anotheria.anoprise.mocking.Mocking;
import net.anotheria.moskito.core.predefined.JSStats;
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

/**
 * Class description!!!!!! Change this!
 *
 * @author Illya Bogatyrchuk
 */
public class JSTalkBackFilterTest {
	static{
		System.setProperty("JUNITTEST", "true");
	}

	@Before
	public void cleanup(){
		ProducerRegistryFactory.getProducerRegistryInstance().cleanup();
	}

	@Test
	public void basicTestForMethodCall() throws Exception{
		JSTalkBackFilter filter = new JSTalkBackFilter();

		filter.init(TestingUtil.createFilterConfig());
		HttpServletRequest req = MockFactory.createMock(HttpServletRequest.class, new HttpServletRequestMock());
		FilterChain chain = TestingUtil.createFilterChain();

		filter.doFilter(req, null, chain);

		List<IStats> stats = new ProducerRegistryAPIFactory().createProducerRegistryAPI().getProducer(filter.getProducerId()).getStats();

		assertEquals("expect 2 stat entries ", 2, stats.size());
		final JSStats jsStats = (JSStats) stats.get(1);
		assertEquals("testurl.com", jsStats.getUrl());
//		assertEquals(1400, jsStats.getDomLoadTime("default"));
//		assertEquals(2400, jsStats.getWindowLoadTime("default"));
	}

	public static class HttpServletRequestMock implements Mocking {
		public String getParameter(String param){
			if(param.equals("url"))
				return "testurl.com";
			if(param.equals("domLoadTime"))
				return "1400";

			if(param.equals("windowLoadTime"))
				return "2400";

			throw new AssertionError();
		}
	}

}
