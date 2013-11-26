package net.anotheria.moskito.integration.cdi.scopes;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.context.Destroyed;
import javax.enterprise.context.Initialized;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 26.11.13 16:55
 */
public final class ScopeInstanceCreationListener {

	private ScopeInstanceCreationListener(){

	}

	private static ScopeCountStats requestStats = new ScopeCountStats("request");
	private static ScopeCountStats sessionStats = new ScopeCountStats("session");
	private static ScopeCountStats conversationStats = new ScopeCountStats("conversation");
	private static ScopeCountStats applicationStats = new ScopeCountStats("application");

	static{
		//create registration wrappers.
		new ScopeInstanceCreationProducer("cdi-requests", requestStats);
		new ScopeInstanceCreationProducer("cdi-sessions", sessionStats);
		new ScopeInstanceCreationProducer("cdi-conversations", conversationStats);
		new ScopeInstanceCreationProducer("cdi-application", applicationStats);


	}

	public static void requestContextInitialized(@Observes @Initialized(RequestScoped.class) Object event){
		System.out.println("--- RequestScoped CREATED "+event+" "+event.getClass());
		requestStats.notifyInstanceCreated();
	}

	public static void requestContextDestroyed(@Observes @Destroyed(RequestScoped.class) Object event){
		System.out.println("--- RequestScoped DESTROYED "+event+" "+event.getClass());
		requestStats.notifyInstanceDestroyed();
	}

	public static void sessionContextInitialized(@Observes @Initialized(SessionScoped.class) Object event){
		System.out.println("--- SessionScoped CREATED "+event+" "+event.getClass());
		sessionStats.notifyInstanceCreated();
	}

	public static void sessionContextDestroyed(@Observes @Destroyed(SessionScoped.class) Object event){
		System.out.println("--- SessionScoped DESTROYED "+event+" "+event.getClass());
		sessionStats.notifyInstanceDestroyed();
	}

	public static void conversationContextInitialized(@Observes @Initialized(ConversationScoped.class) Object event){
		System.out.println("--- ConversationScoped CREATED");
		conversationStats.notifyInstanceCreated();
	}

	public static void conversationContextDestroyed(@Observes @Destroyed(ConversationScoped.class) Object event){
		System.out.println("--- ConversationScoped DESTROYED");
		conversationStats.notifyInstanceDestroyed();
	}

	public static void applicationContextInitialized(@Observes @Initialized(ApplicationScoped.class) Object event){
		System.out.println("--- ApplicationScoped CREATED");
		applicationStats.notifyInstanceCreated();
	}

	public static void applicationContextDestroyed(@Observes @Destroyed(ApplicationScoped.class) Object event){
		System.out.println("--- ApplicationScoped DESTROYED");
		applicationStats.notifyInstanceDestroyed();
	}

}
