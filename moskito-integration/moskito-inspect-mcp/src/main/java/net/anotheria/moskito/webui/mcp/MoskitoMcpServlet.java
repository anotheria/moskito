package net.anotheria.moskito.webui.mcp;

import com.google.gson.Gson;
import io.modelcontextprotocol.server.McpServer;
import io.modelcontextprotocol.server.McpStatelessSyncServer;
import io.modelcontextprotocol.server.transport.HttpServletStatelessServerTransport;
import io.modelcontextprotocol.spec.McpSchema;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.anotheria.moskito.webui.shared.api.MoskitoAPIInitializer;
import net.anotheria.moskito.webui.util.VersionUtil;
import net.anotheria.util.maven.MavenVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Model Context Protocol (MCP) endpoint of moskito-inspect, using the stateless streamable-http transport of the mcp
 * java sdk. It exposes the moskito-inspect apis as tools; each tool accepts an optional targetSystem argument to run
 * against a remote moskito instance instead of the local one. Being stateless fits this well: the target system is
 * chosen per call, there is no session state to keep.
 * <p>
 * The endpoint is only enabled if a key is configured (environment variable {@value McpKey#ENV_VARIABLE} or system
 * property {@value McpKey#SYSTEM_PROPERTY}); clients have to send it as {@code Authorization: Bearer <key>}.
 *
 * @author lrosenberg
 */
public class MoskitoMcpServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Name of the init parameter with the path the servlet is mapped to.
	 */
	public static final String PARAM_ENDPOINT = "endpoint";

	/**
	 * Default path of the mcp endpoint.
	 */
	public static final String DEFAULT_ENDPOINT = "/moskito-inspect-mcp";

	private static final Logger LOGGER = LoggerFactory.getLogger(MoskitoMcpServlet.class);

	private static final String INSTRUCTIONS = "Tools to inspect a java application monitored by MoSKito: producers "
			+ "and their stats, thresholds, accumulators, gauges, dashboards, journeys, tracers, threads and more. "
			+ "All tools run against the local instance unless a targetSystem is given, see list_target_systems. "
			+ "Start with get_top_producers or get_thresholds to find problems, then drill down with get_producer.";

	private transient HttpServletStatelessServerTransport transport;

	private transient McpStatelessSyncServer server;

	/**
	 * The key clients have to present, null if none is configured and the endpoint is disabled.
	 */
	private transient McpKey key;

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		key = McpKey.fromEnvironment();
		if (key == null) {
			LOGGER.warn("MCP endpoint is disabled because no key is configured. Set the environment variable {} or the "
					+ "system property {} to enable it.", McpKey.ENV_VARIABLE, McpKey.SYSTEM_PROPERTY);
			return;
		}
		MoskitoAPIInitializer.initialize();

		String endpoint = config.getInitParameter(PARAM_ENDPOINT);
		transport = HttpServletStatelessServerTransport.builder()
				.messageEndpoint(endpoint == null || endpoint.isBlank() ? DEFAULT_ENDPOINT : endpoint)
				.build();

		Gson gson = ToolResultGson.create();
		server = McpServer.sync(transport)
				.serverInfo("moskito-inspect", version(config))
				.instructions(INSTRUCTIONS)
				.capabilities(McpSchema.ServerCapabilities.builder().tools(false).build())
				//execute the tools on the request thread, as the web ui does.
				.immediateExecution(true)
				.tools(MoskitoTools.all().stream().map(tool -> tool.toSpecification(gson)).toList())
				.build();
		transport.init(config);
	}

	private static String version(ServletConfig config) {
		try {
			MavenVersion version = VersionUtil.getWebappLibVersion(config.getServletContext(), "moskito-webui");
			if (version != null && version.getVersion() != null)
				return version.getVersion();
		} catch (RuntimeException ignored) {
			//the version is informational only.
		}
		return "unknown";
	}

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		if (key == null) {
			res.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE, "MCP endpoint is disabled, no key configured.");
			return;
		}
		if (!key.matches(req.getHeader("Authorization"))) {
			res.setHeader("WWW-Authenticate", "Bearer");
			res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing or invalid MCP key.");
			return;
		}
		transport.service(req, res);
	}

	@Override
	public void destroy() {
		if (server != null)
			server.close();
		super.destroy();
	}
}
