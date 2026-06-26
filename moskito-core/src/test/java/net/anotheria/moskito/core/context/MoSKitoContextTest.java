package net.anotheria.moskito.core.context;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class MoSKitoContextTest {
	@Test public void testExceptionVisibility(){
		MoSKitoContext.get();
		RuntimeException e = new RuntimeException();
		assertFalse(MoSKitoContext.get().seenErrorAlready(e));
		assertTrue(MoSKitoContext.get().seenErrorAlready(e));

		MoSKitoContext.get().reset();
	}

	@Test public void testExceptionNaming(){
		RuntimeException e1 = new RuntimeException();
		RuntimeException e2 = new RuntimeException();

		Integer firstKey = MoSKitoContext.getHashKey(e1);
		Integer secondKey = MoSKitoContext.getHashKey(e2);
		Integer secondKeyV2 = MoSKitoContext.getHashKey(e2);

		assertNotEquals(firstKey, secondKey);
		assertEquals(secondKey, secondKeyV2);
	}
}
