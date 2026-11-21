package net.anotheria.moskito.webui.mcp;

import com.google.gson.Gson;
import io.modelcontextprotocol.spec.McpSchema;
import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.moskito.core.predefined.ServiceStats;
import net.anotheria.moskito.core.predefined.ServiceStatsFactory;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.webui.shared.api.MoskitoAPIInitializer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Checks the tool definitions and executes every read tool that needs no arguments against the local instance. The
 * latter mainly guards against api objects gson can't serialize.
 */
class MoskitoToolsTest {

	private static final Gson GSON = ToolResultGson.create();

	@BeforeAll
	static void setUp() throws Exception {
		System.setProperty("JUNITTEST", "true");
		APIFinder.cleanUp();
		MoskitoAPIInitializer.initialize();

		OnDemandStatsProducer<ServiceStats> producer = new OnDemandStatsProducer<>("McpTestProducer", "test", "sub",
				ServiceStatsFactory.DEFAULT_INSTANCE);
		ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(producer);
		ServiceStats stats = producer.getStats("doSomething");
		stats.addRequest();
		stats.addExecutionTime(100);
		stats.notifyRequestFinished();
	}

	@Test
	void toolNamesAreUniqueAndTargetable() {
		Set<String> names = new HashSet<>();
		for (MoskitoTool tool : MoskitoTools.all()) {
			assertTrue(names.add(tool.name()), "duplicate tool " + tool.name());
			Map<?, ?> properties = (Map<?, ?>) tool.toTool().inputSchema().get("properties");
			assertEquals(tool.targetable(), properties.containsKey(MoskitoTool.PARAM_TARGET_SYSTEM), tool.name());
		}
		assertTrue(names.contains("list_target_systems"));
		assertTrue(names.contains("get_top_producers"));
	}

	@Test
	void annotationsReflectKind() {
		for (MoskitoTool tool : MoskitoTools.all()) {
			McpSchema.ToolAnnotations annotations = tool.toTool().annotations();
			assertEquals(tool.kind() == MoskitoTool.Kind.READ, annotations.readOnlyHint(), tool.name());
			assertEquals(tool.kind() == MoskitoTool.Kind.DESTRUCTIVE, annotations.destructiveHint(), tool.name());
		}
	}

	@Test
	void readToolsWithoutRequiredArgumentsSucceedLocally() {
		for (MoskitoTool tool : MoskitoTools.all()) {
			if (tool.kind() != MoskitoTool.Kind.READ || tool.parameters().stream().anyMatch(ToolParameter::required))
				continue;
			//get_accumulator requires one of two optional arguments.
			if (tool.name().equals("get_accumulator") || tool.name().equals("get_journey_call"))
				continue;
			McpSchema.CallToolResult result = tool.call(Map.of(), GSON);
			assertFalse(result.isError(), tool.name() + " failed: " + text(result));
		}
	}

	@Test
	void producerToolsReturnTheProducer() {
		MoskitoTool getProducer = find("get_producer");
		McpSchema.CallToolResult result = getProducer.call(Map.of("producerId", "McpTestProducer"), GSON);
		assertFalse(result.isError(), text(result));
		assertTrue(text(result).contains("doSomething"), text(result));

		McpSchema.CallToolResult byCategory = find("get_producers").call(Map.of("category", "test"), GSON);
		assertFalse(byCategory.isError(), text(byCategory));
		assertTrue(text(byCategory).contains("McpTestProducer"));

		McpSchema.CallToolResult value = find("get_producer_value").call(Map.of("producerId", "McpTestProducer",
				"statName", "doSomething", "valueName", "TR"), GSON);
		assertFalse(value.isError(), text(value));
		assertEquals("1", text(value));
	}

	@Test
	void missingArgumentsAreToolErrors() {
		McpSchema.CallToolResult result = find("get_producer").call(Map.of(), GSON);
		assertTrue(result.isError());
		assertTrue(text(result).contains("producerId"));
	}

	@Test
	void conflictingFiltersAreRejected() {
		McpSchema.CallToolResult result = find("get_producers").call(Map.of("category", "a", "subsystem", "b"), GSON);
		assertTrue(result.isError());
	}

	@Test
	void unknownTargetSystemIsToolError() {
		McpSchema.CallToolResult result = find("get_producer_categories").call(Map.of("targetSystem", "nonsense"), GSON);
		assertTrue(result.isError());
		assertTrue(text(result).contains("list_target_systems"), text(result));
	}

	@Test
	void stringListAcceptsArraysAndCommaSeparatedStrings() {
		assertEquals(List.of("a", "b"), new ToolArguments(Map.of("ids", List.of("a", " b "))).stringList("ids"));
		assertEquals(List.of("a", "b"), new ToolArguments(Map.of("ids", "a, b")).stringList("ids"));
		assertEquals(List.of(), new ToolArguments(null).stringList("ids"));
	}

	private static MoskitoTool find(String name) {
		return MoskitoTools.all().stream().filter(tool -> tool.name().equals(name)).findFirst().orElseThrow();
	}

	private static String text(McpSchema.CallToolResult result) {
		return ((McpSchema.TextContent) result.content().get(0)).text();
	}
}
