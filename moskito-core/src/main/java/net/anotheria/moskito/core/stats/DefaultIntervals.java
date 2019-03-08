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
package net.anotheria.moskito.core.stats;

import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.stats.impl.IntervalRegistry;


/**
 * This utility class provides constants for the most important intervals.
 *
 * @author Miro
 */
@Deprecated
public abstract class DefaultIntervals {

	/**
	 * an interval of one minute
	 */
	public static final Interval ONE_MINUTE = IntervalRegistry.getInstance().getInterval(Constants.INTERVAL_ONE_MINUTE);
	/**
	 * an interval of five minutes
	 */
	public static final Interval FIVE_MINUTES = IntervalRegistry.getInstance().getInterval(Constants.INTERVAL_FIVE_MINUTES);
	/**
	 * an interval of fifteen minutes
	 */
	public static final Interval FIFTEEN_MINUTES = IntervalRegistry.getInstance().getInterval(Constants.INTERVAL_FIFTEEN_MINUTES);
	/**
	 * an interval of one hour
	 */
	public static final Interval ONE_HOUR = IntervalRegistry.getInstance().getInterval(Constants.INTERVAL_ONE_HOUR);
	/**
	 * an interval of one hour
	 */
	public static final Interval TWELVE_HOURS = IntervalRegistry.getInstance().getInterval(Constants.INTERVAL_ONE_HOUR);
	/**
	 * an interval of one day
	 */
	public static final Interval ONE_DAY = IntervalRegistry.getInstance().getInterval(Constants.INTERVAL_ONE_DAY);



	
}
