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

import java.io.ByteArrayInputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Drives the servlet through the mcp sdk transport with mocked servlet objects: initialize, tools/list and tools/call.
 */
class MoskitoMcpServletTest {

	private MoskitoMcpServlet servlet;

	@BeforeEach
	void setUp() throws Exception {
		System.setProperty("JUNITTEST", "true");
		ServletConfig config = mock(ServletConfig.class);
		when(config.getServletContext()).thenReturn(mock(ServletContext.class));
		servlet = new MoskitoMcpServlet();
		servlet.init(config);
	}

	@AfterEach
	void tearDown() {
		servlet.destroy();
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

		StringWriter out = new StringWriter();
		HttpServletResponse response = mock(HttpServletResponse.class);
		when(response.getWriter()).thenReturn(new PrintWriter(out));

		servlet.service(request, response);
		return out.toString();
	}
}
