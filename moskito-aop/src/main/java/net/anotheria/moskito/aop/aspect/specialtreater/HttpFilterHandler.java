package net.anotheria.moskito.aop.aspect.specialtreater;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Handler to extract call information from an http filter.
 * It retrieves the request URI (for now).
 * Maybe parameters will be added later.
 *
 * @author lrosenberg
 * @since 08.12.20 15:32
 */
public class HttpFilterHandler implements SpecialCaseHandler{
	@Override
	public StringBuilder getCallDescription(Class clazz, String methodName, Object target, Object[] args) {

		//this is special treater for http filter.
		try {
			Class requestClazz = Class.forName("jakarta.servlet.http.HttpServletRequest");
			Object requestObject = args[0];
			if (!requestClazz.isInstance(requestObject))
				return null;
			Method m2 = requestClazz.getMethod("getRequestURI", null);
			Method m1 = requestClazz.getMethod("getMethod", null);
			return new StringBuilder().append(m1.invoke(requestObject)).append(' ').append(m2.invoke(requestObject) );
		}catch(ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e){
			//TODO log handling maybe.
			e.printStackTrace();
			return null;
		}
	}
}
