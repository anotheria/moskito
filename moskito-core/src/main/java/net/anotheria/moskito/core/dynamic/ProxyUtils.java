package net.anotheria.moskito.core.dynamic;

import net.anotheria.moskito.core.predefined.ServiceStatsCallHandler;
import net.anotheria.moskito.core.predefined.ServiceStatsFactory;
import net.anotheria.moskito.core.producers.IStats;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This utility class helps to create moskitoinvocation proxies. All you need is to submit your implementation and the interfaces which should be supported. In all methods the first interface is mandatory and 
 * defines the name of the proxy if the name parameter is omitted (or null).
 * @author lrosenberg
 *
 */
public class ProxyUtils {

	/**
	 * Hide constructor.
	 */
	private ProxyUtils(){

	}

	/**
	 * Internal storage for instance counters.
	 */
	private static final ConcurrentMap<String,AtomicInteger> instanceCounters = new ConcurrentHashMap<>();
	/**
	 * Creates a new proxied instance for an existing implementation.
	 * @param <T> interface type.
	 * @param impl the implementation of the interface.
	 * @param name name of the producer.
	 * @param category category of the producer, i.e. service, dao, api, controller.
	 * @param subsystem subsystem of the producer, i.e. messaging, payment, registration, shop.
	 * @param handler handler for the calls.
	 * @param statsFactory the factory for the stats.
	 * @param interf interfaces.
	 * @return a newly created proxy of type T.
	 */
	public static <T> T createInstance(T impl, String name, String category, String subsystem, IOnDemandCallHandler handler, IOnDemandStatsFactory<? extends IStats> statsFactory, Class<T> interf, Class<?>... additionalInterfaces){
		if (name==null)
			name = extractName(interf);

		Class<?>[] interfacesParameter = mergeInterfaces(interf, additionalInterfaces);
		@SuppressWarnings ("unchecked")
		final MoskitoInvokationProxy proxy = new MoskitoInvokationProxy(
				impl,
				handler,
				statsFactory,
				name+ '-' +getInstanceCounter(name),
				category,
				subsystem,
				interfacesParameter
			);
		
		@SuppressWarnings("unchecked")
		T ret = (T) proxy.createProxy();
		return ret;
	}

	/**
	 * Creates an array consisting of an interface class (first parameter) and optionally some more additional interface
	 * classes.
	 *
	 * @param interf The interface that will be positioned as the first element of the array
	 * @param additionalInterfaces Optional additional interfaces, that will be appended to the array
     * @return an array containing the interface and some more interfaces if given
     */
	static Class<?>[] mergeInterfaces(Class<?> interf, Class<?>... additionalInterfaces) {
		Class<?>[] interfacesParameter = new Class<?>[additionalInterfaces==null ? 1 : 1 + additionalInterfaces.length];
		interfacesParameter[0] = interf;
		if (additionalInterfaces!=null){
			System.arraycopy(additionalInterfaces, 0, interfacesParameter, 1, additionalInterfaces.length);
		}
		return interfacesParameter;
	}

	/**
	 * Creates a monitored proxy instance for a service. Service in this context means, that the ServiceStatsCallHandler and ServiceStatsFactory are used.
	 * @param <T> the server interface.
	 * @param impl the implementation of T.
	 * @param name name for this instance.
	 * @param category category of this instance.
	 * @param subsystem subsystem of this instance.
	 * @param interf class of T, main interface of the service.
	 * @param additionalInterfaces additional helper interfaces, that should be supported as well.
	 * @return a newly created proxy of type T.
	 */
	public static <T> T createServiceInstance(T impl, String name, String category, String subsystem, Class<T> interf, Class<?>... additionalInterfaces){
		return createInstance(impl, 
				name, 
				category, 
				subsystem, 
				new ServiceStatsCallHandler(),
				new ServiceStatsFactory(),
				interf,
				additionalInterfaces);
	}
	
	
 	/**
 	 * Shortcut method to create service instance. Creates an instance with service interface name as instance name, custom category and subsystem, ServiceStatsCallHandler and ServiceStatsFactory.
 	 * @param <T>
 	 * @param impl
 	 * @param category
 	 * @param subsystem
 	 * @param interf
 	 * @param additionalInterfaces
	 * @return a newly created proxy of type T.
 	 */
	public static <T> T createServiceInstance(T impl, String category, String subsystem, Class<T> interf, Class<?>... additionalInterfaces){
		return createServiceInstance(impl, null, category, subsystem, interf, additionalInterfaces);
	}
	
	/**
	 * Shortcut method to create service instance with least possible effort. Creates an instance with service interface name as instance name, category service, ServiceStatsCallHandler and ServiceStatsFactory.  
	 * @param <T> service interface.
	 * @param impl implementation of T.
	 * @param subsystem subsystem of the service.
	 * @param interf Class of T.
	 * @param additionalInterfaces Additional interfaces if applicable.
	 * @return a newly created proxy of type T.
	 */
	public static <T> T createServiceInstance(T impl, String subsystem, Class<T> interf, Class<?>... additionalInterfaces){
		return createServiceInstance(impl, null, "service", subsystem, interf, additionalInterfaces);
	}

	/**
	 * Shortcut method to create an instance of category dao.
	 * @param impl implementation of T.
	 * @param subsystem subsystem of the dao.
	 * @param interf Class of T.
	 * @param additionalInterfaces additional interfaces if applicable.
	 * @param <T> main dao interface.
	 * @return a newly created proxy of type T.
	 */
	public static <T> T createDAOInstance(T impl, String subsystem, Class<T> interf, Class<?>... additionalInterfaces){
		return createServiceInstance(impl, null, "dao", subsystem, interf, additionalInterfaces);
	}

	/**
	 * Returns a new counter for instance with core-name 'name'. We support multiple producers with same name by adding a minus <number> to the name, like Service, Service-1 etc.
	 * @param name proposed name of the instance.
	 * @return instance count for known instances with name 'name'.
	 */
	private static int getInstanceCounter(String name){
		AtomicInteger counter = instanceCounters.get(name);
		if (counter!=null)
			return counter.incrementAndGet();

		counter = new AtomicInteger(0);
		AtomicInteger old = instanceCounters.putIfAbsent(name, counter);
		if (old!=null)
			counter = old;
		return counter.incrementAndGet();
	}

	/**
	 * Internal method which extracts name of the service out of it class. net.java.dev.moskito.core.dynamic.ProxyUtils -> ProxyUtils.
	 * @param clazz the target clazz..
	 * @return
	 */
	private static String extractName(Class<?> clazz){
		String name = clazz.getName();
		if (name==null)
			name = "";
		int indexOfDot = name.lastIndexOf('.');
		return indexOfDot == -1 ? name : name.substring(indexOfDot+1);
	}
}
