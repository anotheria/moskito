package net.anotheria.moskito.webui.mcp;

import com.google.gson.Gson;
import io.modelcontextprotocol.server.McpStatelessServerFeatures;
import io.modelcontextprotocol.spec.McpSchema;
import net.anotheria.anoplass.api.APIException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Definition of an mcp tool backed by a moskito-inspect api. Unless created with {@link #localOnly}, every tool gets
 * an optional {@value #PARAM_TARGET_SYSTEM} parameter: if omitted, the call goes to the local instance, otherwise to
 * the given remote instance, the same way moskito-inspect connects to remote instances.
 *
 * @param name tool name.
 * @param description tool description for the model.
 * @param parameters tool parameters, without the target system.
 * @param kind whether the tool only reads, changes or deletes state.
 * @param targetable whether the tool can be executed against a remote target system.
 * @param handler the implementation.
 * @author lrosenberg
 */
record MoskitoTool(String name, String description, List<ToolParameter> parameters, Kind kind, boolean targetable,
		ToolHandler handler) {

	static final String PARAM_TARGET_SYSTEM = "targetSystem";
	static final String PARAM_INTERVAL = "interval";
	static final String PARAM_UNIT = "unit";

	private static final Logger LOGGER = LoggerFactory.getLogger(MoskitoTool.class);

	private static final ToolParameter TARGET_SYSTEM = ToolParameter.string(PARAM_TARGET_SYSTEM,
			"MoSKito instance to query: '" + TargetSystems.LOCAL + "' (default if omitted), the name of a configured "
					+ "remote or host:port of a remote instance's rmi registry. See list_target_systems.");

	/**
	 * Interval parameter shared by all tools that return interval based stats.
	 */
	static final ToolParameter INTERVAL = ToolParameter.string(PARAM_INTERVAL,
			"Name of the interval the values are taken from, e.g. 1m, 5m, 15m, 1h, 1d or snapshot. Defaults to '"
					+ ToolArguments.DEFAULT_INTERVAL + "' (values since start). See get_interval_infos.");

	/**
	 * Unit parameter shared by all tools that return time values.
	 */
	static final ToolParameter UNIT = ToolParameter.string(PARAM_UNIT,
			"Time unit in which time values are returned. Defaults to MILLISECONDS.")
			.withAllowedValues(List.of("NANOSECONDS", "MICROSECONDS", "MILLISECONDS", "SECONDS"));

	/**
	 * What a tool does to the monitored system, rendered into the mcp tool annotations.
	 */
	enum Kind {
		/**
		 * Only reads data.
		 */
		READ,
		/**
		 * Creates or changes something (e.g. a threshold).
		 */
		WRITE,
		/**
		 * Removes something.
		 */
		DESTRUCTIVE
	}

	MoskitoTool {
		parameters = List.copyOf(parameters);
	}

	static MoskitoTool read(String name, String description, List<ToolParameter> parameters, ToolHandler handler) {
		return new MoskitoTool(name, description, parameters, Kind.READ, true, handler);
	}

	static MoskitoTool write(String name, String description, List<ToolParameter> parameters, ToolHandler handler) {
		return new MoskitoTool(name, description, parameters, Kind.WRITE, true, handler);
	}

	static MoskitoTool destructive(String name, String description, List<ToolParameter> parameters, ToolHandler handler) {
		return new MoskitoTool(name, description, parameters, Kind.DESTRUCTIVE, true, handler);
	}

	static MoskitoTool localOnly(String name, String description, ToolHandler handler) {
		return new MoskitoTool(name, description, List.of(), Kind.READ, false, handler);
	}

	McpStatelessServerFeatures.SyncToolSpecification toSpecification(Gson gson) {
		return new McpStatelessServerFeatures.SyncToolSpecification(toTool(),
				(context, request) -> call(request.arguments(), gson));
	}

	McpSchema.Tool toTool() {
		List<ToolParameter> all = new ArrayList<>(parameters);
		if (targetable)
			all.add(TARGET_SYSTEM);

		Map<String, Object> properties = new LinkedHashMap<>();
		List<String> required = new ArrayList<>();
		for (ToolParameter parameter : all) {
			properties.put(parameter.name(), parameter.toSchema());
			if (parameter.required())
				required.add(parameter.name());
		}

		Map<String, Object> inputSchema = new LinkedHashMap<>();
		inputSchema.put("type", "object");
		inputSchema.put("properties", properties);
		if (!required.isEmpty())
			inputSchema.put("required", required);

		McpSchema.ToolAnnotations annotations = McpSchema.ToolAnnotations.builder()
				.readOnlyHint(kind == Kind.READ)
				.destructiveHint(kind == Kind.DESTRUCTIVE)
				.openWorldHint(false)
				.build();

		return McpSchema.Tool.builder(name, inputSchema)
				.description(description)
				.annotations(annotations)
				.build();
	}

	/**
	 * Executes the tool. Errors are returned as tool errors (and not as protocol errors), so the model sees and can
	 * react to them, e.g. to an unknown producer id or an unreachable remote instance.
	 * @param rawArguments the arguments as sent by the client.
	 * @param gson serializer for the result.
	 * @return the tool result.
	 */
	McpSchema.CallToolResult call(Map<String, Object> rawArguments, Gson gson) {
		ToolArguments arguments = new ToolArguments(rawArguments);
		try {
			Apis apis = new Apis(targetable ? TargetSystems.resolve(arguments.targetSystem()) : null);
			Object result = handler.handle(apis, arguments);
			String text = result instanceof String s ? s : gson.toJson(result);
			return McpSchema.CallToolResult.builder().addTextContent(text).isError(false).build();
		} catch (APIException | IllegalArgumentException e) {
			return error(e.getMessage());
		} catch (RuntimeException e) {
			LOGGER.warn("MCP tool {} failed", name, e);
			return error(e.getClass().getSimpleName() + ": " + e.getMessage());
		}
	}

	private static McpSchema.CallToolResult error(String message) {
		return McpSchema.CallToolResult.builder()
				.addTextContent(message == null ? "Unknown error" : message)
				.isError(true)
				.build();
	}
}
