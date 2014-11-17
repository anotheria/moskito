package net.anotheria.moskito.web.filters;

import net.anotheria.anoprise.mocking.MockFactory;
import net.anotheria.anoprise.mocking.Mocking;
import net.anotheria.moskito.core.predefined.PageInBrowserStats;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.registry.ProducerRegistryAPIFactory;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.web.TestingUtil;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

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

	@Test
	public void basicTestForMethodCall() throws Exception {
		final String intervalName = "default";
		final String url = "http://www.moskito.org/";

		JSTalkBackFilter filter = new JSTalkBackFilter();
		filter.init(TestingUtil.createFilterConfig());

		HttpServletResponse response = callFilter(filter, "JSTalkBackFilter", "", 0, 0);
		assertEquals(0, response.getStatus());
		assertNull(response.getContentType());

		response = callFilter(filter, "JSTalkBackFilter2", url, 1000, 3000);
		assertEquals(HttpServletResponse.SC_NO_CONTENT, response.getStatus());
		assertEquals("image/gif", response.getContentType());

		response = callFilter(filter, "JSTalkBackFilter2", url, 2000, 6000);
		assertEquals(HttpServletResponse.SC_NO_CONTENT, response.getStatus());
		assertEquals("image/gif", response.getContentType());

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
		HttpServletRequest req = MockFactory.createMock(HttpServletRequest.class, createMockedHttpServletRequest(producerId, url, domLoadTime, windowLoadTime));
		HttpServletResponse res = MockFactory.createMock(HttpServletResponse.class, new HttpServletResponseMock());
		FilterChain chain = TestingUtil.createFilterChain();
		filter.doFilter(req, res, chain);
		return res;
	}

	/**
	 * Creates mocked instance of HttpServletRequest.
	 *
	 * @param producerId     id of the producer
	 * @param url            page url
	 * @param domLoadTime    DOM load time
	 * @param windowLoadTime page load time
	 * @return {@link HttpServletRequestMock}
	 */
	private HttpServletRequestMock createMockedHttpServletRequest(final String producerId, final String url, final long domLoadTime, final long windowLoadTime) {
		final HttpServletRequestMock mocked = new HttpServletRequestMock();
		mocked.addParameter("producerId", producerId);
		mocked.addParameter("url", url);
		mocked.addParameter("domLoadTime", String.valueOf(domLoadTime));
		mocked.addParameter("windowLoadTime", String.valueOf(windowLoadTime));
		return mocked;
	}

	/**
	 * {@link HttpServletRequest} mocking implementation.
	 */
	public static class HttpServletRequestMock implements Mocking {
		/**
		 * Request parameters container.
		 */
		private Map<String, String> paramMap = new HashMap<String, String>();

		/**
		 * Returns parameter value by key.
		 *
		 * @param key parameter name
		 * @return parameter value
		 */
		public String getParameter(final String key) {
			return key != null ? paramMap.get(key) : null;
		}

		/**
		 * Adds parameter key and value to the container.
		 *
		 * @param key   parameter name
		 * @param value parameter value
		 */
		public void addParameter(final String key, final String value) {
			if (key != null)
				this.paramMap.put(key, value);
		}
	}

	/**
	 * {@link HttpServletResponse} mocking implementation.
	 */
	public static class HttpServletResponseMock implements Mocking {
		/**
		 * Response status numeric representation.
		 */
		private int status;
		/**
		 * Response content type
		 */
		private String contentType;

		public void setStatus(final int status) {
			this.status = status;
		}

		public int getStatus() {
			return status;
		}

		public void setContentType(final String contentType) {
			this.contentType = contentType;
		}

		public String getContentType() {
			return contentType;
		}

		/**
		 * Returns instance of {@link PrintWriter}                 .
		 *
		 * @return {@link PrintWriter}
		 */
		public PrintWriter getWriter() {
			return new PrintWriter(new ByteArrayOutputStream());
		}
	}
}
