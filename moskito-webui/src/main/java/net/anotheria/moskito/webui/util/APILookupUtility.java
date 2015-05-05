package net.anotheria.moskito.webui.util;

import net.anotheria.anoplass.api.API;
import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.moskito.webui.MoSKitoWebUIContext;
import net.anotheria.moskito.webui.accumulators.api.AccumulatorAPI;
import net.anotheria.moskito.webui.dashboards.api.DashboardAPI;
import net.anotheria.moskito.webui.gauges.api.GaugeAPI;
import net.anotheria.moskito.webui.journey.api.JourneyAPI;
import net.anotheria.moskito.webui.producers.api.ProducerAPI;
import net.anotheria.moskito.webui.shared.api.AdditionalFunctionalityAPI;
import net.anotheria.moskito.webui.threads.api.ThreadAPI;
import net.anotheria.moskito.webui.threshold.api.ThresholdAPI;
import net.anotheria.moskito.webui.tracers.api.TracerAPI;
import org.distributeme.core.ServiceDescriptor;

import javax.servlet.http.HttpSession;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * This utility is used to separate between local apis and remote apis and provide remote instances.
 *
 * @author lrosenberg
 * @since 21.03.14 17:41
 */
public class APILookupUtility {

	/**
	 * Currently setup remote instance.
	 */
	private static RemoteInstance currentRemoteInstance;

	/**
	 * A map with all remote instance.
	 */
	private static ConcurrentMap<RemoteInstance, ConcurrentMap<Class<? extends API>, API>> remotes = new ConcurrentHashMap<RemoteInstance, ConcurrentMap<Class<? extends API>,API>>();

	/**
	 * Currently configured ConnectivityMode (local or remote).
	 */
	private static ConnectivityMode currentConnectivityMode = WebUIConfig.getInstance().getConnectivityMode();
	private static TracerAPI tracerAPI;

	public static boolean isLocal(){
		//if current usage mode is personal, every change made to the connectivity mode is global.
		if (WebUIConfig.getInstance().getUsageMode() == UsageMode.PERSONAL)
			return currentConnectivityMode == ConnectivityMode.LOCAL;
		HttpSession session = MoSKitoWebUIContext.getCallContext().getCurrentSession();
		//if session is null, which can't happen (hope so), we fall back to personal behaviour silently.
		if (session==null)
			return currentConnectivityMode == ConnectivityMode.LOCAL;
		ConnectivityMode mode = (ConnectivityMode)session.getAttribute(ConnectivityMode.class.getName());
		return mode == null ?  true : mode == ConnectivityMode.LOCAL ;

	}

	public static RemoteInstance getCurrentRemoteInstance(){

		if (WebUIConfig.getInstance().getUsageMode()==UsageMode.PERSONAL)
			return getCurrentRemoteInstanceForPersonalUsage();
		return getCurrentRemoteInstanceForSharedUsage();
	}

	private static RemoteInstance getCurrentRemoteInstanceForPersonalUsage(){
		if (currentRemoteInstance !=null)
			return currentRemoteInstance;
		RemoteInstance[] instances = WebUIConfig.getInstance().getRemotes();
		if (instances==null || instances.length<1)
			throw new IllegalStateException("Can't select first remote instance, but obviously remote usage is configured");
		currentRemoteInstance = instances[0];
		return currentRemoteInstance;

	}

	private static RemoteInstance getCurrentRemoteInstanceForSharedUsage(){
		RemoteInstance mySelectedInstance = (RemoteInstance)MoSKitoWebUIContext.getCallContext().getCurrentSession().getAttribute(RemoteInstance.class.getName());
		if (mySelectedInstance !=null)
			return mySelectedInstance;
		RemoteInstance[] instances = WebUIConfig.getInstance().getRemotes();
		if (instances==null || instances.length<1)
			throw new IllegalStateException("Can't select first remote instance, but obviously remote usage is configured");
		mySelectedInstance = instances[0];
		MoSKitoWebUIContext.getCallContext().getCurrentSession().setAttribute(RemoteInstance.class.getName(), mySelectedInstance);
		return mySelectedInstance;

	}

	public static JourneyAPI getJourneyAPI(){
		return isLocal() ?
				APIFinder.findAPI(JourneyAPI.class) :
				findRemote(JourneyAPI.class);
	}

	public static ThresholdAPI getThresholdAPI(){
		return isLocal() ?
			APIFinder.findAPI(ThresholdAPI.class) :
			findRemote(ThresholdAPI.class);
	}


	public static AccumulatorAPI getAccumulatorAPI() {
		return isLocal() ?
				APIFinder.findAPI(AccumulatorAPI.class) :
				findRemote(AccumulatorAPI.class);
	}

	public static ThreadAPI getThreadAPI() {
		return isLocal() ?
				APIFinder.findAPI(ThreadAPI.class) :
				findRemote(ThreadAPI.class);
	}

	public static ProducerAPI getProducerAPI() {
		return isLocal() ?
				APIFinder.findAPI(ProducerAPI.class) :
				findRemote(ProducerAPI.class);
	}

	public static AdditionalFunctionalityAPI getAdditionalFunctionalityAPI() {
		return isLocal() ?
				APIFinder.findAPI(AdditionalFunctionalityAPI.class) :
				findRemote(AdditionalFunctionalityAPI.class);
	}

	public static GaugeAPI getGaugeAPI() {
		return isLocal() ?
				APIFinder.findAPI(GaugeAPI.class) :
				findRemote(GaugeAPI.class);
	}

	public static DashboardAPI getDashboardAPI() {
		return isLocal() ?
				APIFinder.findAPI(DashboardAPI.class) :
				findRemote(DashboardAPI.class);
	}

	public static TracerAPI getTracerAPI() {
		return isLocal() ?
				APIFinder.findAPI(TracerAPI.class) :
				findRemote(TracerAPI.class);
	}




	private static <T extends API> T findRemote(Class<T> targetClass){
		String serviceId = null;
		try{
			Class constantsClass = Class.forName(targetClass.getPackage().getName()+".generated."+targetClass.getSimpleName()+"Constants");
			Method m = constantsClass.getMethod("getServiceId");
			serviceId = (String)m.invoke(null);
		}catch(ClassNotFoundException e){
			throw new AssertionError("Can not find supporting classes for "+targetClass+" " + e.getMessage());
		} catch (NoSuchMethodException e) {
			throw new AssertionError("Can not find supporting classes or methods for "+targetClass+" " + e.getMessage());
		} catch (InvocationTargetException e) {
			throw new AssertionError("Can not obtain service id "+targetClass+" " + e.getMessage());
		} catch (IllegalAccessException e) {
			throw new AssertionError("Can not obtain service id "+targetClass+" " + e.getMessage());
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
			throw new IllegalStateException("Cannot connect to "+ri+", due: "+e.getTargetException().getMessage()+". Server at "+ri.getHost()+", port: "+ri.getPort()+" is down or not properly configured", e);
		} catch (InstantiationException e) {
			throw new IllegalStateException("Cannot connect to "+ri+", due: "+e.getMessage()+". Server at "+ri.getHost()+", port: "+ri.getPort()+" is down or not properly configured", e);
		} catch (IllegalAccessException e) {
			throw new IllegalStateException("Cannot connect to "+ri+", due: "+e.getMessage()+". Server at "+ri.getHost()+", port: "+ri.getPort()+" is down or not properly configured", e);
		}
	}

	public static final String describeConnectivity(){
		return isLocal() ? "Local" : "Remote: "+getCurrentRemoteInstance();
	}

	public static ConnectivityMode getCurrentConnectivityMode() {
		return currentConnectivityMode;
	}

	public static void setCurrentRemoteInstance(RemoteInstance currentRemoteInstance) {
		if (WebUIConfig.getInstance().getUsageMode()==UsageMode.PERSONAL) {
			APILookupUtility.currentRemoteInstance = currentRemoteInstance;
			return;
		}
		MoSKitoWebUIContext.getCallContext().getCurrentSession().setAttribute(RemoteInstance.class.getName(), currentRemoteInstance);

	}
	public static void setCurrentConnectivityMode(ConnectivityMode currentConnectivityMode) {
		if (WebUIConfig.getInstance().getUsageMode()==UsageMode.PERSONAL) {
			APILookupUtility.currentConnectivityMode = currentConnectivityMode;
			return;
		}
		MoSKitoWebUIContext.getCallContext().getCurrentSession().setAttribute(ConnectivityMode.class.getName(), currentConnectivityMode);

	}


}
