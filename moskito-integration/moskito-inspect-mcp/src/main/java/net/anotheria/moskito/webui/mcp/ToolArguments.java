package net.anotheria.moskito.webui.mcp;

import net.anotheria.moskito.core.stats.TimeUnit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Typed read access to the arguments of a tool call. Malformed or missing required arguments are reported as
 * {@link IllegalArgumentException}, which ends up as a tool error the model can react to.
 *
 * @param values the raw arguments as sent by the client.
 * @author lrosenberg
 */
record ToolArguments(Map<String, Object> values) {

	/**
	 * Default interval, same as in moskito-inspect.
	 */
	static final String DEFAULT_INTERVAL = "default";

	ToolArguments {
		values = values == null ? Map.of() : values;
	}

	String string(String name) {
		Object value = values.get(name);
		if (value == null)
			return null;
		String asString = value.toString().trim();
		return asString.isEmpty() ? null : asString;
	}

	String string(String name, String defaultValue) {
		String value = string(name);
		return value == null ? defaultValue : value;
	}

	String requiredString(String name) {
		String value = string(name);
		if (value == null)
			throw new IllegalArgumentException("Missing required argument '" + name + "'.");
		return value;
	}

	int integer(String name, int defaultValue) {
		Object value = values.get(name);
		if (value == null)
			return defaultValue;
		if (value instanceof Number number)
			return number.intValue();
		try {
			return Integer.parseInt(value.toString().trim());
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Argument '" + name + "' must be an integer, got '" + value + "'.");
		}
	}

	int requiredInteger(String name) {
		if (values.get(name) == null)
			throw new IllegalArgumentException("Missing required argument '" + name + "'.");
		return integer(name, 0);
	}

	boolean bool(String name, boolean defaultValue) {
		Object value = values.get(name);
		if (value == null)
			return defaultValue;
		if (value instanceof Boolean b)
			return b;
		return Boolean.parseBoolean(value.toString().trim());
	}

	boolean requiredBool(String name) {
		if (values.get(name) == null)
			throw new IllegalArgumentException("Missing required argument '" + name + "'.");
		return bool(name, false);
	}

	/**
	 * Returns a list of strings. Besides a json array, a comma separated string is accepted as well.
	 * @param name name of the argument.
	 * @return the values, empty if the argument is absent.
	 */
	List<String> stringList(String name) {
		Object value = values.get(name);
		if (value == null)
			return List.of();
		Collection<?> raw = value instanceof Collection<?> collection
				? collection
				: Arrays.asList(value.toString().split(","));
		List<String> result = new ArrayList<>(raw.size());
		for (Object element : raw) {
			if (element != null && !element.toString().isBlank())
				result.add(element.toString().trim());
		}
		return result;
	}

	List<String> requiredStringList(String name) {
		List<String> list = stringList(name);
		if (list.isEmpty())
			throw new IllegalArgumentException("Missing required argument '" + name + "'.");
		return list;
	}

	String interval() {
		return string(MoskitoTool.PARAM_INTERVAL, DEFAULT_INTERVAL);
	}

	TimeUnit unit() {
		String unit = string(MoskitoTool.PARAM_UNIT);
		return unit == null ? TimeUnit.MILLISECONDS : TimeUnit.fromString(unit);
	}

	String targetSystem() {
		return string(MoskitoTool.PARAM_TARGET_SYSTEM);
	}
}
