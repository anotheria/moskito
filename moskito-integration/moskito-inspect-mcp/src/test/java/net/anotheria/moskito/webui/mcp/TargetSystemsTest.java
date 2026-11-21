package net.anotheria.moskito.webui.mcp;

import net.anotheria.moskito.webui.util.RemoteInstance;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TargetSystemsTest {

	@Test
	void omittedOrLocalTargetIsLocal() {
		assertNull(TargetSystems.resolve(null));
		assertNull(TargetSystems.resolve(""));
		assertNull(TargetSystems.resolve(" LOCAL "));
	}

	@Test
	void hostAndPortIsRegisteredOnceAndReused() {
		RemoteInstance first = TargetSystems.resolve("mcp-test-host:9451");
		assertEquals("mcp-test-host", first.getHost());
		assertEquals(9451, first.getPort());
		//remote stubs are cached per instance, so the same instance must be returned for the same target.
		assertSame(first, TargetSystems.resolve("mcp-test-host:9451"));
		assertTrue(TargetSystems.list().stream().anyMatch(system -> system.targetSystem().equals("mcp-test-host:9451")));
	}

	@Test
	void invalidTargetsAreRejected() {
		assertThrows(IllegalArgumentException.class, () -> TargetSystems.resolve("no-port"));
		assertThrows(IllegalArgumentException.class, () -> TargetSystems.resolve("host:"));
		assertThrows(IllegalArgumentException.class, () -> TargetSystems.resolve("host:abc"));
		assertThrows(IllegalArgumentException.class, () -> TargetSystems.resolve("host:70000"));
	}
}
