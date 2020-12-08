package net.anotheria.moskito.aop.aspect.specialtreater;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 08.12.20 15:32
 */
public interface SpecialTreater {
	StringBuilder getCallDescription(Class clazz, String methodName, Object target, Object[] args);
}
