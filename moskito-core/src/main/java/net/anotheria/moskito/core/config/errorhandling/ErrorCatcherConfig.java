package net.anotheria.moskito.core.config.errorhandling;

import org.configureme.annotations.Configure;

/**
 * Defines a catcher for a specific error. Error catchers are defined to help you to find errors which are unexpectingly occuring in your application.
 *
 * @author lrosenberg
 * @since 04.06.17 14:43
 */
public class ErrorCatcherConfig {
	/**
	 * The error clazz as fully qualified string (x.z.RuntimeException).
	 */
	@Configure
	private String clazz;
	/**
	 * Error catcher target. Can be LOG or MEMORY (see ErrorCatcherTarget class for details).
	 */
	@Configure
	private ErrorCatcherTarget target;
	/**
	 * Target specific parameter. 
	 */
	@Configure
	private String parameter;

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public ErrorCatcherTarget getTarget() {
		return target;
	}

	public void setTarget(ErrorCatcherTarget target) {
		this.target = target;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	@Override
	public String toString() {
		return "ErrorCatcherConfig{" +
				"clazz='" + clazz + '\'' +
				", target=" + target +
				", parameter='" + parameter + '\'' +
				'}';
	}
}
