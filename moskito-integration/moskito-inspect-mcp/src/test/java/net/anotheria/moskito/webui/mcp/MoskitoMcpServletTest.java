package net.anotheria.moskito.webui.mcp;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Drives the servlet through the mcp sdk transport with mocked servlet objects: key check, initialize, tools/list and
 * tools/call.
 */
class MoskitoMcpServletTest {

	private static final String KEY = "test-key";

	private MoskitoMcpServlet servlet;

	@BeforeEach
	void setUp() throws Exception {
		System.setProperty("JUNITTEST", "true");
		System.setProperty(McpKey.SYSTEM_PROPERTY, KEY);
		servlet = createServlet();
	}

	@AfterEach
	void tearDown() {
		servlet.destroy();
		System.clearProperty(McpKey.SYSTEM_PROPERTY);
	}

	private static MoskitoMcpServlet createServlet() throws Exception {
		ServletConfig config = mock(ServletConfig.class);
		when(config.getServletContext()).thenReturn(mock(ServletContext.class));
		MoskitoMcpServlet servlet = new MoskitoMcpServlet();
		servlet.init(config);
		return servlet;
	}

	@Test
	void requestsWithoutValidKeyAreRejected() throws Exception {
		String body = "{\"jsonrpc\":\"2.0\",\"id\":1,\"method\":\"tools/list\"}";
		assertEquals(HttpServletResponse.SC_UNAUTHORIZED, statusOf(servlet, body, null));
		assertEquals(HttpServletResponse.SC_UNAUTHORIZED, statusOf(servlet, body, "Bearer wrong-key"));
		assertEquals(HttpServletResponse.SC_UNAUTHORIZED, statusOf(servlet, body, KEY));
	}

	@Test
	void endpointIsDisabledWithoutConfiguredKey() throws Exception {
		System.clearProperty(McpKey.SYSTEM_PROPERTY);
		//the environment variable may be set on the build machine, then this test can't verify the disabled state.
		assumeTrue(System.getenv(McpKey.ENV_VARIABLE) == null);
		MoskitoMcpServlet disabled = createServlet();
		try {
			assertEquals(HttpServletResponse.SC_SERVICE_UNAVAILABLE,
					statusOf(disabled, "{\"jsonrpc\":\"2.0\",\"id\":1,\"method\":\"tools/list\"}", "Bearer " + KEY));
		} finally {
			disabled.destroy();
		}
	}

	@Test
	void initialize() throws Exception {
		String response = post("{\"jsonrpc\":\"2.0\",\"id\":1,\"method\":\"initialize\",\"params\":{"
				+ "\"protocolVersion\":\"2025-06-18\",\"capabilities\":{},"
				+ "\"clientInfo\":{\"name\":\"test\",\"version\":\"1\"}}}");
		assertTrue(response.contains("\"moskito-inspect\""), response);
		assertTrue(response.contains("\"tools\""), response);
	}

	@Test
	void listTools() throws Exception {
		String response = post("{\"jsonrpc\":\"2.0\",\"id\":2,\"method\":\"tools/list\"}");
		assertTrue(response.contains("\"get_producers\""), response);
		assertTrue(response.contains("\"targetSystem\""), response);
	}

	@Test
	void callTool() throws Exception {
		String response = post("{\"jsonrpc\":\"2.0\",\"id\":3,\"method\":\"tools/call\",\"params\":{"
				+ "\"name\":\"list_target_systems\",\"arguments\":{}}}");
		assertTrue(response.contains("\"isError\":false"), response);
		assertTrue(response.contains("local"), response);
	}

	private String post(String body) throws Exception {
		StringWriter out = new StringWriter();
		HttpServletResponse response = mock(HttpServletResponse.class);
		when(response.getWriter()).thenReturn(new PrintWriter(out));
		servlet.service(request(body, "Bearer " + KEY), response);
		return out.toString();
	}

	private static int statusOf(MoskitoMcpServlet target, String body, String authorization) throws Exception {
		HttpServletResponse response = mock(HttpServletResponse.class);
		when(response.getWriter()).thenReturn(new PrintWriter(new StringWriter()));
		target.service(request(body, authorization), response);
		ArgumentCaptor<Integer> status = ArgumentCaptor.forClass(Integer.class);
		verify(response).sendError(status.capture(), anyString());
		return status.getValue();
	}

	private static HttpServletRequest request(String body, String authorization) throws Exception {
		byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
		ByteArrayInputStream input = new ByteArrayInputStream(bytes);
		ServletInputStream servletInput = new ServletInputStream() {
			@Override
			public boolean isFinished() {
				return input.available() == 0;
			}

			@Override
			public boolean isReady() {
				return true;
			}

			@Override
			public void setReadListener(ReadListener readListener) {
			}

			@Override
			public int read() {
				return input.read();
			}

			@Override
			public int read(byte[] b, int off, int len) {
				return input.read(b, off, len);
			}
		};

		HttpServletRequest request = mock(HttpServletRequest.class);
		when(request.getMethod()).thenReturn("POST");
		when(request.getRequestURI()).thenReturn(MoskitoMcpServlet.DEFAULT_ENDPOINT);
		when(request.getContentLengthLong()).thenReturn((long) bytes.length);
		when(request.getHeader("Accept")).thenReturn("application/json, text/event-stream");
		when(request.getHeaderNames()).thenReturn(Collections.enumeration(List.of("Accept")));
		when(request.getHeaders("Accept")).thenReturn(Collections.enumeration(List.of("application/json, text/event-stream")));
		when(request.getInputStream()).thenReturn(servletInput);
		when(request.getCharacterEncoding()).thenReturn("UTF-8");
		when(request.getHeader("Authorization")).thenReturn(authorization);
		return request;
	}
}
