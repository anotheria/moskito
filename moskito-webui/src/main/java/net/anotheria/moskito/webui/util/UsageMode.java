package net.anotheria.moskito.webui.util;

/**
 * Enum defining the different usage modes. The difference between the usage modes between PERSONAL is SHARED is in server connectivity.
 * In Personal usage mode the server connect is application wide, in Shared usage mode - bound to user session.
 *
 * @author lrosenberg
 * @since 11.05.14 17:49
 */
public enum UsageMode {
	/**
	 * Personal usage mode suggests that the user is using its own copy of moskito inspect without interference with others.
	 */
	PERSONAL,
	/**
	 * Multiple users are sharing one copy of moskito inspect.
	 */
	SHARED
}
