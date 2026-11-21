package net.anotheria.moskito.webui.mcp;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.moskito.webui.tracers.api.TracerAPI;

import java.util.List;

import static net.anotheria.moskito.webui.mcp.MoskitoTool.UNIT;
import static net.anotheria.moskito.webui.mcp.MoskitoTool.destructive;
import static net.anotheria.moskito.webui.mcp.MoskitoTool.read;
import static net.anotheria.moskito.webui.mcp.MoskitoTool.write;
import static net.anotheria.moskito.webui.mcp.ToolParameter.bool;
import static net.anotheria.moskito.webui.mcp.ToolParameter.string;

/**
 * Tools backed by {@link TracerAPI}. A tracer records the stack traces of the calls to a producer method, to find out
 * where the calls come from.
 *
 * @author lrosenberg
 */
final class TracerTools {

	private static final ToolParameter TRACER_ID = string("tracerId", "Id of the tracer.").asRequired();

	private TracerTools() {
	}

	static List<MoskitoTool> tools() {
		return List.of(
				read("get_tracers",
						"Returns all tracers.",
						List.of(),
						(apis, args) -> apis.get(TracerAPI.class).getTracers()),
				read("get_tracer",
						"Returns a single tracer.",
						List.of(TRACER_ID),
						(apis, args) -> apis.get(TracerAPI.class).getTracer(args.requiredString("tracerId"))),
				read("get_traces",
						"Returns the traces (stack traces with duration) recorded by a tracer.",
						List.of(TRACER_ID, UNIT),
						(apis, args) -> apis.get(TracerAPI.class).getTraces(args.requiredString("tracerId"), args.unit())),
				write("create_tracer",
						"Creates a tracer for a producer stat (method).",
						List.of(string("producerId", "Id of the producer.").asRequired(),
								string("statName", "Name of the stat (method) to trace.").asRequired()),
						TracerTools::create),
				write("set_tracer_enabled",
						"Enables or disables a tracer.",
						List.of(TRACER_ID, bool("enabled", "True to enable, false to disable.").asRequired()),
						TracerTools::setEnabled),
				destructive("remove_tracer",
						"Removes a tracer and its recorded traces.",
						List.of(TRACER_ID),
						TracerTools::remove)
		);
	}

	private static Object create(Apis apis, ToolArguments args) throws APIException {
		String producerId = args.requiredString("producerId");
		String statName = args.requiredString("statName");
		apis.get(TracerAPI.class).createTracer(producerId, statName);
		return "Tracer for " + producerId + '.' + statName + " created.";
	}

	private static Object setEnabled(Apis apis, ToolArguments args) throws APIException {
		String tracerId = args.requiredString("tracerId");
		boolean enabled = args.requiredBool("enabled");
		if (enabled)
			apis.get(TracerAPI.class).enableTracer(tracerId);
		else
			apis.get(TracerAPI.class).disableTracer(tracerId);
		return "Tracer " + tracerId + (enabled ? " enabled." : " disabled.");
	}

	private static Object remove(Apis apis, ToolArguments args) throws APIException {
		String tracerId = args.requiredString("tracerId");
		apis.get(TracerAPI.class).removeTracer(tracerId);
		return "Tracer " + tracerId + " removed.";
	}
}
