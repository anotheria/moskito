package net.anotheria.moskito.webui.mcp;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.moskito.webui.producers.api.ProducerAPI;

import java.util.List;

import static net.anotheria.moskito.webui.mcp.MoskitoTool.INTERVAL;
import static net.anotheria.moskito.webui.mcp.MoskitoTool.UNIT;
import static net.anotheria.moskito.webui.mcp.MoskitoTool.read;
import static net.anotheria.moskito.webui.mcp.MoskitoTool.write;
import static net.anotheria.moskito.webui.mcp.ToolParameter.bool;
import static net.anotheria.moskito.webui.mcp.ToolParameter.string;
import static net.anotheria.moskito.webui.mcp.ToolParameter.stringArray;

/**
 * Tools backed by {@link ProducerAPI}.
 *
 * @author lrosenberg
 */
final class ProducerTools {

	private static final ToolParameter PRODUCER_ID = string("producerId", "Id of the producer.").asRequired();

	private ProducerTools() {
	}

	static List<MoskitoTool> tools() {
		return List.of(
				read("get_producer_categories",
						"Returns all producer categories with the number of producers in each.",
						List.of(),
						(apis, args) -> apis.get(ProducerAPI.class).getCategories()),
				read("get_producer_subsystems",
						"Returns all producer subsystems with the number of producers in each.",
						List.of(),
						(apis, args) -> apis.get(ProducerAPI.class).getSubsystems()),
				read("get_producers",
						"Returns producers with their stats in the given interval. Filter by category, subsystem or a "
								+ "list of producer ids (at most one filter). Without a filter all producers are "
								+ "returned, which can be large.",
						List.of(
								string("category", "Only return producers of this category."),
								string("subsystem", "Only return producers of this subsystem."),
								stringArray("producerIds", "Only return the producers with these ids."),
								INTERVAL, UNIT),
						ProducerTools::getProducers),
				read("get_producer",
						"Returns a single producer with all its stats in the given interval.",
						List.of(PRODUCER_ID, INTERVAL, UNIT),
						(apis, args) -> apis.get(ProducerAPI.class)
								.getProducer(args.requiredString("producerId"), args.interval(), args.unit())),
				read("get_producer_value",
						"Returns a single value of a producer's stat, e.g. the average time (valueName Avg) of the "
								+ "stat cumulated.",
						List.of(PRODUCER_ID,
								string("statName", "Name of the stat (e.g. a method name or cumulated).").asRequired(),
								string("valueName", "Name of the value (e.g. TR, TT, Avg, ERR).").asRequired(),
								INTERVAL, UNIT),
						(apis, args) -> apis.get(ProducerAPI.class).getSingleValue(args.requiredString("producerId"),
								args.requiredString("statName"), args.requiredString("valueName"), args.interval(),
								args.unit())),
				write("set_producer_logging",
						"Enables or disables call logging for a producer that supports it.",
						List.of(PRODUCER_ID, bool("enabled", "True to enable, false to disable.").asRequired()),
						ProducerTools::setLogging),
				write("set_producer_source_monitoring",
						"Enables or disables source monitoring (recording of the callers) for a producer that "
								+ "supports it.",
						List.of(PRODUCER_ID, bool("enabled", "True to enable, false to disable.").asRequired()),
						ProducerTools::setSourceMonitoring)
		);
	}

	private static Object getProducers(Apis apis, ToolArguments args) throws APIException {
		ProducerAPI api = apis.get(ProducerAPI.class);
		String category = args.string("category");
		String subsystem = args.string("subsystem");
		List<String> producerIds = args.stringList("producerIds");
		int filters = (category == null ? 0 : 1) + (subsystem == null ? 0 : 1) + (producerIds.isEmpty() ? 0 : 1);
		if (filters > 1)
			throw new IllegalArgumentException("Use at most one of category, subsystem and producerIds.");
		if (category != null)
			return api.getAllProducersByCategory(category, args.interval(), args.unit());
		if (subsystem != null)
			return api.getAllProducersBySubsystem(subsystem, args.interval(), args.unit());
		if (!producerIds.isEmpty())
			return api.getProducers(producerIds, args.interval(), args.unit());
		return api.getAllProducers(args.interval(), args.unit());
	}

	private static Object setLogging(Apis apis, ToolArguments args) throws APIException {
		String producerId = args.requiredString("producerId");
		boolean enabled = args.requiredBool("enabled");
		if (enabled)
			apis.get(ProducerAPI.class).enableLogging(producerId);
		else
			apis.get(ProducerAPI.class).disableLogging(producerId);
		return "Logging " + (enabled ? "enabled" : "disabled") + " for producer " + producerId + '.';
	}

	private static Object setSourceMonitoring(Apis apis, ToolArguments args) throws APIException {
		String producerId = args.requiredString("producerId");
		boolean enabled = args.requiredBool("enabled");
		if (enabled)
			apis.get(ProducerAPI.class).enableSourceMonitoring(producerId);
		else
			apis.get(ProducerAPI.class).disableSourceMonitoring(producerId);
		return "Source monitoring " + (enabled ? "enabled" : "disabled") + " for producer " + producerId + '.';
	}
}
