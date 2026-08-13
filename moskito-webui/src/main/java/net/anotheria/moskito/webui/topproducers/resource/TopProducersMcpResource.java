package net.anotheria.moskito.webui.topproducers.resource;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.anotheria.anoplass.api.APIException;
import net.anotheria.moskito.webui.shared.resource.AbstractResource;
import net.anotheria.moskito.webui.topproducers.api.CategoryTopProducersAO;
import net.anotheria.moskito.webui.topproducers.api.TopProducerAO;
import net.anotheria.moskito.webui.topproducers.api.TopProducersAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;

/**
 * Model Context Protocol (MCP) endpoint over the streamable-http transport. It exposes the top-producers ranking as an
 * mcp tool so an llm can retrieve the heaviest producers as optimization targets. This resource is intentionally thin:
 * it only speaks the json-rpc 2.0 / mcp envelope and delegates all business logic to {@link TopProducersAPI}, so a
 * future stdio transport can reuse the very same api.
 *
 * @author lrosenberg
 */
@Path("mcp")
public class TopProducersMcpResource extends AbstractResource {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TopProducersMcpResource.class);

	/**
	 * Default mcp protocol version this server speaks. The client's requested version is echoed back if present.
	 */
	private static final String DEFAULT_PROTOCOL_VERSION = "2025-06-18";
	/**
	 * Name of the tool exposing the ranking.
	 */
	private static final String TOOL_NAME = "get_top_producers";
	/**
	 * Default number of producers returned per category.
	 */
	private static final int DEFAULT_LIMIT = 10;

	private static final Gson GSON = new Gson();

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response handle(String requestBody) {
		String responseBody = process(requestBody);
		//notifications and empty batches produce no response body, only an acknowledgement.
		return responseBody == null
				? Response.status(Response.Status.ACCEPTED).build()
				: Response.ok(responseBody, MediaType.APPLICATION_JSON).build();
	}

	/**
	 * Processes a json-rpc request body and returns the json response as a string, or {@code null} if there is no
	 * response to send (a notification or a batch consisting only of notifications). This holds the whole protocol
	 * logic, kept transport-agnostic so it can be reused (e.g. by a future stdio transport) and unit tested.
	 * @param requestBody the raw json-rpc request body.
	 * @return the json response, or null if nothing is to be answered.
	 */
	String process(String requestBody) {
		final JsonElement parsed;
		try {
			parsed = JsonParser.parseString(requestBody);
		} catch (RuntimeException e) {
			return errorResponse(null, -32700, "Parse error: " + e.getMessage()).toString();
		}

		//json-rpc supports batches (arrays of requests).
		if (parsed.isJsonArray()) {
			JsonArray responses = new JsonArray();
			for (JsonElement element : parsed.getAsJsonArray()) {
				JsonObject response = handleSingle(element);
				if (response != null)
					responses.add(response);
			}
			//a batch consisting only of notifications gets no response body.
			return responses.size() == 0 ? null : responses.toString();
		}

		JsonObject response = handleSingle(parsed);
		//notifications (no id) are not answered.
		return response == null ? null : response.toString();
	}

	/**
	 * Handles a single json-rpc request/notification. Returns the response object, or null for notifications which
	 * must not be answered.
	 */
	private JsonObject handleSingle(JsonElement element) {
		if (element == null || !element.isJsonObject())
			return errorResponse(null, -32600, "Invalid Request");

		JsonObject request = element.getAsJsonObject();
		JsonElement id = request.get("id");
		String method = optString(request, "method", null);

		if (method == null)
			return errorResponse(id, -32600, "Invalid Request: missing method");

		//notifications carry no id and are not answered.
		boolean isNotification = id == null || id.isJsonNull();

		switch (method) {
			case "initialize":
				return resultResponse(id, initializeResult(request));
			case "tools/list":
				return resultResponse(id, toolsListResult());
			case "tools/call":
				return handleToolCall(id, request);
			case "ping":
				return resultResponse(id, new JsonObject());
			default:
				if (isNotification) {
					//unknown notification (e.g. notifications/initialized) - just acknowledge with no body.
					return null;
				}
				return errorResponse(id, -32601, "Method not found: " + method);
		}
	}

	private JsonObject initializeResult(JsonObject request) {
		String protocolVersion = DEFAULT_PROTOCOL_VERSION;
		JsonObject params = optObject(request, "params");
		if (params != null && params.has("protocolVersion"))
			protocolVersion = params.get("protocolVersion").getAsString();

		JsonObject capabilities = new JsonObject();
		capabilities.add("tools", new JsonObject());

		JsonObject serverInfo = new JsonObject();
		serverInfo.addProperty("name", "moskito-top-producers");
		serverInfo.addProperty("version", "1.0.0");

		JsonObject result = new JsonObject();
		result.addProperty("protocolVersion", protocolVersion);
		result.add("capabilities", capabilities);
		result.add("serverInfo", serverInfo);
		return result;
	}

	private JsonObject toolsListResult() {
		JsonArray categoryEnum = new JsonArray();
		try {
			for (String category : getTopProducersAPI().getCategories())
				categoryEnum.add(category);
		} catch (APIException e) {
			LOGGER.warn("Could not load ranking categories", e);
		}

		JsonObject categoryProperty = new JsonObject();
		categoryProperty.addProperty("type", "string");
		categoryProperty.add("enum", categoryEnum);
		categoryProperty.addProperty("description",
				"Ranking category to return. If omitted, the top producers of every category are returned.");

		JsonObject limitProperty = new JsonObject();
		limitProperty.addProperty("type", "integer");
		limitProperty.addProperty("description", "Maximum number of producers to return per category.");
		limitProperty.addProperty("default", DEFAULT_LIMIT);

		JsonObject properties = new JsonObject();
		properties.add("category", categoryProperty);
		properties.add("limit", limitProperty);

		JsonObject inputSchema = new JsonObject();
		inputSchema.addProperty("type", "object");
		inputSchema.add("properties", properties);

		JsonObject tool = new JsonObject();
		tool.addProperty("name", TOOL_NAME);
		tool.addProperty("description",
				"Returns the producers that consume the most resources (requests, total time, errors, error rate, "
						+ "max concurrent requests), ranked over time, so they can be used as optimization targets.");
		tool.add("inputSchema", inputSchema);

		JsonArray tools = new JsonArray();
		tools.add(tool);

		JsonObject result = new JsonObject();
		result.add("tools", tools);
		return result;
	}

	private JsonObject handleToolCall(JsonElement id, JsonObject request) {
		JsonObject params = optObject(request, "params");
		String toolName = params == null ? null : optString(params, "name", null);
		if (!TOOL_NAME.equals(toolName))
			return errorResponse(id, -32602, "Unknown tool: " + toolName);

		JsonObject arguments = params == null ? null : optObject(params, "arguments");
		String category = arguments == null ? null : optString(arguments, "category", null);
		int limit = arguments != null && arguments.has("limit") && arguments.get("limit").isJsonPrimitive()
				? arguments.get("limit").getAsInt() : DEFAULT_LIMIT;

		try {
			TopProducersAPI api = getTopProducersAPI();
			String payload;
			if (category == null || category.trim().isEmpty()) {
				List<CategoryTopProducersAO> all = api.getTopProducersByAllCategories(limit);
				payload = GSON.toJson(all);
			} else {
				List<TopProducerAO> producers = api.getTopProducers(category, limit);
				payload = GSON.toJson(producers);
			}
			return resultResponse(id, toolContent(payload, false));
		} catch (APIException e) {
			//domain errors (e.g. unknown category) are reported to the model as a tool error, not a protocol error.
			return resultResponse(id, toolContent(e.getMessage(), true));
		} catch (RuntimeException e) {
			LOGGER.warn("Top producers tool call failed", e);
			return errorResponse(id, -32603, "Internal error: " + e.getMessage());
		}
	}

	private JsonObject toolContent(String text, boolean isError) {
		JsonObject content = new JsonObject();
		content.addProperty("type", "text");
		content.addProperty("text", text);

		JsonArray contents = new JsonArray();
		contents.add(content);

		JsonObject result = new JsonObject();
		result.add("content", contents);
		result.addProperty("isError", isError);
		return result;
	}

	private JsonObject resultResponse(JsonElement id, JsonObject result) {
		JsonObject response = new JsonObject();
		response.addProperty("jsonrpc", "2.0");
		response.add("id", id);
		response.add("result", result);
		return response;
	}

	private JsonObject errorResponse(JsonElement id, int code, String message) {
		JsonObject error = new JsonObject();
		error.addProperty("code", code);
		error.addProperty("message", message);

		JsonObject response = new JsonObject();
		response.addProperty("jsonrpc", "2.0");
		response.add("id", id);
		response.add("error", error);
		return response;
	}

	private static String optString(JsonObject object, String property, String defaultValue) {
		JsonElement element = object.get(property);
		return element != null && element.isJsonPrimitive() ? element.getAsString() : defaultValue;
	}

	private static JsonObject optObject(JsonObject object, String property) {
		JsonElement element = object.get(property);
		return element != null && element.isJsonObject() ? element.getAsJsonObject() : null;
	}
}
