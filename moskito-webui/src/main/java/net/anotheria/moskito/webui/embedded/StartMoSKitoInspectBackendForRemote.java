package net.anotheria.moskito.webui.embedded;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This utility class starts the MoSKitoInspect
 *
 * @author lrosenberg
 * @since 16.04.14 10:58
 */
public class StartMoSKitoInspectBackendForRemote {

	/**
	 * Log.
	 */
	private static Logger log = LoggerFactory.getLogger(StartMoSKitoInspectBackendForRemote.class);

	/**
	 * Starts MoSKito Inspect Backend. Uses default port.
	 * @throws MoSKitoInspectStartException
	 */
	public static void startMoSKitoInspectBackend() throws MoSKitoInspectStartException {
		startMoSKitoInspectBackend(-1);
	}

	/**
	 * Starts MoSKito Inspect Backend.
	 * @param port port on which to start moskito rmi listener. If not specified (-1) either default or configured by distributeme port is used.
	 * @throws MoSKitoInspectStartException
	 */
	public static void startMoSKitoInspectBackend(int port) throws MoSKitoInspectStartException{

		log.info("Starting moskito-inspect remote agent at port "+port);

		Class serverClazz = null;
		Exception exception = null;
		try{
			serverClazz = Class.forName("net.anotheria.moskito.webui.shared.api.generated.CombinedAPIServer");
			Method startMethod = serverClazz.getMethod("createCombinedServicesAndRegisterLocally", int.class);
			startMethod.invoke(null, port);
		}catch(ClassNotFoundException e){
			exception = e;
			log.error("Couldn't find the backend server class", e);
		}catch(NoSuchMethodException e){
			exception = e;
			log.error("Couldn't find the method in server class", e);
		} catch (InvocationTargetException e) {
			exception = e;
			log.error("Couldn't invoke start method", e);
		} catch (IllegalAccessException e) {
			exception = e;
			log.error("Couldn't invoke start method", e);
		}

		if (exception!=null){
			throw new MoSKitoInspectStartException(exception);
		}
	}
}
