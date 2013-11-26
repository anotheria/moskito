package net.anotheria.moskito.integration.cdi.scopes;

import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 26.11.13 16:55
 */
public class ScopeInstanceCreationListener {

	private static ScopeCountStats requestStats = new ScopeCountStats("request");
	private static ScopeCountStats sessionStats;
	private static ScopeCountStats conversationStats;
	private static ScopeCountStats applicationStats;

	public static void requestContextInitialized(@Observes @Initialized(RequestScoped.class) Object event){

	}

	public static void requestContextDestroyed(@Observes @Destroyed(RequestScoped.class) Object event){

	}

}
