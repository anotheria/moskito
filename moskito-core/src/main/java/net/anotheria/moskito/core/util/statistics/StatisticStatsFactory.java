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
package net.anotheria.moskito.core.util.statistics;

import net.anotheria.moskito.core.decorators.DecoratorRegistryFactory;
import net.anotheria.moskito.core.decorators.IDecorator;
import net.anotheria.moskito.core.predefined.AbstractStatsFactory;
import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.predefined.ServiceStats;
import net.anotheria.moskito.core.stats.Interval;

/**
 * Factory that creates ServiceStats objects for on demand producers.
 *
 * @author lrosenberg
 */
public class StatisticStatsFactory extends AbstractStatsFactory<ServiceStats> {

	static{
		//reuse same decorator as ServiceStats.
		IDecorator d = DecoratorRegistryFactory.getDecoratorRegistry().getDecorator(ServiceStats.class);
		DecoratorRegistryFactory.getDecoratorRegistry().addDecorator(StatisticStats.class, d);
	}

	/**
	 * Creates a new factory with custom intervals.
	 *
	 * @param configuredIntervals
	 * 		{@link Interval} array
	 */
	public StatisticStatsFactory(final Interval[] configuredIntervals) {
		super(configuredIntervals == null || configuredIntervals.length <= 0 ? Constants.getDefaultIntervals() : configuredIntervals);
	}

	/**
	 * Creates a new factory with default intervals.
	 */
	public StatisticStatsFactory() {
		super();
	}

	@Override
	public StatisticStats createStatsObject(String name) {
		return new StatisticStats(name, getIntervals());
	}
}
