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
package net.anotheria.moskito.webui.decorators.util;

import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.util.storage.StorageStats;
import net.anotheria.moskito.webui.shared.bean.DoubleValueBean;
import net.anotheria.moskito.webui.shared.bean.LongValueBean;
import net.anotheria.moskito.webui.shared.bean.StatValueBean;
import net.anotheria.moskito.webui.decorators.AbstractDecorator;

import java.util.ArrayList;
import java.util.List;

/**
 * Decorator for storage stats.
 * @author lrosenberg
 *
 */
public class StorageStatsDecorator extends AbstractDecorator {
	/**
	 * Captions.
	 */
	private static final String CAPTIONS[] = {
		"Get",
		"mGet",
		"mGet_R",
		"hGet_R",
		
		"Put",
		"owPut",
		"owPut_R",
		"newPut_R",
		
		"Rem",
		"no_Rem",
		"no_Rem_R",
		
		"Put/Get_R",
		"Put/Rem_R",
		"Size",

		"cKeyC",
		"cKeyH",
		"cKeyHR",

		"cValC",
		"cValH",
		"cValHR",
	};
	
	/**
	 * Short explanations.
	 */
	private static final String SHORT_EXPLANATIONS[] = {
		"Number of get calls",
		"Missed get calls",
		"Missed get calls ratio",
		"Hit get calls ratio",
		"Number of put calls",
		"Puts which overwrite",
		"Overwrite put ratio",
		"New put ratio",
		"Number of remove calls",
		"No effect removes",
		"No effect removes ratio",
		
		"Put/Get Ratio",
		"Put/Remove Ratio",
		"Size",

		"Calls to containsKey",
		"Hits to containsKey ",
		"containsKey hit ratio",

		"Calls to containsValue",
		"Hits to containsValue",
		"containsValue hit ratio",
	};


	/**
	 * Long explanations.
	 */
	private static final String EXPLANATIONS[] = {
		"Total number of calls to the method V get(K).",
		"Total number of calls to the method V get(K) which returned null as result",
		"The ratio of missed get calls (calls that returned null) to total get calls. For most (but not all) usecases a high value (>0.5) means, that your data retrieval strategy is inefficent. ",
		"The ratio of hit get calls (hit get calls are those, which returned a not null object, as opposite to the missed get calls). The equation mGet_R+hGet R=1 should always be true.", 
		
		"Total number of calls to the method V put(K, V).",
		"Number of put calls which actually overwrite data in the storage.",
		"Ratio of overwriting put calls to total put calls. Gives you the feeling how much of the data is actually being replaced.",
		"Ratio of put calls which are puting new data into the storage to total put calls. Gives you the feeling how much of the data is actually being placed in the storage. Note that 'owPut R'+'newPut R' should be always 1.",

		"Total number of calls to V remove(K) method",
		"Number of calls to the remove method which have no effect because there was no object under the given key.",
		"Ratio between no-effect-removes and removes. If this value is too high (max is 1.0) you are trying to remove non-existing objects way too often, and should have a look at your algorithms.",
		
		"Put/Get ratio. If this value is above 1.0 you are probably doing something wrong.",
		"Put/Remove ratio. If this value is to high and owPut R is low your application is probably leaking memory.",
		"The size of the storage (number of contained elements).",

		"Total number of calls to containsKey method. ",
		"Total number of calls to containsKey method, which returned true.",
		"Number of calls to containsKey method which returned true / total number of calls. If you are using the storage as a cache you will want this value high (1.0 is max).",

		"Total number of calls to containsValue method. Since contains value method is very time intensive, you shouldn't use it too often.",
		"Total number of calls to containsValue method, which returned true.",
		"Number of calls to containsValue method which returned true / total. As stated above, the containsValue method is very time intensive (at least linear to the number of items in the undelying map implementation), so having using it with low hit rate is a performance killer. ",
	};

	
	/**
	 * Creates a new StorageStatsDecorator.
	 */
	public StorageStatsDecorator(){
		super("Storage", CAPTIONS, SHORT_EXPLANATIONS, EXPLANATIONS);
	}
	

	@Override public List<StatValueBean> getValues(IStats statsObject, String interval, TimeUnit unit) {
		StorageStats stats = (StorageStats)statsObject;
		List<StatValueBean> ret = new ArrayList<StatValueBean>(CAPTIONS.length);
		
		int i = 0;
		
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getGets(interval)));
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getMissedGets(interval)));
		ret.add(new DoubleValueBean(CAPTIONS[i++], stats.getMissedGetRatio(interval)));
		ret.add(new DoubleValueBean(CAPTIONS[i++], stats.getHitGetRatio(interval)));

		ret.add(new LongValueBean(CAPTIONS[i++], stats.getPuts(interval)));
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getOverwritePuts(interval)));
		ret.add(new DoubleValueBean(CAPTIONS[i++], stats.getOverwritePutRatio(interval)));
		ret.add(new DoubleValueBean(CAPTIONS[i++], stats.getNewPutRatio(interval)));

		ret.add(new LongValueBean(CAPTIONS[i++], stats.getRemoves(interval)));
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getNoopRemoves(interval)));
		ret.add(new DoubleValueBean(CAPTIONS[i++], stats.getNoopRemoveRatio(interval)));
		
		ret.add(new DoubleValueBean(CAPTIONS[i++], stats.getPutGetRatio(interval)));
		ret.add(new DoubleValueBean(CAPTIONS[i++], stats.getPutRemoveRatio(interval)));
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getSize(interval)));
		
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getContainsKeyCalls(interval)));
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getContainsKeyHits(interval)));
		ret.add(new DoubleValueBean(CAPTIONS[i++], stats.getContainsKeyHitRatio(interval)));
		
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getContainsValueCalls(interval)));
		ret.add(new LongValueBean(CAPTIONS[i++], stats.getContainsValueHits(interval)));
		ret.add(new DoubleValueBean(CAPTIONS[i++], stats.getContainsValueHitRatio(interval)));
		
		return ret;
	}
}
