package net.anotheria.moskito.webui.mcp;

import net.anotheria.anoplass.api.API;
import net.anotheria.moskito.webui.util.APILookupUtility;
import net.anotheria.moskito.webui.util.RemoteInstance;

/**
 * Gives a tool access to the moskito-inspect apis of the target system of the current call. Tools call the api methods
 * exactly like the actions of moskito-inspect do, whether the api is local or a remote stub is transparent.
 *
 * @param target the remote instance, or null for the local instance.
 * @author lrosenberg
 */
record Apis(RemoteInstance target) {

	<T extends API> T get(Class<T> apiClass) {
		return APILookupUtility.getAPI(apiClass, target);
	}
}
