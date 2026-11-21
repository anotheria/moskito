package net.anotheria.moskito.webui.mcp;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class McpKeyTest {

	@Test
	void blankKeyMeansNoKey() {
		assertNull(McpKey.of(null));
		assertNull(McpKey.of("  "));
	}

	@Test
	void onlyTheBearerTokenMatches() {
		McpKey key = McpKey.of("secret");
		assertTrue(key.matches("Bearer secret"));
		assertTrue(key.matches("bearer  secret "));
		assertFalse(key.matches("secret"));
		assertFalse(key.matches("Bearer secret2"));
		assertFalse(key.matches("Bearer "));
		assertFalse(key.matches(null));
	}
}
