package net.anotheria.moskito.webui.mcp;

import net.anotheria.moskito.webui.util.RemoteInstance;
import net.anotheria.moskito.webui.util.WebUIConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Resolves the {@code targetSystem} argument of a tool call to the moskito instance the call is executed against. This
 * is the mcp counterpart of the {@code remoteConnection} url parameter of moskito-inspect, but scoped to a single call
 * instead of the session: the connectivity mode of the web ui is neither read nor changed.
 *
 * @author lrosenberg
 */
final class TargetSystems {

	/**
	 * Value of the target system argument that explicitly selects the local instance.
	 */
	static final String LOCAL = "local";

	private TargetSystems() {
	}

	/**
	 * Resolves a target system. An omitted, empty or {@value #LOCAL} target resolves to null, the local instance.
	 * Otherwise the target is matched against the configured remotes by name or {@code host:port}; an unknown
	 * {@code host:port} is registered as new remote, the same way moskito-inspect does for the remoteConnection
	 * parameter. The registered instance is returned, because remote stubs are cached per instance.
	 * @param targetSystem the target system argument.
	 * @return the remote instance or null for the local instance.
	 * @throws IllegalArgumentException if the target is neither local, a configured remote nor a valid host:port.
	 */
	static RemoteInstance resolve(String targetSystem) {
		if (targetSystem == null || targetSystem.isBlank() || LOCAL.equalsIgnoreCase(targetSystem.trim()))
			return null;
		String target = targetSystem.trim();

		RemoteInstance configured = findConfigured(target);
		if (configured != null)
			return configured;

		int separator = target.lastIndexOf(':');
		if (separator <= 0 || separator == target.length() - 1)
			throw unknownTarget(target);
		int port;
		try {
			port = Integer.parseInt(target.substring(separator + 1));
		} catch (NumberFormatException e) {
			throw unknownTarget(target);
		}
		if (port < 1 || port > 65535)
			throw unknownTarget(target);

		RemoteInstance newInstance = new RemoteInstance();
		newInstance.setHost(target.substring(0, separator));
		newInstance.setPort(port);
		WebUIConfig.getInstance().addRemote(newInstance);
		//another call may have registered the same remote concurrently, so we use whatever ended up in the config.
		RemoteInstance registered = findConfigured(target);
		return registered == null ? newInstance : registered;
	}

	/**
	 * Returns all known target systems, starting with the local instance.
	 * @return the target systems.
	 */
	static List<TargetSystem> list() {
		List<TargetSystem> systems = new ArrayList<>();
		systems.add(new TargetSystem(LOCAL, LOCAL));
		for (RemoteInstance remote : WebUIConfig.getInstance().getRemotes())
			systems.add(new TargetSystem(remote.getName(), hostAndPort(remote)));
		return systems;
	}

	private static RemoteInstance findConfigured(String target) {
		for (RemoteInstance remote : WebUIConfig.getInstance().getRemotes()) {
			if (target.equals(hostAndPort(remote)))
				return remote;
		}
		for (RemoteInstance remote : WebUIConfig.getInstance().getRemotes()) {
			if (target.equals(remote.getName()))
				return remote;
		}
		return null;
	}

	private static String hostAndPort(RemoteInstance remote) {
		return remote.getHost() + ':' + remote.getPort();
	}

	private static IllegalArgumentException unknownTarget(String target) {
		return new IllegalArgumentException("Unknown target system '" + target + "', expected '" + LOCAL
				+ "', the name of a configured remote or host:port (see list_target_systems).");
	}

	/**
	 * A target system as returned by the list_target_systems tool.
	 * @param name display name of the system.
	 * @param targetSystem value to pass as targetSystem argument.
	 */
	record TargetSystem(String name, String targetSystem) {
	}
}
