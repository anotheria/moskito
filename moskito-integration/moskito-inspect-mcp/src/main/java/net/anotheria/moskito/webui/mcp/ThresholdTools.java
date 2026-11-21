package net.anotheria.moskito.webui.mcp;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.moskito.webui.threshold.api.ThresholdAPI;
import net.anotheria.moskito.webui.threshold.api.ThresholdPO;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.function.UnaryOperator;

import static net.anotheria.moskito.webui.mcp.MoskitoTool.INTERVAL;
import static net.anotheria.moskito.webui.mcp.MoskitoTool.UNIT;
import static net.anotheria.moskito.webui.mcp.MoskitoTool.destructive;
import static net.anotheria.moskito.webui.mcp.MoskitoTool.read;
import static net.anotheria.moskito.webui.mcp.MoskitoTool.write;
import static net.anotheria.moskito.webui.mcp.ToolParameter.string;
import static net.anotheria.moskito.webui.mcp.ToolParameter.stringArray;

/**
 * Tools backed by {@link ThresholdAPI}.
 *
 * @author lrosenberg
 */
final class ThresholdTools {

	private static final ToolParameter THRESHOLD_ID = string("thresholdId", "Id of the threshold.").asRequired();

	/**
	 * Threshold colors in the order they are configured in moskito-inspect.
	 */
	private static final List<String> COLORS = List.of("green", "yellow", "orange", "red", "purple");

	private ThresholdTools() {
	}

	static List<MoskitoTool> tools() {
		return List.of(
				read("get_thresholds",
						"Returns the current status (GREEN, YELLOW, ORANGE, RED, PURPLE) and value of thresholds.",
						List.of(stringArray("names", "Only return the thresholds with these names.")),
						ThresholdTools::getStatuses),
				read("get_worst_threshold_status",
						"Returns the worst status of all thresholds or of the given ones.",
						List.of(stringArray("names", "Only consider the thresholds with these names.")),
						ThresholdTools::getWorstStatus),
				read("get_threshold_alerts",
						"Returns the recent threshold alerts (status changes).",
						List.of(),
						(apis, args) -> apis.get(ThresholdAPI.class).getAlerts()),
				read("get_threshold_definitions",
						"Returns the definitions of all thresholds (producer, stat, value, interval, guards).",
						List.of(),
						(apis, args) -> apis.get(ThresholdAPI.class).getThresholdDefinitions()),
				read("get_threshold_definition",
						"Returns the definition of a single threshold.",
						List.of(THRESHOLD_ID),
						(apis, args) -> apis.get(ThresholdAPI.class)
								.getThresholdDefinition(args.requiredString("thresholdId"))),
				read("get_threshold_guards",
						"Returns the guards (status conditions) of a threshold.",
						List.of(THRESHOLD_ID),
						(apis, args) -> apis.get(ThresholdAPI.class)
								.getGuardsForThreshold(args.requiredString("thresholdId"))),
				read("get_thresholds_for_producer",
						"Returns the ids of the thresholds tied to a producer.",
						List.of(string("producerId", "Id of the producer.").asRequired()),
						(apis, args) -> apis.get(ThresholdAPI.class)
								.getThresholdIdsTiedToASpecificProducer(args.requiredString("producerId"))),
				write("create_threshold",
						"Creates a threshold on a producer value. Each color guard sets the status to that color "
								+ "if the value is ABOVE or BELOW the guard value.",
						createParameters(),
						ThresholdTools::create),
				write("update_threshold",
						"Changes the name and the guards of a threshold. Each color guard sets the status to that "
								+ "color if the value is ABOVE or BELOW the guard value.",
						updateParameters(),
						ThresholdTools::update),
				destructive("remove_threshold",
						"Removes a threshold.",
						List.of(THRESHOLD_ID),
						ThresholdTools::remove)
		);
	}

	private static Object getStatuses(Apis apis, ToolArguments args) throws APIException {
		List<String> names = args.stringList("names");
		ThresholdAPI api = apis.get(ThresholdAPI.class);
		return names.isEmpty() ? api.getThresholdStatuses() : api.getThresholdStatuses(names.toArray(new String[0]));
	}

	private static Object getWorstStatus(Apis apis, ToolArguments args) throws APIException {
		List<String> names = args.stringList("names");
		ThresholdAPI api = apis.get(ThresholdAPI.class);
		return names.isEmpty() ? api.getWorstStatus() : api.getWorstStatus(names);
	}

	private static Object create(Apis apis, ToolArguments args) throws APIException {
		ThresholdPO po = new ThresholdPO();
		po.setName(args.requiredString("name"));
		po.setProducerId(args.requiredString("producerId"));
		po.setStatName(args.requiredString("statName"));
		po.setValueName(args.requiredString("valueName"));
		po.setInterval(args.interval());
		po.setUnit(args.unit().name());
		//create expects the directions as above/below.
		setGuards(po, args, direction -> direction.toLowerCase(Locale.ROOT));
		apis.get(ThresholdAPI.class).createThreshold(po);
		return "Threshold " + po.getName() + " created.";
	}

	private static Object update(Apis apis, ToolArguments args) throws APIException {
		String thresholdId = args.requiredString("thresholdId");
		ThresholdPO po = new ThresholdPO();
		po.setName(args.requiredString("name"));
		//update expects the directions as GuardedDirection names.
		setGuards(po, args, direction -> direction.equals("ABOVE") ? "UP" : "DOWN");
		apis.get(ThresholdAPI.class).updateThreshold(thresholdId, po);
		return "Threshold " + thresholdId + " updated.";
	}

	private static Object remove(Apis apis, ToolArguments args) throws APIException {
		String thresholdId = args.requiredString("thresholdId");
		apis.get(ThresholdAPI.class).removeThreshold(thresholdId);
		return "Threshold " + thresholdId + " removed.";
	}

	private static void setGuards(ThresholdPO po, ToolArguments args, UnaryOperator<String> mapper) {
		po.setGreenDir(direction(args, "green", mapper));
		po.setGreenValue(args.requiredString("greenValue"));
		po.setYellowDir(direction(args, "yellow", mapper));
		po.setYellowValue(args.requiredString("yellowValue"));
		po.setOrangeDir(direction(args, "orange", mapper));
		po.setOrangeValue(args.requiredString("orangeValue"));
		po.setRedDir(direction(args, "red", mapper));
		po.setRedValue(args.requiredString("redValue"));
		po.setPurpleDir(direction(args, "purple", mapper));
		po.setPurpleValue(args.requiredString("purpleValue"));
	}

	private static String direction(ToolArguments args, String color, UnaryOperator<String> mapper) {
		String name = color + "Dir";
		String direction = args.requiredString(name).toUpperCase(Locale.ROOT);
		if (!direction.equals("ABOVE") && !direction.equals("BELOW"))
			throw new IllegalArgumentException("Argument '" + name + "' must be ABOVE or BELOW, got '" + direction + "'.");
		return mapper.apply(direction);
	}

	private static List<ToolParameter> guardParameters() {
		List<ToolParameter> parameters = new ArrayList<>();
		for (String color : COLORS) {
			parameters.add(string(color + "Dir", "Whether the status becomes " + color.toUpperCase(Locale.ROOT)
					+ " if the value is ABOVE or BELOW " + color + "Value.")
					.withAllowedValues(List.of("ABOVE", "BELOW")).asRequired());
			parameters.add(string(color + "Value", "Guard value for " + color.toUpperCase(Locale.ROOT) + ".")
					.asRequired());
		}
		return parameters;
	}

	private static List<ToolParameter> createParameters() {
		List<ToolParameter> parameters = new ArrayList<>();
		parameters.add(string("name", "Name of the threshold.").asRequired());
		parameters.add(string("producerId", "Id of the producer.").asRequired());
		parameters.add(string("statName", "Name of the stat (e.g. a method name or cumulated).").asRequired());
		parameters.add(string("valueName", "Name of the value (e.g. TR, TT, Avg, ERR).").asRequired());
		parameters.add(INTERVAL);
		parameters.add(UNIT);
		parameters.addAll(guardParameters());
		return parameters;
	}

	private static List<ToolParameter> updateParameters() {
		List<ToolParameter> parameters = new ArrayList<>();
		parameters.add(THRESHOLD_ID);
		parameters.add(string("name", "New name of the threshold.").asRequired());
		parameters.addAll(guardParameters());
		return parameters;
	}
}
