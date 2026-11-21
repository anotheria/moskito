package net.anotheria.moskito.webui.mcp;

import net.anotheria.anoplass.api.APIException;

/**
 * Implementation of a single mcp tool. Handlers only map the arguments onto a moskito-inspect api call, the api itself
 * is obtained from {@link Apis}, which already points to the target system of the call.
 *
 * @author lrosenberg
 */
@FunctionalInterface
interface ToolHandler {

	/**
	 * Executes the tool.
	 * @param apis access to the apis of the target system.
	 * @param arguments the call arguments.
	 * @return the result, a String is returned as is, everything else is serialized to json.
	 * @throws APIException if the api call fails.
	 */
	Object handle(Apis apis, ToolArguments arguments) throws APIException;
}
