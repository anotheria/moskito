package net.java.dev.moskito.sql.callingAspect;

import org.junit.Test;

/**
 * SQL intercept test.
 *
 * @author <a href="mailto:vzhovtiuk@anotheria.net">Vitaliy Zhovtiuk</a>
 *         Date: 11/29/11
 *         Time: 2:22 PM
 */
public class MethodCallTest {
    @Test
    public void shouldInterceptMethod() throws Exception {
        CalledMethod calledMethod = new CalledMethod();
        calledMethod.doSomething();
        calledMethod.doSomething();
        calledMethod.doSomething();
    }
}
