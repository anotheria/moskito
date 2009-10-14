/*
 * $Id$
 * $Author$
 *
 * This file is part of the MoSKito software project
 * that is hosted at http://moskito.java.net.
 * 
 * Copyright (c) 2006 by MoSKito Project
 *
 * All MoSKito files are under MIT License: 
 * http://www.opensource.org/licenses/mit-license.php
 */
package net.java.dev.moskito.core.stats;

import net.java.dev.moskito.core.stats.impl.IntervalRegistry;


/**
 * This utility class provides constants for the most important intervals.
 *
 * @author Miro
 */
public abstract class DefaultIntervals {

	/**
	 * an interval of one minute
	 */
	public static final Interval ONE_MINUTE = IntervalRegistry.getInstance().getInterval("1m");
	/**
	 * an interval of five minutes
	 */
	public static final Interval FIVE_MINUTES = IntervalRegistry.getInstance().getInterval("5m");
	/**
	 * an interval of fifteen minutes
	 */
	public static final Interval FIFTEEN_MINUTES = IntervalRegistry.getInstance().getInterval("15m");
	/**
	 * an interval of one hour
	 */
	public static final Interval ONE_HOUR = IntervalRegistry.getInstance().getInterval("1h");
	/**
	 * an interval of one hour
	 */
	public static final Interval TWELVE_HOURS = IntervalRegistry.getInstance().getInterval("12h");
	/**
	 * an interval of one day
	 */
	public static final Interval ONE_DAY = IntervalRegistry.getInstance().getInterval("1d");
	
	
}
