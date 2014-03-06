package net.anotheria.moskito.core.util;

/**
 * Utility to check if MoSKito WebUI is connected to application at runtime.
 *
 * @author Vladyslav Bezuhlyi
 */
public class MoskitoWebUi {

    public static boolean isPresent() {
        try {
            Class.forName("net.anotheria.moskito.webui.decorators.DecoratorRegistryFactory");
            return true;
        } catch (ClassNotFoundException e) {
            return false;
        }
    }

    public static boolean isAbsent() {
        return !isPresent();
    }

}
