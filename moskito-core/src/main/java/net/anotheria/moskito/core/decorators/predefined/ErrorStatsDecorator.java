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
package net.anotheria.moskito.core.decorators.predefined;

import net.anotheria.moskito.core.decorators.AbstractDecorator;
import net.anotheria.moskito.core.decorators.value.LongValueAO;
import net.anotheria.moskito.core.decorators.value.StatValueAO;
import net.anotheria.moskito.core.predefined.ErrorStats;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.stats.TimeUnit;

import java.util.ArrayList;
import java.util.List;

/**
 * Decorator for error stats.
 */
public class ErrorStatsDecorator extends AbstractDecorator {
	/**
	 * Captions.
	 */
	private static final String CAPTIONS[] = {
		"Initial",
		"Total",
		"Max initial per Minute",
		"Max total per Minute"
	};

	/**
	 * Short explanations (mouse over).
	 */
	private static final String SHORT_EXPLANATIONS[] = {
		"Number of initial occurrences",
		"Number of total occurrences",
		"Max number of initial occurrences per minute reached",
		"Max number of total occurrences per minute reached"

	};

	/**
	 * Long explanations - separate page.
	 */
	private static final String EXPLANATIONS[] = {
		"Number of occurrences of this Exception/Error/Throwable object when it was initial (first) error in the processing",
		"Number of total occurrences of this Exception/Error/Throwable object, regardless if it was initial or followup error. It also includes initial errors.",
		"Maximal number of occurrences of this Exception/Error/Throwable object when it was initial (first) error in the processing that was reached in a single minute.",
		"Maximal number of total occurrences of this Exception/Error/Throwable object, regardless if it was initial or followup error. It also includes initial errors. This value is max value reached per minute"

	};

	/**
	 * Constructor.
	 */
	public ErrorStatsDecorator(){
		super("Errors", CAPTIONS, SHORT_EXPLANATIONS, EXPLANATIONS);
	}
	

	@Override public List<StatValueAO> getValues(IStats statsObject, String interval, TimeUnit unit) {
		ErrorStats stats = (ErrorStats)statsObject;
		List<StatValueAO> ret = new ArrayList<StatValueAO>(CAPTIONS.length);
		int i = 0;
		ret.add(new LongValueAO(CAPTIONS[i++], stats.getInitial(interval)));
		ret.add(new LongValueAO(CAPTIONS[i++], stats.getTotal(interval)));
		ret.add(new LongValueAO(CAPTIONS[i++], mapToLong(stats.getMaxInitial(interval))));
		ret.add(new LongValueAO(CAPTIONS[i++], mapToLong(stats.getMaxTotal(interval))));
		return ret;
	}

	private static final long mapToLong(int value){
		return (value == Integer.MAX_VALUE) ? Long.MAX_VALUE :
				(value == Integer.MIN_VALUE) ? Long.MIN_VALUE :
						value;
	}


}
