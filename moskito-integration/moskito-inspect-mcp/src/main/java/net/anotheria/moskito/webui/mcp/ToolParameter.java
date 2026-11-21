package net.anotheria.moskito.webui.mcp;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * A single input parameter of a {@link MoskitoTool}, rendered into the json schema of the tool.
 *
 * @param name name of the parameter.
 * @param type json schema type (string, integer, boolean, array).
 * @param description description for the model.
 * @param required whether the parameter must be present.
 * @param allowedValues allowed values, rendered as enum; empty if unrestricted.
 * @author lrosenberg
 */
record ToolParameter(String name, String type, String description, boolean required, List<String> allowedValues) {

	ToolParameter {
		allowedValues = List.copyOf(allowedValues);
	}

	static ToolParameter string(String name, String description) {
		return new ToolParameter(name, "string", description, false, List.of());
	}

	static ToolParameter integer(String name, String description) {
		return new ToolParameter(name, "integer", description, false, List.of());
	}

	static ToolParameter bool(String name, String description) {
		return new ToolParameter(name, "boolean", description, false, List.of());
	}

	/**
	 * An array of strings parameter.
	 */
	static ToolParameter stringArray(String name, String description) {
		return new ToolParameter(name, "array", description, false, List.of());
	}

	ToolParameter asRequired() {
		return new ToolParameter(name, type, description, true, allowedValues);
	}

	ToolParameter withAllowedValues(List<String> values) {
		return new ToolParameter(name, type, description, required, values);
	}

	Map<String, Object> toSchema() {
		Map<String, Object> schema = new LinkedHashMap<>();
		schema.put("type", type);
		schema.put("description", description);
		if ("array".equals(type))
			schema.put("items", Map.of("type", "string"));
		if (!allowedValues.isEmpty())
			schema.put("enum", allowedValues);
		return schema;
	}
}
