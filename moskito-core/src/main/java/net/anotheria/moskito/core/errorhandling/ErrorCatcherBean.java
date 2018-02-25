package net.anotheria.moskito.core.errorhandling;

import net.anotheria.moskito.core.config.errorhandling.ErrorCatcherTarget;

/**
 * This class is used to transfer information about a catcher temporarly within an application. Like from BuiltInErrorProducer to the API.
 *
 * @author lrosenberg
 * @since 25.02.18 21:50
 */
public class ErrorCatcherBean {

	public static enum ErrorCatcherType{
		/**
		 * Default means reacing to all errors.
		 */
		DEFAULT,
		/**
		 * Customer error catchers.
		 */
		CUSTOM,
		/**
		 * ErrorCatchers that are bound to one exception type.
		 */
		EXCEPTION_BOUND;
	}

	private ErrorCatcherTarget target;

	private ErrorCatcherType type;

	private String name;

	private int numberOfCaughtErrors;

	private String parameter;

	public ErrorCatcherType getType() {
		return type;
	}

	public void setType(ErrorCatcherType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumberOfCaughtErrors() {
		return numberOfCaughtErrors;
	}

	public void setNumberOfCaughtErrors(int numberOfCaughtErrors) {
		this.numberOfCaughtErrors = numberOfCaughtErrors;
	}
	public void setTarget(ErrorCatcherTarget target) {
		this.target = target;
	}

	public ErrorCatcherTarget getTarget() {
		return target;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
}
