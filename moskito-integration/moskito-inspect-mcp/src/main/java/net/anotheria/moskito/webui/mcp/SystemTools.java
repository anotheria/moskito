package net.anotheria.moskito.webui.mcp;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.moskito.core.config.KillSwitchConfiguration;
import net.anotheria.moskito.webui.shared.api.AdditionalFunctionalityAPI;

import java.util.List;

import static net.anotheria.moskito.webui.mcp.MoskitoTool.destructive;
import static net.anotheria.moskito.webui.mcp.MoskitoTool.localOnly;
import static net.anotheria.moskito.webui.mcp.MoskitoTool.read;
import static net.anotheria.moskito.webui.mcp.MoskitoTool.write;
import static net.anotheria.moskito.webui.mcp.ToolParameter.bool;
import static net.anotheria.moskito.webui.mcp.ToolParameter.string;

/**
 * Tools about the monitored system itself, mostly backed by {@link AdditionalFunctionalityAPI}.
 *
 * @author lrosenberg
 */
final class SystemTools {

	private static final ToolParameter INTERVAL_NAME = string("intervalName", "Name of the interval, e.g. 1m.").asRequired();

	private SystemTools() {
	}

	static List<MoskitoTool> tools() {
		return List.of(
				localOnly("list_target_systems",
						"Returns the moskito instances the tools can be executed against: the local one and the "
								+ "configured remotes. Pass the targetSystem value to any other tool to query that "
								+ "instance.",
						(apis, args) -> TargetSystems.list()),
				read("get_system_status",
						"Returns the overall status: worst threshold status and kill switch configuration.",
						List.of(),
						(apis, args) -> apis.get(AdditionalFunctionalityAPI.class).getSystemStatus()),
				read("get_interval_infos",
						"Returns the available intervals with their length and last update.",
						List.of(),
						(apis, args) -> apis.get(AdditionalFunctionalityAPI.class).getIntervalInfos()),
				read("get_interval_update_timestamp",
						"Returns the timestamp (millis) of the last update of an interval.",
						List.of(INTERVAL_NAME),
						(apis, args) -> apis.get(AdditionalFunctionalityAPI.class)
								.getIntervalUpdateTimestamp(args.requiredString("intervalName"))),
				write("force_interval_update",
						"Forces an immediate update of an interval, so its values become available without waiting "
								+ "for the interval to end.",
						List.of(INTERVAL_NAME),
						SystemTools::forceIntervalUpdate),
				read("get_configuration",
						"Returns the active moskito configuration as json.",
						List.of(),
						(apis, args) -> apis.get(AdditionalFunctionalityAPI.class).getConfigurationAsString()),
				read("get_plugins",
						"Returns the loaded moskito plugins.",
						List.of(),
						(apis, args) -> apis.get(AdditionalFunctionalityAPI.class).getPlugins()),
				destructive("remove_plugin",
						"Unloads a moskito plugin.",
						List.of(string("pluginName", "Name of the plugin.").asRequired()),
						SystemTools::removePlugin),
				read("get_mbeans",
						"Returns the mbeans of the jvm with their attributes.",
						List.of(),
						(apis, args) -> apis.get(AdditionalFunctionalityAPI.class).getMBeans()),
				read("get_error_catchers",
						"Returns the active error catchers with the number of errors they caught.",
						List.of(),
						(apis, args) -> apis.get(AdditionalFunctionalityAPI.class).getActiveErrorCatchers()),
				read("get_caught_errors",
						"Returns the errors caught by an error catcher, including stack traces and tags.",
						List.of(string("catcherName", "Name of the catcher, usually the exception class name.").asRequired(),
								string("catcherType", "Type of the catcher, as returned by get_error_catchers.").asRequired()),
						(apis, args) -> apis.get(AdditionalFunctionalityAPI.class).getCaughtErrorsByExceptionName(
								args.requiredString("catcherName"), args.requiredString("catcherType"))),
				write("set_kill_switch",
						"Configures the kill switch, which disables metric collection and/or tracing at runtime.",
						List.of(bool("disableMetricCollection", "True to disable metric collection.").asRequired(),
								bool("disableTracing", "True to disable tracing.").asRequired()),
						SystemTools::setKillSwitch)
		);
	}

	private static Object forceIntervalUpdate(Apis apis, ToolArguments args) throws APIException {
		String intervalName = args.requiredString("intervalName");
		apis.get(AdditionalFunctionalityAPI.class).forceIntervalUpdate(intervalName);
		return "Interval " + intervalName + " updated.";
	}

	private static Object removePlugin(Apis apis, ToolArguments args) throws APIException {
		String pluginName = args.requiredString("pluginName");
		apis.get(AdditionalFunctionalityAPI.class).removePlugin(pluginName);
		return "Plugin " + pluginName + " removed.";
	}

	private static Object setKillSwitch(Apis apis, ToolArguments args) throws APIException {
		KillSwitchConfiguration configuration = new KillSwitchConfiguration();
		configuration.setDisableMetricCollection(args.requiredBool("disableMetricCollection"));
		configuration.setDisableTracing(args.requiredBool("disableTracing"));
		apis.get(AdditionalFunctionalityAPI.class).setKillSwitchConfiguration(configuration);
		return "Kill switch set to " + configuration + '.';
	}
}
