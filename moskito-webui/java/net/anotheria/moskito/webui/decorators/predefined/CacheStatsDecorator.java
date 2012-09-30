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
package net.anotheria.moskito.webui.decorators.predefined;

import net.anotheria.moskito.core.predefined.CacheStats;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.webui.bean.DoubleValueBean;
import net.anotheria.moskito.webui.bean.LongValueBean;
import net.anotheria.moskito.webui.bean.StatValueBean;
import net.anotheria.moskito.webui.decorators.AbstractDecorator;

import java.util.ArrayList;
import java.util.List;

public class CacheStatsDecorator extends AbstractDecorator {
	/**
	 * Captions.
	 */
	private static final String CAPTIONS[] = {
		"Req",
		"Hit",
		"HR",
		"WR",
		"GC",
		"RO",
		"EX",
		"FI",
		"FR"
	};
	
	/**
	 * Short explanations (mouse over).
	 */
	private static final String SHORT_EXPLANATIONS[] = {
		"Number of requests",
		"Number of hits",
		"Hit ratio",
		"Number of writes ",
		"Number of garbage collected items",
		"Number of rolled over items",
		"Number of expired items",
		"Number of filtered items",
		"Fill ratio"
	};

	/**
	 * Long explanations - separate page.
	 */
	private static final String EXPLANATIONS[] = {
		"Total number of retrieval requests",
		"Total number of hits among the requests",
		"Hit ratio (hits / requests). You want it as high as possible. 1.0 is max.",
		"Total number of writes (number of items put into the cache)",
		"Number of garbage collected items (only if the cache implementation uses SoftReferences)",
		"Number of rolled over items. Rollover is implementation specific, but for most caches with fixed sizes it will happen.",
		"Number of expired items (only if the cache supports expiration).",
		"Number of filtered items (only if the cache supports filtering).",
		"Fill ratio, how much storage place is actually used (if implemented by the cache :-))."
	};

	/**
	 * Constructor.
	 */
	public CacheStatsDecorator(){
		super("Cache", CAPTIONS, SHORT_EXPLANATIONS, EXPLANATIONS);
	}
	

	@Override public List<StatValueBean> getValues(IStats statsObject, String interval, TimeUnit unit) {
		CacheStats stats = (CacheStats)statsObject;
		List<StatValueBean> ret = new ArrayList<StatValueBean>(CAPTIONS.length);
		int i = 0;
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getRequests(interval)));
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getHits(interval)));
		ret.add(new DoubleValueBean(CAPTIONS[i++], stats.getHitRatio(interval)));
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getWrites(interval)));
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getGarbageCollected(interval)));
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getRolloverCount(interval)));
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getExpired(interval)));
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getFiltered(interval)));
		ret.add(new DoubleValueBean(CAPTIONS[i++], stats.getFillRatio(interval)));
		return ret;
	}

}
