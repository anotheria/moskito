package net.anotheria.moskito.aop.aspect.specialtreater;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 08.12.20 15:32
 */
public class HttpFilterTreater implements SpecialTreater{
	@Override
	public StringBuilder getCallDescription(Class clazz, String methodName, Object target, Object[] args) {

		//this is special treater for http filter.
		try {
			Class requestClazz = Class.forName("javax.servlet.http.HttpServletRequest");
			Object requestObject = args[0];
			if (!requestClazz.isInstance(requestObject))
				return null;
			Method m = requestClazz.getMethod("getRequestURI", null);
			return new StringBuilder().append(m.invoke(requestObject));
		}catch(ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e){
			e.printStackTrace();
			return null;
		}
	}
}
