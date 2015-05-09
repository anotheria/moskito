package net.anotheria.moskito.core.calltrace;

import java.lang.reflect.Method;

/**
 * Tracing Util.
 *
 * @author Alex Osadchy
 */
public final class TracingUtil {

    /**
     * Private constructor.
     */
    private TracingUtil() {}

    /**
     * Builds call.
     *
     * @param method source {@link Method}, can't be null
     * @param parameters method parameters
     *
     * @return call
     */
    public static String buildCall(final Method method, final Object[] parameters) {
        if (method == null) {
            throw new IllegalArgumentException("Parameter method can't be null");
        }

        StringBuilder call = new StringBuilder(method.getDeclaringClass().getSimpleName()).append('.').append(method.getName()).append("(");

        if (parameters != null && parameters.length > 0) {
            for (int i = 0; i < parameters.length; i++) {
                call.append(parameters[i]);
                if (i < parameters.length - 1) {
                    call.append(", ");
                }
            }
        }

        call.append(")");

        return call.toString();
    }

}
