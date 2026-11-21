package net.anotheria.moskito.webui.mcp;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.moskito.webui.journey.api.JourneyAPI;

import java.util.List;

import static net.anotheria.moskito.webui.mcp.MoskitoTool.UNIT;
import static net.anotheria.moskito.webui.mcp.MoskitoTool.destructive;
import static net.anotheria.moskito.webui.mcp.MoskitoTool.read;
import static net.anotheria.moskito.webui.mcp.ToolParameter.bool;
import static net.anotheria.moskito.webui.mcp.ToolParameter.integer;
import static net.anotheria.moskito.webui.mcp.ToolParameter.string;

/**
 * Tools backed by {@link JourneyAPI}. A journey is a recorded sequence of calls (e.g. a user session), each call
 * containing the tree of producer calls it made.
 *
 * @author lrosenberg
 */
final class JourneyTools {

	private static final ToolParameter JOURNEY_NAME = string("journeyName", "Name of the journey.").asRequired();

	private JourneyTools() {
	}

	static List<MoskitoTool> tools() {
		return List.of(
				read("get_journeys",
						"Returns all recorded journeys.",
						List.of(),
						(apis, args) -> apis.get(JourneyAPI.class).getJourneys()),
				read("get_journey",
						"Returns a journey with the list of its recorded calls.",
						List.of(JOURNEY_NAME, UNIT),
						(apis, args) -> apis.get(JourneyAPI.class).getJourney(args.requiredString("journeyName"), args.unit())),
				read("get_journey_call",
						"Returns a single recorded call of a journey with the full tree of producer calls it made, "
								+ "selected by position or by name.",
						List.of(JOURNEY_NAME,
								integer("callPosition", "Position of the call in the journey (0 based)."),
								string("callName", "Name of the call, used if no position is given."),
								UNIT),
						JourneyTools::getCall),
				read("analyze_journey",
						"Aggregates all calls of a journey by producer, category and subsystem (or by method), to "
								+ "find where the time is spent.",
						List.of(JOURNEY_NAME, bool("byMethod", "Aggregate by producer method instead of producer.")),
						JourneyTools::analyze),
				destructive("delete_journey",
						"Deletes a recorded journey.",
						List.of(JOURNEY_NAME),
						JourneyTools::delete)
		);
	}

	private static Object getCall(Apis apis, ToolArguments args) throws APIException {
		String journeyName = args.requiredString("journeyName");
		JourneyAPI api = apis.get(JourneyAPI.class);
		if (args.values().get("callPosition") != null)
			return api.getTracedCall(journeyName, args.integer("callPosition", 0), args.unit());
		String callName = args.string("callName");
		if (callName != null)
			return api.getTracedCallByName(journeyName, callName, args.unit());
		throw new IllegalArgumentException("Either callPosition or callName is required.");
	}

	private static Object analyze(Apis apis, ToolArguments args) throws APIException {
		String journeyName = args.requiredString("journeyName");
		JourneyAPI api = apis.get(JourneyAPI.class);
		return args.bool("byMethod", false) ? api.analyzeJourneyByMethod(journeyName) : api.analyzeJourney(journeyName);
	}

	private static Object delete(Apis apis, ToolArguments args) throws APIException {
		String journeyName = args.requiredString("journeyName");
		apis.get(JourneyAPI.class).deleteJourney(journeyName);
		return "Journey " + journeyName + " deleted.";
	}
}
