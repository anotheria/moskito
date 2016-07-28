package net.anotheria.moskito.webui.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * This listener enables remote webui backend.
 *
 * @author lrosenberg
 * @since 31.03.14 01:30
 */
public class EnableWebUIBackend implements ServletContextListener{

	private static Logger log = LoggerFactory.getLogger(EnableWebUIBackend.class);

	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		Class serverClazz = null;
		try{
			serverClazz = Class.forName("net.anotheria.moskito.webui.shared.api.generated.CombinedAPIServer");
			Method startMethod = serverClazz.getMethod("createCombinedServicesAndRegisterLocally");
			startMethod.invoke(null);
		}catch(ClassNotFoundException e){
			log.error("Couldn't find the backend server class", e);
		}catch(NoSuchMethodException e){
			log.error("Couldn't find the method in server class", e);
		} catch (InvocationTargetException | IllegalAccessException e) {
			log.error("Couldn't invoke start method", e);
		}
    }

	@Override
	public void contextDestroyed(ServletContextEvent servletContextEvent) {

	}
}
