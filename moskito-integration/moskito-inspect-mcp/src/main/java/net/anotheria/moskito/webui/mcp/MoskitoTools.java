package net.anotheria.moskito.webui.mcp;

import java.util.ArrayList;
import java.util.List;

/**
 * Registry of all mcp tools. Every tool maps onto a method of a moskito-inspect api, so the tools offer the same
 * functionality as the actions of moskito-inspect.
 *
 * @author lrosenberg
 */
final class MoskitoTools {

	private MoskitoTools() {
	}

	static List<MoskitoTool> all() {
		List<MoskitoTool> tools = new ArrayList<>();
		tools.addAll(SystemTools.tools());
		tools.addAll(ProducerTools.tools());
		tools.addAll(MonitoringTools.tools());
		tools.addAll(ThresholdTools.tools());
		tools.addAll(AccumulatorTools.tools());
		tools.addAll(DashboardTools.tools());
		tools.addAll(JourneyTools.tools());
		tools.addAll(TracerTools.tools());
		tools.addAll(ThreadTools.tools());
		return tools;
	}
}
