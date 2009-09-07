/*
 * $Id$
 * 
 * This file is part of the MoSKito software project
 * that is hosted at http://moskito.dev.java.net.
 * 
 * All MoSKito files are distributed under MIT License:
 * 
 * Copyright (c) 2006 The MoSKito Project Team.
 * 
 * Permission is hereby granted, free of charge,
 * to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), 
 * to deal in the Software without restriction, 
 * including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit 
 * persons to whom the Software is furnished to do so, 
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice
 * shall be included in all copies 
 * or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY
 * OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS 
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, 
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */	
package net.java.dev.moskito.core.stats.impl;

import org.junit.Test;

import net.java.dev.moskito.core.stats.UnknownIntervalLengthException;
import net.java.dev.moskito.core.stats.impl.IntervalNameParser;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;;

/**
 * This is a test of IntervalNameParser.  
 *
 * @author miros
 */
public class IntervalNameParserTest {

	/**
	 * Test some valid inputs
	 */
	@Test
	public void testGuessMethodWithValidParams() {
		assertEquals(60, IntervalNameParser.guessLengthFromName("60s"));
		assertEquals(30, IntervalNameParser.guessLengthFromName("30s"));
		assertEquals(60, IntervalNameParser.guessLengthFromName("1m"));
		assertEquals(300, IntervalNameParser.guessLengthFromName("5m"));
		assertEquals(7200, IntervalNameParser.guessLengthFromName("2h"));
		assertEquals(86400, IntervalNameParser.guessLengthFromName("1D"));
	}
	
	/**
	 * Test some invalid inputs
	 */
	@Test public void testGuessMethodWithInvalidParams() {
		try {
			IntervalNameParser.guessLengthFromName("60");
			fail("This was not a valid value.");
		} catch (UnknownIntervalLengthException e) {
			// ok, we expect that
		}
		try {
			IntervalNameParser.guessLengthFromName("asd");
			fail("This was not a valid value.");
		} catch (UnknownIntervalLengthException e) {
			// ok, we expect that
		}
		try {
			IntervalNameParser.guessLengthFromName("9ms");
			fail("This was not a valid value.");
		} catch (UnknownIntervalLengthException e) {
			// ok, we expect that
		}
	}
}
