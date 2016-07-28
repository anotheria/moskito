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
package net.anotheria.moskito.core.dynamic;

import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.Collection;

/**
 * This is an InvocationHandler which is used whenever you want to monitor an impementation of an interface in a AOP way. Using this proxy you can inject monitoring to any
 * code by just 'surrounding' the impl with measuring proxy.
 * <pre>
 * For example if you have an 
 * public interface IImagesService {
 * 	List<Image> getImages() throws ImagesServiceException;
 * }
 * and an implementation of it : ImageServiceImpl, with whatever implementation details, and you want to monitor all calls to this impl, and you typically have an ImageServiceFactory
 * all you need to do is (example code):
 * public class ImagesServiceFactory{
 *
 *	private static AtomicInteger instanceCounter = new AtomicInteger(0);
 *
 *	public static IImagesService createImagesService(){
 *		MoskitoInvokationProxy proxy = new MoskitoInvokationProxy(
 *			createInstance(),
 *			new ServiceStatsCallHandler(),
 *			new ServiceStatsFactory(),
 *			"IImagesService-"+instanceCounter.incrementAndGet(),
 *			"service",
 *			"asg",
 *	 		IImagesService.class, ASGService.class
 *		);
 *		return (IImagesService) proxy.createProxy();
 *	}
 *
 *	private static IImagesService createInstance(){
 *		return ImagesServiceImpl.getInstance();
 *	}
 *  
 * </pre>
 * @author lrosenberg
 *
 */
public class MoskitoInvokationProxy<S extends IStats> implements InvocationHandler{
	/**
	 * Supported interfaces.
	 */
	private Class<?>[] supportedInterfaces;
	/**
	 * Exceptions that are expected to be thrown.
	 */
	private Class<?>[] declaredExceptions;
	
	/**
	 * The implementation which is proxied.
	 */
	private Object implementation;
	
	/**
	 * A handler object for the call execution.
	 */
	private IOnDemandCallHandler handler;
	
	/**
	 * Producer for the stats which is learning the methods on the fly.
	 */
	private OnDemandStatsProducer<S> producer;
	
	/**
	 * Creates a new MoskitoInvocationProxy with given implementation, handler and stats factory, and interfaces to monitor.
	 * @param anImplementation Implementation object for all interfaces, i.e. MyServiceImpl 
	 * @param aHandler  the call handler, i.e. net.java.dev.moskito.core.predefined.ServiceStatsCallHandler
	 * @param factory the factory for the stats, i.e. net.java.dev.moskito.core.predefined.ServiceStatsFactory
	 * @param producerId the id of the producer (for ui) i.e. MyService or MyService-1 ...
	 * @param category the key/name/id for the monitored category i.e. service,api,controller,persistence
	 * @param subsystem the key/name/id for the monitored i.e. user,shop,messaging
	 * @param interfaces interfaces which can be called from outside, i.e. MyService
	 */
	public MoskitoInvokationProxy(Object anImplementation, IOnDemandCallHandler aHandler, IOnDemandStatsFactory<S> factory, String producerId, String category, String subsystem, Class<?>... interfaces ){
		if (interfaces.length==0)
			throw new RuntimeException("No interfaces specified!");
		implementation = anImplementation;
		supportedInterfaces = interfaces;
		
		handler = aHandler;
		producer = new OnDemandStatsProducer<>(producerId, category, subsystem, factory);
		producer.setTracingSupported(true);
		
		ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(producer);
		
		guessExceptions();
	}

	/**
	 * Creates a new MoskitoInvokationProxy.
	 * @param anImplementation object for monitoring. Should implement the supplied interfaces.
	 * @param aHandler a handler.
	 * @param factory factory for stats objects.
	 * @param interfaces multiple interfaces that should be supported.
	 */
	public MoskitoInvokationProxy(Object anImplementation, IOnDemandCallHandler aHandler, IOnDemandStatsFactory factory, Class<?>... interfaces ){
		this(anImplementation, aHandler, factory, guessProducerId(anImplementation), null, null, interfaces);
	}

	/**
	 * Creates a new MoskitoInvokationProxy.
	 * @param anImplementation object for monitoring. Should implement the supplied interfaces.
	 * @param aHandler a handler.
	 * @param factory factory for stats objects.
	 * @param category category for presentation.
	 * @param subsystem subsystem for presentation.
	 * @param interfaces multiple interfaces that should be supported.
	 */
	public MoskitoInvokationProxy(Object anImplementation, IOnDemandCallHandler aHandler, IOnDemandStatsFactory factory, String category, String subsystem, Class<?>... interfaces) {
		this(anImplementation, aHandler, factory, guessProducerId(anImplementation), category, subsystem, interfaces);
	}
	
	private static String guessProducerId(Object implementation){
		String className = implementation.getClass().getName();
		return className.substring(className.lastIndexOf('.')+1);
	}
	
	/**
	 * Looks up all possible exceptions.
	 */
	private void guessExceptions(){
		Collection<Class<?>> tmpExceptionList = new ArrayList<>();
		for (Class<?> c:supportedInterfaces){
			Method[] methods = c.getDeclaredMethods();
			for (Method m:methods){
				for (Class<?> exc : m.getExceptionTypes()){
					if (!tmpExceptionList.contains(exc))
						tmpExceptionList.add(exc);
				}
			}
		}
		declaredExceptions = new Class[tmpExceptionList.size()];
		tmpExceptionList.toArray(declaredExceptions);
	}
	
  	@Override public Object invoke(Object aProxy, Method aMethod, Object[] args) throws Throwable {
  		Class<?> methodsClass = aMethod.getDeclaringClass();
  		for (Class<?> c : supportedInterfaces){
  			if (c.equals(methodsClass)){
  				return handler.invoke(implementation, args, aMethod, c, declaredExceptions, producer.getDefaultStats(), producer.getStats(aMethod.getName()), producer);
  			}
  		}
  		
  		return aMethod.invoke(this, args);
    }

  	/**
  	 * Creates the actual proxy object.
  	 * @return
  	 */
  	public Object createProxy(){
  		return createProxy(implementation.getClass().getClassLoader());
  	}
  	
  	/**
  	 * Creates the proxy with the submitted classloader.
  	 * @param classLoader
  	 * @return
  	 */
	public Object createProxy(ClassLoader classLoader){
		return Proxy.newProxyInstance(
				classLoader, 
				supportedInterfaces, 
				this);
	
	}
	
	/**
	 * Returns the internally used implementation of the interfaces.
	 * @return
	 */
	public Object getImplementation(){
		return implementation;
	}
	
	/**
	 * Returns the producer.
	 * @return
	 */
	public IStatsProducer getProducer(){
		return producer;
	}
}
