package net.anotheria.moskito.web.filters;

import net.anotheria.moskito.core.predefined.PageInBrowserStats;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.registry.ProducerRegistryAPIFactory;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.web.TestingUtil;
import org.junit.Before;
import org.junit.Test;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link JSTalkBackFilter}.
 *
 * @author Illya Bogatyrchuk
 */
public class JSTalkBackFilterTest {
	static {
		System.setProperty("JUNITTEST", "true");
	}

	@Before
	public void cleanup() {
		ProducerRegistryFactory.getProducerRegistryInstance().cleanup();
	}

	@Test public void testEmptyURL() throws Exception{
		JSTalkBackFilter filter = createFilter();
		HttpServletResponse res = mock(HttpServletResponse.class);
		filter.doFilter(mockHttpServletRequest("", 0, 0),
				res, mock(FilterChain.class)
		);

		verify(res, never()).setStatus(HttpServletResponse.SC_NO_CONTENT);

	}

	private JSTalkBackFilter createFilter() throws ServletException{
		JSTalkBackFilter filter = new JSTalkBackFilter();
		FilterConfig config = mock(FilterConfig.class);
		filter.init(config);
		return filter;
	}

	private HttpServletRequest mockHttpServletRequest(final String url, final long domLoadTime, final long windowLoadTime) {
		HttpServletRequest req = mock(HttpServletRequest.class);
		when(req.getParameter("url")).thenReturn(url);
		when(req.getParameter("domLoadTime")).thenReturn(String.valueOf(domLoadTime));
		when(req.getParameter("windowLoadTime")).thenReturn(String.valueOf(windowLoadTime));
		return req;
	}


	@Test
	public void basicTestForMethodCall() throws Exception {
		final String intervalName = "default";
		final String url = "http://www.moskito.org/";

		JSTalkBackFilter filter = new JSTalkBackFilter();
		filter.init(TestingUtil.createFilterConfig());

		HttpServletResponse response;

		response = callFilter(filter, "JSTalkBackFilter2", url, 1000, 3000);
		response = callFilter(filter, "JSTalkBackFilter2", url, 2000, 6000);

		List<IStats> stats = new ProducerRegistryAPIFactory().createProducerRegistryAPI().getProducer("JSTalkBackFilter2").getStats();

		assertEquals("Expected predefined producer and producer with name: " + url, 2, stats.size());
		assertEquals("cumulated", stats.get(0).getName());

		final PageInBrowserStats pageInBrowserStats = (PageInBrowserStats) stats.get(1);
		assertEquals(url, pageInBrowserStats.getName());
		assertEquals(2, pageInBrowserStats.getNumberOfLoads());

		// check milliseconds
		assertEquals(1000, pageInBrowserStats.getDomMinLoadTime(intervalName, TimeUnit.MILLISECONDS));
		assertEquals(2000, pageInBrowserStats.getDomMaxLoadTime(intervalName, TimeUnit.MILLISECONDS));
		assertEquals(1500.0, pageInBrowserStats.getAverageDOMLoadTime(intervalName, TimeUnit.MILLISECONDS), 0.0);
		assertEquals(2000, pageInBrowserStats.getDomLastLoadTime(intervalName, TimeUnit.MILLISECONDS));
		assertEquals(3000, pageInBrowserStats.getWindowMinLoadTime(intervalName, TimeUnit.MILLISECONDS));
		assertEquals(6000, pageInBrowserStats.getWindowMaxLoadTime(intervalName, TimeUnit.MILLISECONDS));
		assertEquals(4500.0, pageInBrowserStats.getAverageWindowLoadTime(intervalName, TimeUnit.MILLISECONDS), 0.0);
		assertEquals(6000, pageInBrowserStats.getWindowLastLoadTime(intervalName, TimeUnit.MILLISECONDS));
		assertEquals(3000, pageInBrowserStats.getTotalDomLoadTime(intervalName, TimeUnit.MILLISECONDS));
		assertEquals(9000, pageInBrowserStats.getTotalWindowLoadTime(intervalName, TimeUnit.MILLISECONDS));

		// check seconds
		TimeUnit timeUnit = TimeUnit.SECONDS;
		assertEquals(timeUnit.transformMillis(1000), pageInBrowserStats.getDomMinLoadTime(intervalName, timeUnit));
		assertEquals(timeUnit.transformMillis(2000), pageInBrowserStats.getDomMaxLoadTime(intervalName, timeUnit));
		assertEquals(timeUnit.transformMillis(1500.0), pageInBrowserStats.getAverageDOMLoadTime(intervalName, timeUnit), 0.0);
		assertEquals(timeUnit.transformMillis(2000), pageInBrowserStats.getDomLastLoadTime(intervalName, timeUnit));
		assertEquals(timeUnit.transformMillis(3000), pageInBrowserStats.getWindowMinLoadTime(intervalName, timeUnit));
		assertEquals(timeUnit.transformMillis(6000), pageInBrowserStats.getWindowMaxLoadTime(intervalName, timeUnit));
		assertEquals(timeUnit.transformMillis(4500.0), pageInBrowserStats.getAverageWindowLoadTime(intervalName, timeUnit), 0.0);
		assertEquals(timeUnit.transformMillis(6000), pageInBrowserStats.getWindowLastLoadTime(intervalName, timeUnit));
		assertEquals(timeUnit.transformMillis(3000), pageInBrowserStats.getTotalDomLoadTime(intervalName, timeUnit));
		assertEquals(timeUnit.transformMillis(9000), pageInBrowserStats.getTotalWindowLoadTime(intervalName, timeUnit));

		// check microseconds
		timeUnit = TimeUnit.MICROSECONDS;
		assertEquals(timeUnit.transformMillis(1000), pageInBrowserStats.getDomMinLoadTime(intervalName, timeUnit));
		assertEquals(timeUnit.transformMillis(2000), pageInBrowserStats.getDomMaxLoadTime(intervalName, timeUnit));
		assertEquals(timeUnit.transformMillis(1500.0), pageInBrowserStats.getAverageDOMLoadTime(intervalName, timeUnit), 0.0);
		assertEquals(timeUnit.transformMillis(2000), pageInBrowserStats.getDomLastLoadTime(intervalName, timeUnit));
		assertEquals(timeUnit.transformMillis(3000), pageInBrowserStats.getWindowMinLoadTime(intervalName, timeUnit));
		assertEquals(timeUnit.transformMillis(6000), pageInBrowserStats.getWindowMaxLoadTime(intervalName, timeUnit));
		assertEquals(timeUnit.transformMillis(4500.0), pageInBrowserStats.getAverageWindowLoadTime(intervalName, timeUnit), 0.0);
		assertEquals(timeUnit.transformMillis(6000), pageInBrowserStats.getWindowLastLoadTime(intervalName, timeUnit));
		assertEquals(timeUnit.transformMillis(3000), pageInBrowserStats.getTotalDomLoadTime(intervalName, timeUnit));
		assertEquals(timeUnit.transformMillis(9000), pageInBrowserStats.getTotalWindowLoadTime(intervalName, timeUnit));

		// check microseconds
		timeUnit = TimeUnit.NANOSECONDS;
		assertEquals(timeUnit.transformMillis(1000), pageInBrowserStats.getDomMinLoadTime(intervalName, timeUnit));
		assertEquals(timeUnit.transformMillis(2000), pageInBrowserStats.getDomMaxLoadTime(intervalName, timeUnit));
		assertEquals(timeUnit.transformMillis(1500.0), pageInBrowserStats.getAverageDOMLoadTime(intervalName, timeUnit), 0.0);
		assertEquals(timeUnit.transformMillis(2000), pageInBrowserStats.getDomLastLoadTime(intervalName, timeUnit));
		assertEquals(timeUnit.transformMillis(3000), pageInBrowserStats.getWindowMinLoadTime(intervalName, timeUnit));
		assertEquals(timeUnit.transformMillis(6000), pageInBrowserStats.getWindowMaxLoadTime(intervalName, timeUnit));
		assertEquals(timeUnit.transformMillis(4500.0), pageInBrowserStats.getAverageWindowLoadTime(intervalName, timeUnit), 0.0);
		assertEquals(timeUnit.transformMillis(6000), pageInBrowserStats.getWindowLastLoadTime(intervalName, timeUnit));
		assertEquals(timeUnit.transformMillis(3000), pageInBrowserStats.getTotalDomLoadTime(intervalName, timeUnit));
		assertEquals(timeUnit.transformMillis(9000), pageInBrowserStats.getTotalWindowLoadTime(intervalName, timeUnit));
	}

	/**
	 * @param filter         {@link JSTalkBackFilter}
	 * @param url            page url
	 * @param domLoadTime    DOM load time
	 * @param windowLoadTime page load time
	 * @return {@link HttpServletResponse}
	 * @throws IOException      on filter errors
	 * @throws ServletException on filter errors
	 */
	private HttpServletResponse callFilter(final JSTalkBackFilter filter, final String producerId, final String url, final long domLoadTime, final long windowLoadTime) throws IOException, ServletException {
		HttpServletRequest req = mock(HttpServletRequest.class);
		when(req.getParameter("producerId")).thenReturn(producerId);
		when(req.getParameter("url")).thenReturn(url);
		when(req.getParameter("domLoadTime")).thenReturn(String.valueOf(domLoadTime));
		when(req.getParameter("windowLoadTime")).thenReturn(String.valueOf(windowLoadTime));

		HttpServletResponse res = mock(HttpServletResponse.class);//MockFactory.createMock(HttpServletResponse.class, new HttpServletResponseMock());
		when(res.getWriter()).thenReturn(mock(PrintWriter.class));
		
		FilterChain chain = TestingUtil.createFilterChain();
		filter.doFilter(req, res, chain);

		//migrated checks here
		verify(res, times(1)).setStatus(HttpServletResponse.SC_NO_CONTENT);
		verify(res, times(1)).setContentType("image/gif");
		

		return res;
	}
}
