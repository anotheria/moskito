package net.anotheria.moskito.core.errorhandling;

import net.anotheria.moskito.core.config.errorhandling.ErrorCatcherConfig;

/**
 * Factory for creating {@link ErrorCatcher} instances based on configuration.
 *
 * <p>This factory provides a centralized way to instantiate error catchers for the MoSKito
 * error handling system. Error catchers intercept and record exceptions that occur during
 * application execution, making them available for analysis through the monitoring interface.
 *
 * <p><b>Current Implementation:</b> Currently returns {@link BuiltinErrorCatcher} instances.
 * The factory pattern allows for future extension to support custom error catcher implementations.
 *
 * <p><b>Thread Safety:</b> This class is stateless and thread-safe.
 *
 * <p><b>Example Usage:</b>
 * <pre>
 * // Create configuration
 * ErrorCatcherConfig config = new ErrorCatcherConfig();
 * config.setParameter("maxErrors", "100");
 *
 * // Create error catcher
 * ErrorCatcher catcher = ErrorCatcherFactory.createErrorCatcher(config);
 *
 * // Error catcher is now ready to catch and record exceptions
 * </pre>
 *
 * @author lrosenberg
 * @since 23.02.18 14:20
 * @see ErrorCatcher
 * @see BuiltinErrorCatcher
 * @see ErrorCatcherConfig
 */
public class ErrorCatcherFactory {
	public static ErrorCatcher createErrorCatcher(ErrorCatcherConfig config){
		BuiltinErrorCatcher catcher = new BuiltinErrorCatcher(config);
		return catcher;
	}
}
