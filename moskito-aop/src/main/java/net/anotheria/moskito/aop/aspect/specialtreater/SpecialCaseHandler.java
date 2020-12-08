package net.anotheria.moskito.aop.aspect.specialtreater;

/**
 * A specific handler to create case description for a special case.
 *
 * @author lrosenberg
 * @since 08.12.20 15:32
 */
public interface SpecialCaseHandler {
	StringBuilder getCallDescription(Class clazz, String methodName, Object target, Object[] args);
}
