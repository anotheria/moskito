package net.anotheria.moskito.webui.mcp;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * The key clients have to present to use the mcp endpoint, as {@code Authorization: Bearer <key>}. The mcp endpoint
 * is not covered by the moskito-inspect authentication and gives full access to the apis (including write tools and
 * remote instances), so it is only enabled if a key is configured.
 *
 * @author lrosenberg
 */
final class McpKey {

	/**
	 * Environment variable with the key, e.g. passed to the docker container.
	 */
	static final String ENV_VARIABLE = "MOSKITO_MCP_KEY";

	/**
	 * System property with the key, takes precedence over the environment variable.
	 */
	static final String SYSTEM_PROPERTY = "moskito.mcp.key";

	private static final String BEARER_PREFIX = "Bearer ";

	private final byte[] key;

	private McpKey(String key) {
		this.key = key.getBytes(StandardCharsets.UTF_8);
	}

	/**
	 * Reads the configured key.
	 * @return the key, or null if none is configured.
	 */
	static McpKey fromEnvironment() {
		String key = System.getProperty(SYSTEM_PROPERTY);
		if (key == null || key.isBlank())
			key = System.getenv(ENV_VARIABLE);
		return of(key);
	}

	/**
	 * Creates a key from the given value.
	 * @param key the key value.
	 * @return the key, or null if the value is null or blank.
	 */
	static McpKey of(String key) {
		return key == null || key.isBlank() ? null : new McpKey(key.trim());
	}

	/**
	 * Checks the value of the authorization header.
	 * @param authorizationHeader the header value, may be null.
	 * @return true if the header carries this key as bearer token.
	 */
	boolean matches(String authorizationHeader) {
		if (authorizationHeader == null || !authorizationHeader.regionMatches(true, 0, BEARER_PREFIX, 0, BEARER_PREFIX.length()))
			return false;
		byte[] presented = authorizationHeader.substring(BEARER_PREFIX.length()).trim().getBytes(StandardCharsets.UTF_8);
		//constant time comparison, so the key can't be guessed by timing.
		return MessageDigest.isEqual(key, presented);
	}
}
