package net.java.dev.moskito.sql.callingAspect;

import net.java.dev.moskito.sql.annotation.MonitorClass;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:vzhovtiuk@anotheria.net">Vitaliy Zhovtiuk</a>
 *         Date: 12/1/11
 *         Time: 4:59 PM
 *         To change this template use File | Settings | File Templates.
 */
@MonitorClass
public class CalledClass {
    public void doSome() {
        System.out.println("Do some");
    }

    public void doSome2() {
        System.out.println("Do some2");
    }
}
