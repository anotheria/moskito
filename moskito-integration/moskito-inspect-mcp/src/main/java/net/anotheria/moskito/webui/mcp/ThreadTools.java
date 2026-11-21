package net.anotheria.moskito.webui.mcp;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.moskito.webui.threads.api.ThreadAPI;

import java.util.List;

import static net.anotheria.moskito.webui.mcp.MoskitoTool.read;
import static net.anotheria.moskito.webui.mcp.MoskitoTool.write;
import static net.anotheria.moskito.webui.mcp.ToolParameter.bool;

/**
 * Tools backed by {@link ThreadAPI}.
 *
 * @author lrosenberg
 */
final class ThreadTools {

	private ThreadTools() {
	}

	static List<MoskitoTool> tools() {
		return List.of(
				read("get_threads_info",
						"Returns a summary of the threads of the jvm (counts per state).",
						List.of(),
						(apis, args) -> apis.get(ThreadAPI.class).getThreadsInfo()),
				read("get_threads",
						"Returns all threads of the jvm with state, cpu time and blocking/waiting information.",
						List.of(),
						(apis, args) -> apis.get(ThreadAPI.class).getThreadInfos()),
				read("get_thread_dump",
						"Returns a thread dump: all threads with their stack traces.",
						List.of(),
						(apis, args) -> apis.get(ThreadAPI.class).getThreadDump()),
				read("get_thread_history",
						"Returns the history of started and finished threads (only recorded while active, see "
								+ "set_thread_history).",
						List.of(),
						(apis, args) -> apis.get(ThreadAPI.class).getActiveThreadHistory()),
				write("set_thread_history",
						"Activates or deactivates the recording of the thread history.",
						List.of(bool("active", "True to activate, false to deactivate.").asRequired()),
						ThreadTools::setHistory),
				write("start_test_thread",
						"Starts a short-lived test thread, e.g. to verify the thread history.",
						List.of(),
						ThreadTools::startTestThread)
		);
	}

	private static Object setHistory(Apis apis, ToolArguments args) throws APIException {
		boolean active = args.requiredBool("active");
		if (active)
			apis.get(ThreadAPI.class).activateHistory();
		else
			apis.get(ThreadAPI.class).deactivateHistory();
		return "Thread history " + (active ? "activated." : "deactivated.");
	}

	private static Object startTestThread(Apis apis, ToolArguments args) throws APIException {
		apis.get(ThreadAPI.class).startTestThread();
		return "Test thread started.";
	}
}
