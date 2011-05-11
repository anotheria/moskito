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
package net.java.dev.moskito.webui.decorators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.java.dev.moskito.core.predefined.ActionStats;
import net.java.dev.moskito.core.predefined.CacheStats;
import net.java.dev.moskito.core.predefined.FilterStats;
import net.java.dev.moskito.core.predefined.MemoryPoolStats;
import net.java.dev.moskito.core.predefined.MemoryStats;
import net.java.dev.moskito.core.predefined.OSStats;
import net.java.dev.moskito.core.predefined.RuntimeStats;
import net.java.dev.moskito.core.predefined.ServiceStats;
import net.java.dev.moskito.core.predefined.ServletStats;
import net.java.dev.moskito.core.predefined.ThreadCountStats;
import net.java.dev.moskito.core.predefined.VirtualMemoryPoolStats;
import net.java.dev.moskito.core.producers.AbstractStats;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.util.storage.StorageStats;
import net.java.dev.moskito.web.session.SessionCountStats;
import net.java.dev.moskito.webui.decorators.predefined.ActionStatsDecorator;
import net.java.dev.moskito.webui.decorators.predefined.CacheStatsDecorator;
import net.java.dev.moskito.webui.decorators.predefined.FilterStatsDecorator;
import net.java.dev.moskito.webui.decorators.predefined.MemoryPoolStatsDecorator;
import net.java.dev.moskito.webui.decorators.predefined.MemoryStatsDecorator;
import net.java.dev.moskito.webui.decorators.predefined.OSStatsDecorator;
import net.java.dev.moskito.webui.decorators.predefined.RuntimeStatsDecorator;
import net.java.dev.moskito.webui.decorators.predefined.ServiceStatsDecorator;
import net.java.dev.moskito.webui.decorators.predefined.ServletStatsDecorator;
import net.java.dev.moskito.webui.decorators.predefined.SessionCountDecorator;
import net.java.dev.moskito.webui.decorators.predefined.ThreadCountDecorator;
import net.java.dev.moskito.webui.decorators.util.StorageStatsDecorator;

/**
 * Implementation of the IDecoratorRegistry.
 * @author lrosenberg
 */
public class DecoratorRegistryImpl implements IDecoratorRegistry{

	/**
	 * Internal decorator map.
	 */
	private Map<Class<? extends AbstractStats>,IDecorator> registry;
	/**
	 * Default decorator instance for missing decorators.
	 */
	private IDecorator defaultDecorator;
	
	@Override public IDecorator getDecorator(IStats stats) {
		IDecorator specificDecorator = registry.get(stats.getClass());
		return specificDecorator == null ? defaultDecorator : specificDecorator;
	}
	
	@Override public List<IDecorator> getDecorators(){
		List<IDecorator> ret = new ArrayList<IDecorator>();
		ret.addAll(registry.values());
		return ret;
	}

	DecoratorRegistryImpl(){
		registry = new HashMap<Class<? extends AbstractStats>,IDecorator>();
		configure();
	}
	
	//leon: replace this hard-wired-method with a property or xml config one day
	private void configure(){
		defaultDecorator = new DefaultDecorator();
		registry.put(ServiceStats.class, new ServiceStatsDecorator());
		registry.put(ActionStats.class, new ActionStatsDecorator());
		registry.put(ServletStats.class, new ServletStatsDecorator());
		registry.put(FilterStats.class, new FilterStatsDecorator());
		registry.put(CacheStats.class, new CacheStatsDecorator());
		registry.put(StorageStats.class, new StorageStatsDecorator());
		registry.put(MemoryStats.class, new MemoryStatsDecorator());
		registry.put(MemoryPoolStats.class, new MemoryPoolStatsDecorator());
		registry.put(VirtualMemoryPoolStats.class, new MemoryPoolStatsDecorator("VirtualMemoryPool"));
		registry.put(SessionCountStats.class, new SessionCountDecorator());
		registry.put(ThreadCountStats.class, new ThreadCountDecorator());
		registry.put(OSStats.class, new OSStatsDecorator());
		registry.put(RuntimeStats.class, new RuntimeStatsDecorator());
	}
	
	@Override public void addDecorator(Class <? extends AbstractStats> clazz, IDecorator decorator){
		registry.put(clazz, decorator);
	}
	
}
