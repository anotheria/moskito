package net.anotheria.moskito.core.context;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 22.06.17 00:43
 */
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
