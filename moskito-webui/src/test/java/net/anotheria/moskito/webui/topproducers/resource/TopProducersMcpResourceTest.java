package net.anotheria.moskito.webui.topproducers.resource;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.anotheria.moskito.webui.topproducers.api.TopProducersAPI;
import net.anotheria.moskito.webui.topproducers.api.TopProducersAPIImpl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Exercises the json-rpc 2.0 / mcp envelope of the {@link TopProducersMcpResource}. The api is substituted with a
 * direct {@link TopProducersAPIImpl} (bypassing the web-context lookup) so the tool paths can be tested end-to-end
 * against the real core repository. The transport-agnostic {@code process()} method is driven directly, so no jax-rs
 * runtime is needed.
 */
public class TopProducersMcpResourceTest {

	private final TopProducersMcpResource resource = new TopProducersMcpResource() {
		@Override
		protected TopProducersAPI getTopProducersAPI() {
			return new TopProducersAPIImpl();
		}
	};

	private JsonObject call(String body) {
		String response = resource.process(body);
		return JsonParser.parseString(response).getAsJsonObject();
	}

	@Test
	public void initializeReturnsServerInfoAndCapabilities() {
		JsonObject response = call("{\"jsonrpc\":\"2.0\",\"id\":1,\"method\":\"initialize\",\"params\":{\"protocolVersion\":\"2025-06-18\"}}");
		assertEquals("2.0", response.get("jsonrpc").getAsString());
		assertEquals(1, response.get("id").getAsInt());
		JsonObject result = response.getAsJsonObject("result");
		assertEquals("2025-06-18", result.get("protocolVersion").getAsString());
		assertEquals("moskito-top-producers", result.getAsJsonObject("serverInfo").get("name").getAsString());
		assertTrue(result.getAsJsonObject("capabilities").has("tools"));
	}

	@Test
	public void toolsListExposesGetTopProducers() {
		JsonObject response = call("{\"jsonrpc\":\"2.0\",\"id\":2,\"method\":\"tools/list\"}");
		JsonArray tools = response.getAsJsonObject("result").getAsJsonArray("tools");
		assertEquals(1, tools.size());
		JsonObject tool = tools.get(0).getAsJsonObject();
		assertEquals("get_top_producers", tool.get("name").getAsString());
		JsonArray categoryEnum = tool.getAsJsonObject("inputSchema").getAsJsonObject("properties")
				.getAsJsonObject("category").getAsJsonArray("enum");
		assertEquals(5, categoryEnum.size());
	}

	@Test
	public void toolCallForCategoryReturnsJsonContent() {
		JsonObject response = call("{\"jsonrpc\":\"2.0\",\"id\":3,\"method\":\"tools/call\",\"params\":{\"name\":\"get_top_producers\",\"arguments\":{\"category\":\"REQUESTS\",\"limit\":5}}}");
		JsonObject result = response.getAsJsonObject("result");
		assertFalse(result.get("isError").getAsBoolean());
		String text = result.getAsJsonArray("content").get(0).getAsJsonObject().get("text").getAsString();
		//content text must itself be valid json (an array of producers, empty in a plain jvm).
		assertTrue(JsonParser.parseString(text).isJsonArray());
	}

	@Test
	public void toolCallWithoutCategoryReturnsAllCategories() {
		JsonObject response = call("{\"jsonrpc\":\"2.0\",\"id\":4,\"method\":\"tools/call\",\"params\":{\"name\":\"get_top_producers\",\"arguments\":{}}}");
		JsonObject result = response.getAsJsonObject("result");
		assertFalse(result.get("isError").getAsBoolean());
		String text = result.getAsJsonArray("content").get(0).getAsJsonObject().get("text").getAsString();
		assertEquals(5, JsonParser.parseString(text).getAsJsonArray().size());
	}

	@Test
	public void toolCallWithUnknownCategoryIsToolError() {
		JsonObject response = call("{\"jsonrpc\":\"2.0\",\"id\":5,\"method\":\"tools/call\",\"params\":{\"name\":\"get_top_producers\",\"arguments\":{\"category\":\"nope\"}}}");
		assertTrue(response.getAsJsonObject("result").get("isError").getAsBoolean());
	}

	@Test
	public void unknownToolIsProtocolError() {
		JsonObject response = call("{\"jsonrpc\":\"2.0\",\"id\":6,\"method\":\"tools/call\",\"params\":{\"name\":\"bogus\"}}");
		assertEquals(-32602, response.getAsJsonObject("error").get("code").getAsInt());
	}

	@Test
	public void unknownMethodReturnsMethodNotFound() {
		JsonObject response = call("{\"jsonrpc\":\"2.0\",\"id\":7,\"method\":\"does/not/exist\"}");
		assertEquals(-32601, response.getAsJsonObject("error").get("code").getAsInt());
	}

	@Test
	public void parseErrorIsReported() {
		JsonObject response = call("this is not json");
		assertEquals(-32700, response.getAsJsonObject("error").get("code").getAsInt());
	}

	@Test
	public void notificationIsNotAnswered() {
		//no id -> notification, unknown notifications produce no response body (the transport turns this into a 202).
		assertNull(resource.process("{\"jsonrpc\":\"2.0\",\"method\":\"notifications/initialized\"}"));
	}

	@Test
	public void pingIsAnswered() {
		JsonObject response = call("{\"jsonrpc\":\"2.0\",\"id\":8,\"method\":\"ping\"}");
		assertTrue(response.has("result"));
	}
}
