package net.anotheria.moskito.webui.mcp;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.moskito.webui.accumulators.api.AccumulatorAPI;
import net.anotheria.moskito.webui.accumulators.api.AccumulatorPO;

import java.util.List;
import java.util.Locale;

import static net.anotheria.moskito.webui.mcp.MoskitoTool.INTERVAL;
import static net.anotheria.moskito.webui.mcp.MoskitoTool.UNIT;
import static net.anotheria.moskito.webui.mcp.MoskitoTool.destructive;
import static net.anotheria.moskito.webui.mcp.MoskitoTool.read;
import static net.anotheria.moskito.webui.mcp.MoskitoTool.write;
import static net.anotheria.moskito.webui.mcp.ToolParameter.string;
import static net.anotheria.moskito.webui.mcp.ToolParameter.stringArray;

/**
 * Tools backed by {@link AccumulatorAPI}. Accumulators record the history of a producer value over time.
 *
 * @author lrosenberg
 */
final class AccumulatorTools {

	private static final ToolParameter ACCUMULATOR_ID = string("accumulatorId", "Id of the accumulator.").asRequired();

	private AccumulatorTools() {
	}

	static List<MoskitoTool> tools() {
		return List.of(
				read("get_accumulators",
						"Returns the definitions of all accumulators (producer, stat, value, interval).",
						List.of(),
						(apis, args) -> apis.get(AccumulatorAPI.class).getAccumulatorDefinitions()),
				read("get_accumulator",
						"Returns the recorded values of an accumulator, selected by id or by name.",
						List.of(string("accumulatorId", "Id of the accumulator."),
								string("name", "Name of the accumulator, used if no id is given.")),
						AccumulatorTools::getAccumulator),
				read("get_accumulator_charts",
						"Returns chart data for one or more accumulators. Mode SINGLE returns one chart per "
								+ "accumulator, COMBINED one chart with all accumulators, NORMALIZED one chart with all "
								+ "accumulators scaled to the same range (to compare values of different magnitude).",
						List.of(stringArray("accumulatorIds", "Ids of the accumulators.").asRequired(),
								string("mode", "Chart mode, defaults to SINGLE.")
										.withAllowedValues(List.of("SINGLE", "COMBINED", "NORMALIZED"))),
						AccumulatorTools::getCharts),
				read("get_accumulators_for_producer",
						"Returns the ids of the accumulators tied to a producer.",
						List.of(string("producerId", "Id of the producer.").asRequired()),
						(apis, args) -> apis.get(AccumulatorAPI.class)
								.getAccumulatorIdsTiedToASpecificProducer(args.requiredString("producerId"))),
				write("create_accumulator",
						"Creates an accumulator that records a producer value over time.",
						List.of(string("name", "Name of the accumulator.").asRequired(),
								string("producerId", "Id of the producer.").asRequired(),
								string("statName", "Name of the stat (e.g. a method name or cumulated).").asRequired(),
								string("valueName", "Name of the value (e.g. TR, TT, Avg, ERR).").asRequired(),
								INTERVAL, UNIT),
						AccumulatorTools::create),
				destructive("remove_accumulator",
						"Removes an accumulator.",
						List.of(ACCUMULATOR_ID),
						AccumulatorTools::remove)
		);
	}

	private static Object getAccumulator(Apis apis, ToolArguments args) throws APIException {
		String id = args.string("accumulatorId");
		if (id != null)
			return apis.get(AccumulatorAPI.class).getAccumulator(id);
		String name = args.string("name");
		if (name != null)
			return apis.get(AccumulatorAPI.class).getAccumulatorByName(name);
		throw new IllegalArgumentException("Either accumulatorId or name is required.");
	}

	private static Object getCharts(Apis apis, ToolArguments args) throws APIException {
		List<String> ids = args.requiredStringList("accumulatorIds");
		AccumulatorAPI api = apis.get(AccumulatorAPI.class);
		String mode = args.string("mode", "SINGLE").toUpperCase(Locale.ROOT);
		return switch (mode) {
			case "SINGLE" -> api.getChartsForMultipleAccumulators(ids);
			case "COMBINED" -> api.getCombinedAccumulatorGraphData(ids);
			case "NORMALIZED" -> api.getNormalizedAccumulatorGraphData(ids);
			default -> throw new IllegalArgumentException("Unknown mode '" + mode + "', expected SINGLE, COMBINED or NORMALIZED.");
		};
	}

	private static Object create(Apis apis, ToolArguments args) throws APIException {
		AccumulatorPO po = new AccumulatorPO();
		po.setName(args.requiredString("name"));
		po.setProducerId(args.requiredString("producerId"));
		po.setStatName(args.requiredString("statName"));
		po.setValueName(args.requiredString("valueName"));
		po.setInterval(args.interval());
		po.setUnit(args.unit().name());
		return apis.get(AccumulatorAPI.class).createAccumulator(po);
	}

	private static Object remove(Apis apis, ToolArguments args) throws APIException {
		String id = args.requiredString("accumulatorId");
		apis.get(AccumulatorAPI.class).removeAccumulator(id);
		return "Accumulator " + id + " removed.";
	}
}
