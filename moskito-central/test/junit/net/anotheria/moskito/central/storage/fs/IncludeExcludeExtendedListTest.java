package net.anotheria.moskito.central.storage.fs;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Extended IncludeExcludeListTest.
 * 
 * @author dagafonov
 * @since 25.03.13 10:35
 */
public class IncludeExcludeExtendedListTest {

	@Test
	public void testAsteriskInclude() {
		IncludeExcludeWildcardList list = new IncludeExcludeWildcardList("data,*API,*Service*,Random*,boom", "*oo*");
		assertFalse(list.include("Foo"));
		assertTrue(list.include("data"));
		assertTrue(list.include("AccumulatorAPI"));
		assertTrue(list.include("CommentServiceImpl"));
		assertTrue(list.include("Random1"));
		assertTrue(list.include("Randomize"));
		assertFalse(list.include("Randoize"));
		assertFalse(list.include("doom"));
		assertTrue(list.include("boom"));
		assertFalse(list.include("bloomberg"));
	}

	@Test
	public void testIncludeExclude() {
		IncludeExcludeWildcardList list = new IncludeExcludeWildcardList("alpha, beta, *ma", "one,two, three  ");
		assertFalse(list.include("one"));
		assertFalse(list.include("two"));
		assertFalse(list.include("three"));
		assertFalse(list.include("dimi"));

		assertTrue(list.include("alpha"));
		assertTrue(list.include("beta"));
		assertTrue(list.include("gamma"));
	}
}
