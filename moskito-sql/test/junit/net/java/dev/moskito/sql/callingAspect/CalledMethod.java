package net.java.dev.moskito.sql.callingAspect;

import net.java.dev.moskito.sql.annotation.MonitorMethod;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:vzhovtiuk@anotheria.net">Vitaliy Zhovtiuk</a>
 *         Date: 12/1/11
 *         Time: 4:59 PM
 *         To change this template use File | Settings | File Templates.
 */
public class CalledMethod {
    @MonitorMethod
    public void doSomething() {
        System.out.println("Do something");
    }
}
