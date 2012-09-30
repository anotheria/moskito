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
package net.anotheria.moskito.core.registry;

import net.anotheria.moskito.core.util.StartBuiltInProducers;

/**
 * This class is solely for a) compatibility reasons with older version and b) decoupling IProducerRegistry and ProducerRegistryImpl.
 * @author lrosenberg
 */
public class ProducerRegistryFactory {
		
	/**
	 * The instance of the producer registry impl.
	 */
	private static final ProducerRegistryImpl instance = new ProducerRegistryImpl();
	
	static{
		init();
	}

	/**
	 * Initializes the registry. If we are not in junit mode, start built-in producers.
	 */
	private static void init(){
		String junittest = System.getProperty("JUNITTEST");
		if (junittest!=null && (junittest.equalsIgnoreCase("true")))
			return;
		StartBuiltInProducers.startbuiltin();
	}
	
	/**
	 * Returns the IProducerRegistry singleton instance.
	 * @return
	 */
	public static final IProducerRegistry getProducerRegistryInstance(){
		return instance;
	}
	
	/**
	 * Resets the registry, useable for unit tests. Note this method doesn't stop producers from running (if the are running) hence it shouldn't be used in production environment.
	 */
	public static final void reset(){
		instance.reset();
		String junittest = System.getProperty("JUNITTEST");
		if (junittest!=null && (junittest.equalsIgnoreCase("true")))
			return;
		StartBuiltInProducers.restartbuiltin();
	}
}
