package net.anotheria.moskito.annotation;

import java.util.concurrent.TimeUnit;

/**
 * Created by IntelliJ IDEA.
 *
 * @author <a href="mailto:vzhovtiuk@anotheria.net">Vitaliy Zhovtiuk</a>
 *         Date: 12/1/11
 *         Time: 4:59 PM
 *         To change this template use File | Settings | File Templates.
 */
@MonitorClass
public class AnnotatedClass {
    public void doSome() throws InterruptedException {
        TimeUnit.NANOSECONDS.sleep(1);
    }

    protected void doSome2() throws InterruptedException {
        TimeUnit.NANOSECONDS.sleep(1);
    }

    protected void doSome3() throws InterruptedException {
        TimeUnit.NANOSECONDS.sleep(1);
    }

    protected void doSome4() throws InterruptedException {
        TimeUnit.NANOSECONDS.sleep(1);
    }

    private void doSome5() throws InterruptedException {
        TimeUnit.NANOSECONDS.sleep(1);
    }


}
