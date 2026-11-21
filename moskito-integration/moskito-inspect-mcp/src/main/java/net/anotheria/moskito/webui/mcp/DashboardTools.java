package net.anotheria.moskito.webui.mcp;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.moskito.webui.dashboards.api.DashboardAPI;

import java.util.List;
import java.util.Locale;

import static net.anotheria.moskito.webui.mcp.MoskitoTool.destructive;
import static net.anotheria.moskito.webui.mcp.MoskitoTool.read;
import static net.anotheria.moskito.webui.mcp.MoskitoTool.write;
import static net.anotheria.moskito.webui.mcp.ToolParameter.string;
import static net.anotheria.moskito.webui.mcp.ToolParameter.stringArray;

/**
 * Tools backed by {@link DashboardAPI}.
 *
 * @author lrosenberg
 */
final class DashboardTools {

	private static final ToolParameter DASHBOARD_NAME = string("dashboardName", "Name of the dashboard.").asRequired();

	private static final ToolParameter ELEMENT_TYPE = string("elementType",
			"Type of the dashboard element. GAUGE, THRESHOLD and PRODUCER are referenced by elementName, CHART by "
					+ "accumulatorNames.")
			.withAllowedValues(List.of("GAUGE", "THRESHOLD", "CHART", "PRODUCER")).asRequired();

	private static final ToolParameter ELEMENT_NAME = string("elementName",
			"Name of the gauge, threshold or producer.");

	private static final ToolParameter ACCUMULATOR_NAMES = stringArray("accumulatorNames",
			"Names of the accumulators shown in the chart (CHART only).");

	/**
	 * Result of get_dashboard_names.
	 * @param defaultDashboard name of the default dashboard.
	 * @param dashboards names of all dashboards.
	 */
	record DashboardNames(String defaultDashboard, List<String> dashboards) {
	}

	private DashboardTools() {
	}

	static List<MoskitoTool> tools() {
		return List.of(
				read("get_dashboard_names",
						"Returns the names of all dashboards and the name of the default dashboard.",
						List.of(),
						(apis, args) -> new DashboardNames(apis.get(DashboardAPI.class).getDefaultDashboardName(),
								apis.get(DashboardAPI.class).getDashboardNames())),
				read("get_dashboard",
						"Returns a dashboard with the current data of its gauges, thresholds, charts and producers.",
						List.of(DASHBOARD_NAME),
						(apis, args) -> apis.get(DashboardAPI.class).getDashboard(args.requiredString("dashboardName"))),
				read("get_dashboard_config",
						"Returns the configuration of a dashboard (which elements it contains).",
						List.of(DASHBOARD_NAME),
						(apis, args) -> apis.get(DashboardAPI.class)
								.getDashboardConfig(args.requiredString("dashboardName"))),
				write("create_dashboard",
						"Creates a new, empty dashboard.",
						List.of(DASHBOARD_NAME),
						DashboardTools::create),
				destructive("remove_dashboard",
						"Removes a dashboard.",
						List.of(DASHBOARD_NAME),
						DashboardTools::remove),
				write("add_to_dashboard",
						"Adds a gauge, threshold, producer or accumulator chart to a dashboard.",
						List.of(DASHBOARD_NAME, ELEMENT_TYPE, ELEMENT_NAME, ACCUMULATOR_NAMES,
								string("caption", "Caption of the chart (CHART only).")),
						DashboardTools::add),
				destructive("remove_from_dashboard",
						"Removes a gauge, threshold, producer or accumulator chart from a dashboard.",
						List.of(DASHBOARD_NAME, ELEMENT_TYPE, ELEMENT_NAME, ACCUMULATOR_NAMES),
						DashboardTools::removeElement)
		);
	}

	private static Object create(Apis apis, ToolArguments args) throws APIException {
		String name = args.requiredString("dashboardName");
		apis.get(DashboardAPI.class).createDashboard(name);
		return "Dashboard " + name + " created.";
	}

	private static Object remove(Apis apis, ToolArguments args) throws APIException {
		String name = args.requiredString("dashboardName");
		apis.get(DashboardAPI.class).removeDashboard(name);
		return "Dashboard " + name + " removed.";
	}

	private static Object add(Apis apis, ToolArguments args) throws APIException {
		DashboardAPI api = apis.get(DashboardAPI.class);
		String dashboard = args.requiredString("dashboardName");
		String type = args.requiredString("elementType").toUpperCase(Locale.ROOT);
		switch (type) {
			case "GAUGE" -> api.addGaugeToDashboard(dashboard, args.requiredString("elementName"));
			case "THRESHOLD" -> api.addThresholdToDashboard(dashboard, args.requiredString("elementName"));
			case "PRODUCER" -> api.addProducerToDashboard(dashboard, args.requiredString("elementName"));
			case "CHART" -> api.addChartToDashboard(dashboard, args.string("caption"),
					args.requiredStringList("accumulatorNames").toArray(new String[0]));
			default -> throw unknownType(type);
		}
		return type + " added to dashboard " + dashboard + '.';
	}

	private static Object removeElement(Apis apis, ToolArguments args) throws APIException {
		DashboardAPI api = apis.get(DashboardAPI.class);
		String dashboard = args.requiredString("dashboardName");
		String type = args.requiredString("elementType").toUpperCase(Locale.ROOT);
		switch (type) {
			case "GAUGE" -> api.removeGaugeFromDashboard(dashboard, args.requiredString("elementName"));
			case "THRESHOLD" -> api.removeThresholdFromDashboard(dashboard, args.requiredString("elementName"));
			case "PRODUCER" -> api.removeProducerFromDashboard(dashboard, args.requiredString("elementName"));
			case "CHART" -> api.removeChartFromDashboard(dashboard,
					args.requiredStringList("accumulatorNames").toArray(new String[0]));
			default -> throw unknownType(type);
		}
		return type + " removed from dashboard " + dashboard + '.';
	}

	private static IllegalArgumentException unknownType(String type) {
		return new IllegalArgumentException("Unknown elementType '" + type + "', expected GAUGE, THRESHOLD, CHART or PRODUCER.");
	}
}
