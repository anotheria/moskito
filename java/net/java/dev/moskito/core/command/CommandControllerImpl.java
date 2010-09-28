/*
 * $Id $
 * 
 * This file is part of the MoSKito software project
 * that is hosted at http://moskito.dev.java.net.
 * 
 * All MoSKito files are distributed under MIT License:
 * 
 * Copyright (c) 2006-2007 The MoSKito Project Team.
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
package net.java.dev.moskito.core.command;

import java.util.HashMap;
import java.util.Map;

import net.java.dev.moskito.core.usecase.recorder.UseCaseRecorderCommandProcessor;

import org.apache.log4j.Logger;

/**
 * An implementation of the command controller.
 * @author lrosenberg
 *
 */
public enum CommandControllerImpl implements ICommandController{
	/**
	 * The singleton instance.
	 */
	INSTANCE;
	/**
	 * Map with registered processors.
	 */
	private Map<String, ICommandProcessor> processors;
	/**
	 * Logger instance.
	 */
	private static Logger log = Logger.getLogger(CommandControllerImpl.class);
	/**
	 * Creates a new CommandControllerImpl.
	 */
	private CommandControllerImpl() {
		processors = new HashMap<String, ICommandProcessor>();
		
		registerCommandProcessor("recordUseCase", new UseCaseRecorderCommandProcessor());
	}
	
	@Override public void registerCommandProcessor(String command, ICommandProcessor processor) {
		log.debug("registering processor: "+processor+" for command: "+command);
		ICommandProcessor oldProcessor = processors.put(command, processor);
		if (oldProcessor!=null)
			log.info("Implicitely unregistered processor: "+processor+" for command: "+command);
		
	}

	//note, the second parameter is unneeded and should be removed. 
	@Override public void unregisterCommandProcessor(String command, ICommandProcessor processor) {
		log.debug("unregistering processor: "+processor+" for command: "+command);
		ICommandProcessor oldProcessor = processors.remove(command);
		if (oldProcessor==null)
			log.info("Remove for command: "+command+" had no effect (no command previously registered)");
	}

	@Override public void startCommand(String command, Map<String, String[]> parameters) {
		log.debug("startCommand("+command+", "+parameters+")");
		ICommandProcessor processor = processors.get(command);
		if (processor==null)
			return;
		try{
			processor.startCommand(command, parameters);
		}catch(Exception e){
			log.error("caught in startCommand("+command+", "+parameters+")", e);
		}
	}

	@Override public void stopCommand(String command, Map<String, String[]> parameters) {
		log.debug("stopCommand("+command+", "+parameters+")");
		ICommandProcessor processor = processors.get(command);
		if (processor==null)
			return;
		try{
			processor.stopCommand(command, parameters);
		}catch(Exception e){
			log.error("caught in stopCommand("+command+", "+parameters+")", e);
		}
	}


}
