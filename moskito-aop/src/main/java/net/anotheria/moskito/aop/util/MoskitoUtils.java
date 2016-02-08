package net.anotheria.moskito.aop.util;

/**
 * @author bvanchuhov
 */
public final class MoskitoUtils {

    private MoskitoUtils() {
    }

    public static String producerName(String declaringTypeName) {
        return declaringTypeName.substring(declaringTypeName.lastIndexOf('.') + 1);
    }
}
