package net.anotheria.moskito.core.errorhandling;

import net.anotheria.moskito.core.config.errorhandling.ErrorCatcherConfig;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 23.02.18 14:20
 */
public class ErrorCatcherFactory {
	public static ErrorCatcher createErrorCatcher(ErrorCatcherConfig config){
		BuiltinErrorCatcher catcher = new BuiltinErrorCatcher(config);
		return catcher;
	}
}
