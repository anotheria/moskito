package net.anotheria.moskito.webui.mcp;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.moskito.webui.gauges.api.GaugeAPI;
import net.anotheria.moskito.webui.loadfactors.api.LoadFactorsAPI;
import net.anotheria.moskito.webui.nowrunning.api.NowRunningAPI;
import net.anotheria.moskito.webui.tags.api.TagAPI;
import net.anotheria.moskito.webui.topproducers.api.TopProducersAPI;

import java.util.List;

import static net.anotheria.moskito.webui.mcp.MoskitoTool.destructive;
import static net.anotheria.moskito.webui.mcp.MoskitoTool.read;
import static net.anotheria.moskito.webui.mcp.ToolParameter.integer;
import static net.anotheria.moskito.webui.mcp.ToolParameter.string;
import static net.anotheria.moskito.webui.mcp.ToolParameter.stringArray;

/**
 * Tools backed by the smaller read apis: gauges, load factors, tags, now running and top producers.
 *
 * @author lrosenberg
 */
final class MonitoringTools {

	/**
	 * Default number of top producers returned per category.
	 */
	private static final int DEFAULT_TOP_PRODUCERS_LIMIT = 10;

	private MonitoringTools() {
	}

	static List<MoskitoTool> tools() {
		return List.of(
				read("get_gauges",
						"Returns gauges (a current value between a min and a max, with colored zones).",
						List.of(stringArray("names", "Only return the gauges with these names.")),
						MonitoringTools::getGauges),
				read("get_load_factors",
						"Returns the configured load factors (ratio between two producer values).",
						List.of(),
						(apis, args) -> apis.get(LoadFactorsAPI.class).getLoadFactors()),
				read("get_tags",
						"Returns the configured tags with their last values. Tags are attached to errors and traces.",
						List.of(),
						(apis, args) -> apis.get(TagAPI.class).getTags()),
				read("get_now_running",
						"Returns the entry points with the requests that are running right now and recently finished "
								+ "ones.",
						List.of(),
						(apis, args) -> apis.get(NowRunningAPI.class).getEntryPoints()),
				destructive("remove_past_measurement",
						"Removes a recently finished request from an entry point.",
						List.of(string("producerId", "Id of the entry point producer.").asRequired(),
								string("measurementPosition", "Position of the past measurement.").asRequired()),
						MonitoringTools::removePastMeasurement),
				read("get_top_producer_categories",
						"Returns the categories the top producers are ranked in.",
						List.of(),
						(apis, args) -> apis.get(TopProducersAPI.class).getCategories()),
				read("get_top_producers",
						"Returns the producers that consume the most resources (requests, total time, errors, error "
								+ "rate, max concurrent requests), ranked over time, so they can be used as optimization "
								+ "targets.",
						List.of(string("category", "Ranking category to return, see get_top_producer_categories. If "
										+ "omitted, the top producers of every category are returned."),
								integer("limit", "Maximum number of producers to return per category. Defaults to "
										+ DEFAULT_TOP_PRODUCERS_LIMIT + '.'),
								string("scoreType", "Score to rank by. ORDINAL ranks by how consistently a producer is "
										+ "among the heaviest ones, SHARE by how much of the category total it accounts "
										+ "for. Both scores are returned either way. Defaults to ORDINAL.")
										.withAllowedValues(List.of("ORDINAL", "SHARE"))),
						MonitoringTools::getTopProducers)
		);
	}

	private static Object getGauges(Apis apis, ToolArguments args) throws APIException {
		List<String> names = args.stringList("names");
		GaugeAPI api = apis.get(GaugeAPI.class);
		return names.isEmpty() ? api.getGauges() : api.getGauges(names.toArray(new String[0]));
	}

	private static Object removePastMeasurement(Apis apis, ToolArguments args) throws APIException {
		String producerId = args.requiredString("producerId");
		String position = args.requiredString("measurementPosition");
		apis.get(NowRunningAPI.class).removePastMeasurement(producerId, position);
		return "Past measurement " + position + " of " + producerId + " removed.";
	}

	private static Object getTopProducers(Apis apis, ToolArguments args) throws APIException {
		TopProducersAPI api = apis.get(TopProducersAPI.class);
		int limit = args.integer("limit", DEFAULT_TOP_PRODUCERS_LIMIT);
		String scoreType = args.string("scoreType");
		String category = args.string("category");
		return category == null
				? api.getTopProducersByAllCategories(limit, scoreType)
				: api.getTopProducers(category, limit, scoreType);
	}
}
