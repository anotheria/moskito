package net.anotheria.moskito.core.config.errorhandling;

import org.configureme.annotations.Configure;

import java.io.Serializable;

/**
 * Defines a catcher for a specific error. Error catchers are defined to help you to find errors which are unexpectingly occuring in your application.
 *
 * @author lrosenberg
 * @since 04.06.17 14:43
 */
public class ErrorCatcherConfig implements Serializable{
	/**
	 * The error clazz as fully qualified string (x.z.RuntimeException).
	 */
	@Configure
	private String exceptionClazz;

	/**
	 * Class name of the error catcher.
	 */
	@Configure
	private String errorCatcherClazz;
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

	public String getExceptionClazz() {
		return exceptionClazz;
	}

	public void setExceptionClazz(String exceptionClazz) {
		this.exceptionClazz = exceptionClazz;
	}

	public String getErrorCatcherClazz() {
		return errorCatcherClazz;
	}

	public void setErrorCatcherClazz(String errorCatcherClazz) {
		this.errorCatcherClazz = errorCatcherClazz;
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
				"exceptionClazz='" + exceptionClazz + '\'' +
				", errorCatcherClazz='" + errorCatcherClazz + '\'' +
				", target=" + target +
				", parameter='" + parameter + '\'' +
				'}';
	}
}
