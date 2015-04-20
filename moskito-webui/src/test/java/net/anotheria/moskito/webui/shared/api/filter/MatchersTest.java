package net.anotheria.moskito.webui.shared.api.filter;

import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 20.04.15 11:27
 */
public class MatchersTest {
	@Test
	public void testMatchersCreation(){
		assertTrue(Matchers.createMatcher("*moskito*") instanceof SubstringMatcher);
		assertTrue(Matchers.createMatcher("moskito*") instanceof EndsWithMatcher);
		assertTrue(Matchers.createMatcher("*moskito") instanceof StartsWithMatcher);
		assertTrue(Matchers.createMatcher("moskito") instanceof EqualsMatcher);

		//check that asterisk doesn't have any impact.
		assertTrue(Matchers.createMatcher("*mos*kito*") instanceof SubstringMatcher);
		assertTrue(Matchers.createMatcher("mos*kito*") instanceof EndsWithMatcher);
		assertTrue(Matchers.createMatcher("*mos*kito") instanceof StartsWithMatcher);
		assertTrue(Matchers.createMatcher("mosk*ito") instanceof EqualsMatcher);

	}
}
