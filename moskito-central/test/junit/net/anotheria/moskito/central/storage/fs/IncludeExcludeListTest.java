package net.anotheria.moskito.central.storage.fs;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 25.03.13 10:35
 */
public class IncludeExcludeListTest {
	@Test
	public void testAsteriskInclude(){
		IncludeExcludeList list = new IncludeExcludeList("*", "Foo");
		assertFalse(list.include("Foo"));
		assertTrue(list.include("whatever"));
		assertTrue(list.include("*"));
		assertTrue(list.include("blubili"));
	}

	@Test public void testIncludeExclude(){
		IncludeExcludeList list = new IncludeExcludeList("alpha, beta, gamma", "one,two, three ");
		assertFalse(list.include("one"));
		assertFalse(list.include("two"));
		assertFalse(list.include("three"));

		assertTrue(list.include("alpha"));
		assertTrue(list.include("beta"));
		assertTrue(list.include("gamma"));
	}
}
