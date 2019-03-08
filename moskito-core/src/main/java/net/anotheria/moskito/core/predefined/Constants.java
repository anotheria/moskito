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
package net.anotheria.moskito.core.predefined;

import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.stats.Interval;

/**
 * Constants used through moskito.
 * @author another
 *
 */
public class Constants {
	
	/**
	 * Min time value.
	 */
	public static final long MIN_TIME_DEFAULT = Long.MAX_VALUE;
	/**
	 * Max time value.
	 */
	public static final long MAX_TIME_DEFAULT = Long.MIN_VALUE;

	/**
	 * an interval of one minute
	 */
	public static final String INTERVAL_ONE_MINUTE = "1m";
	/**
	 * an interval of five minutes
	 */
	public static final String INTERVAL_FIVE_MINUTES = "5m";
	/**
	 * an interval of fifteen minutes
	 */
	public static final String INTERVAL_FIFTEEN_MINUTES = "15m";
	/**
	 * an interval of one hour
	 */
	public static final String INTERVAL_ONE_HOUR = "1h";
	/**
	 * an interval of one hour
	 */
	public static final String INTERVAL_TWELVE_HOURS = "12h";
	/**
	 * an interval of one day
	 */
	public static final String INTERVAL_ONE_DAY = "1d";


	/**
	 * Average time value.
	 */
	public static final long AVERAGE_TIME_DEFAULT = -1;

	/**
	 * Returns currently configured intervals. Although this is not a constant anymore, since the intervals can be
	 * configured since 2.9.1 we still leave this method here, because it is used at far too many locations.
	 * However, the intervals are looked up in the configuration now.
	 * @return currently configured intervals.
	 */
	public static final Interval[] getDefaultIntervals(){
		return MoskitoConfigurationHolder.getConfiguration().getConfiguredIntervals();
	}

	/**
	 * All intervals with this prefix are considered as snapshot intervals, meaning that they are not updated automatically.
	 */
	public static final String PREFIX_SNAPSHOT_INTERVAL = "snapshot";




}
