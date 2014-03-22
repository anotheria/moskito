package net.anotheria.moskito.webui.testremote;

import org.distributeme.core.ServerSideCallContext;
import org.distributeme.core.interceptor.InterceptionContext;
import org.distributeme.core.interceptor.InterceptorResponse;
import org.distributeme.core.interceptor.ServerSideRequestInterceptor;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 22.03.14 13:38
 */
public class SysoutInterceptor implements ServerSideRequestInterceptor {
	@Override
	public InterceptorResponse beforeServantCall(ServerSideCallContext serverSideCallContext, InterceptionContext interceptionContext) {
		return InterceptorResponse.CONTINUE;
	}

	@Override
	public InterceptorResponse afterServantCall(ServerSideCallContext serverSideCallContext, InterceptionContext interceptionContext) {
		return InterceptorResponse.CONTINUE;
	}
}
