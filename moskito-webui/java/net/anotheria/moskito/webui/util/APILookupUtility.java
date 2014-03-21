package net.anotheria.moskito.webui.util;

import net.anotheria.anoplass.api.API;
import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.moskito.webui.accumulators.api.AccumulatorAPI;
import net.anotheria.moskito.webui.threshold.api.ThresholdAPI;
import org.distributeme.core.ServiceDescriptor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 21.03.14 17:41
 */
public class APILookupUtility {

	private static RemoteInstance currentRemoteInstance;

	private static ConcurrentMap<RemoteInstance, ConcurrentMap<Class<? extends API>, API>> remotes = new ConcurrentHashMap<RemoteInstance, ConcurrentMap<Class<? extends API>,API>>();

	private static boolean isLocal(){
		return WebUIConfig.getInstance().getConnectivityMode()==ConnectivityMode.LOCAL;
	}

	private static RemoteInstance getCurrentRemoteInstance(){
		if (currentRemoteInstance !=null)
			return currentRemoteInstance;
		RemoteInstance[] instances = WebUIConfig.getInstance().getRemotes();
		if (instances==null || instances.length<1)
			throw new IllegalStateException("Can't select first remote instance, but obviously remote usage is configured");
		currentRemoteInstance = instances[0];
		return currentRemoteInstance;
	}

	public static ThresholdAPI getThresholdAPI(){
		return isLocal() ?
			APIFinder.findAPI(ThresholdAPI.class) :
			findRemote(ThresholdAPI.class);//, RemoteThresholdAPIStub.class, ThresholdAPIConstants.getServiceId()));
	}


	public static AccumulatorAPI getAccumulatorAPI() {
		return isLocal() ?
				APIFinder.findAPI(AccumulatorAPI.class) :
				findRemote(AccumulatorAPI.class);
	}

	private static <T extends API> T findRemote(Class<T> targetClass){
		String serviceId = null;
		try{
			Class constantsClass = Class.forName(targetClass.getPackage().getName()+".generated."+targetClass.getSimpleName()+"Constants");
			Method m = constantsClass.getMethod("getServiceId");
			serviceId = (String)m.invoke(null);
		}catch(ClassNotFoundException e){
			throw new AssertionError("Can not find supporting classes for "+targetClass, e);
		} catch (NoSuchMethodException e) {
			throw new AssertionError("Can not find supporting classes or methods for "+targetClass, e);
		} catch (InvocationTargetException e) {
			throw new AssertionError("Can not obtain service id "+targetClass, e);
		} catch (IllegalAccessException e) {
			throw new AssertionError("Can not obtain service id "+targetClass, e);
		}

		Class<? extends T> remoteStubClass = null;
		try{
			remoteStubClass = (Class<? extends T> )Class.forName(targetClass.getPackage().getName()+".generated.Remote"+targetClass.getSimpleName()+"Stub");
		}catch(ClassNotFoundException e){
			throw new AssertionError("Can not find supporting classes for "+targetClass);
		}

		return findRemote(targetClass, remoteStubClass, serviceId);
	}

	private static <T extends API> T findRemote(Class<T> targetClass, Class<? extends T> remoteStubClass, String serviceId){
		RemoteInstance ri = getCurrentRemoteInstance();
		ConcurrentMap<Class<? extends API>, API> stubsByInterface = remotes.get(ri);
		if (stubsByInterface==null){
			ConcurrentHashMap<Class<? extends API>, API> newStubsByInterface = new ConcurrentHashMap<Class<? extends API>, API>();
			ConcurrentMap old = remotes.putIfAbsent(ri, newStubsByInterface);
			stubsByInterface = old == null ? newStubsByInterface : old;
		}

		T stub = (T)stubsByInterface.get(targetClass);
		if (stub!=null)
			return stub;

		//ok, we didn't have an object, we have to create it.
		ServiceDescriptor sd = new ServiceDescriptor(ServiceDescriptor.Protocol.RMI, serviceId, "any", ri.getHost(), ri.getPort());
		try{
			Constructor<? extends T> c = remoteStubClass.getConstructor(ServiceDescriptor.class);
			T newAPI = c.newInstance(sd);
			stubsByInterface.putIfAbsent(targetClass, newAPI);
			return newAPI;
		}catch (NoSuchMethodException e) {
			throw new IllegalStateException("Constructor with ServiceDescriptor parameter not found in remote stub", e);
		} catch (InvocationTargetException e) {
			throw new IllegalStateException("Constructor with ServiceDescriptor parameter can not be invoked in remote stub", e);
		} catch (InstantiationException e) {
			throw new IllegalStateException("Constructor with ServiceDescriptor parameter can not be instantiated in remote stub", e);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException("Constructor with ServiceDescriptor parameter can not be accessed in remote stub", e);
		}


	}
}
