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
package net.anotheria.moskito.webui.decorators;

import net.anotheria.moskito.core.counter.CounterStats;
import net.anotheria.moskito.core.counter.GuestBasicPremiumStats;
import net.anotheria.moskito.core.counter.MaleFemaleStats;
import net.anotheria.moskito.core.predefined.ActionStats;
import net.anotheria.moskito.core.predefined.CacheStats;
import net.anotheria.moskito.core.predefined.FilterStats;
import net.anotheria.moskito.core.predefined.BrowserStats;
import net.anotheria.moskito.core.predefined.MemoryPoolStats;
import net.anotheria.moskito.core.predefined.MemoryStats;
import net.anotheria.moskito.core.predefined.OSStats;
import net.anotheria.moskito.core.predefined.RuntimeStats;
import net.anotheria.moskito.core.predefined.ServiceStats;
import net.anotheria.moskito.core.predefined.ServletStats;
import net.anotheria.moskito.core.predefined.ThreadCountStats;
import net.anotheria.moskito.core.predefined.ThreadStateStats;
import net.anotheria.moskito.core.predefined.VirtualMemoryPoolStats;
import net.anotheria.moskito.core.producers.AbstractStats;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.util.storage.StorageStats;
import net.anotheria.moskito.web.session.SessionCountStats;
import net.anotheria.moskito.webui.decorators.counter.CounterStatsDecorator;
import net.anotheria.moskito.webui.decorators.counter.GuestBasicPremiumStatsDecorator;
import net.anotheria.moskito.webui.decorators.counter.MaleFemaleStatsDecorator;
import net.anotheria.moskito.webui.decorators.predefined.ActionStatsDecorator;
import net.anotheria.moskito.webui.decorators.predefined.BrowserStatsDecorator;
import net.anotheria.moskito.webui.decorators.predefined.CacheStatsDecorator;
import net.anotheria.moskito.webui.decorators.predefined.FilterStatsDecorator;
import net.anotheria.moskito.webui.decorators.predefined.GenericStatsDecorator;
import net.anotheria.moskito.webui.decorators.predefined.MemoryPoolStatsDecorator;
import net.anotheria.moskito.webui.decorators.predefined.MemoryStatsDecorator;
import net.anotheria.moskito.webui.decorators.predefined.OSStatsDecorator;
import net.anotheria.moskito.webui.decorators.predefined.RuntimeStatsDecorator;
import net.anotheria.moskito.webui.decorators.predefined.ServiceStatsDecorator;
import net.anotheria.moskito.webui.decorators.predefined.ServletStatsDecorator;
import net.anotheria.moskito.webui.decorators.predefined.SessionCountDecorator;
import net.anotheria.moskito.webui.decorators.predefined.ThreadCountDecorator;
import net.anotheria.moskito.webui.decorators.predefined.ThreadStatesDecorator;
import net.anotheria.moskito.webui.decorators.util.StorageStatsDecorator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Implementation of the IDecoratorRegistry.
 * @author lrosenberg
 */
public class DecoratorRegistryImpl implements IDecoratorRegistry{

	/**
	 * Internal decorator map.
	 */
	private Map<String,IDecorator> registry;
	
	@Override public IDecorator getDecorator(IStats stats) {
		return getDecorator(stats.getClass().getName());
	}

	@Override public IDecorator getDecorator(Class<? extends IStats> statsClazz) {
		return getDecorator(statsClazz.getName());
	}

	@Override public IDecorator getDecorator(String className) {
		IDecorator specificDecorator = registry.get(className);

		if (specificDecorator == null) {
			specificDecorator = new GenericStatsDecorator(className);
			addDecorator(className, specificDecorator);
		}

		return specificDecorator;
	}

	@Override public List<IDecorator> getDecorators(){
		List<IDecorator> ret = new ArrayList<IDecorator>();
		ret.addAll(registry.values());
		return ret;
	}

	DecoratorRegistryImpl(){
		registry = new ConcurrentHashMap<String, IDecorator>();
		configure();
	}
	
	//leon: replace this hard-wired-method with a property or xml config one day
	private void configure(){
		addDecorator(ServiceStats.class, new ServiceStatsDecorator());
		addDecorator(ActionStats.class, new ActionStatsDecorator());
		addDecorator(ServletStats.class, new ServletStatsDecorator());
		addDecorator(FilterStats.class, new FilterStatsDecorator());
		addDecorator(CacheStats.class, new CacheStatsDecorator());
		addDecorator(StorageStats.class, new StorageStatsDecorator());
		addDecorator(MemoryStats.class, new MemoryStatsDecorator());
		addDecorator(MemoryPoolStats.class, new MemoryPoolStatsDecorator());
		addDecorator(VirtualMemoryPoolStats.class, new MemoryPoolStatsDecorator("VirtualMemoryPool"));
		addDecorator(SessionCountStats.class, new SessionCountDecorator());
		addDecorator(ThreadCountStats.class, new ThreadCountDecorator());
		addDecorator(ThreadStateStats.class, new ThreadStatesDecorator());
		addDecorator(OSStats.class, new OSStatsDecorator());
		addDecorator(RuntimeStats.class, new RuntimeStatsDecorator());
		addDecorator(BrowserStats.class, new BrowserStatsDecorator());

		//counters
		addDecorator(CounterStats.class, new CounterStatsDecorator());
		addDecorator(MaleFemaleStats.class, new MaleFemaleStatsDecorator());
		addDecorator(GuestBasicPremiumStats.class, new GuestBasicPremiumStatsDecorator());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public void addDecorator(Class <? extends AbstractStats> clazz, IDecorator decorator) {
		addDecorator(clazz.getName(), decorator);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void addDecorator(String clazzName, IDecorator decorator) {
		registry.put(clazzName, decorator);
	}
}
