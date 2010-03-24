package net.java.dev.moskito.central.demo;

import net.java.dev.moskito.core.stats.impl.IntervalRegistry;

import java.io.Serializable;

/**
 * Tests remote central stat storage
 *
 * @author imercuriev
 *         Date: Mar 24, 2010
 *         Time: 12:14:41 PM
 */
public class TestCentral {
    public static void main(String[] args) {
        IntervalRegistry.getInstance();
        IDemoService service = new DemoServiceImpl();

        long limit = 10000;
        long start = System.nanoTime();
        for (int i=0; i<limit; i++) {
            try {
                String param = "call #" + String.valueOf(i + 1);
                System.out.println("Calling method w/ param: " + param + " ...");
                Serializable result = service.echo("call #" + String.valueOf(i + 1));
                System.out.println("The method call returned: " + result);
            } catch (DemoServiceException e) {
                System.out.println("Exception caught: " + getAllExceptionMessages(e));
            }
        }
        long end = System.nanoTime();
        double duration = ((double)(end-start)) / 1000 / 1000;
        System.out.println("Sent "+limit+" requests in "+duration);
        System.out.println("avg req dur: "+(duration/limit)+" ms.");
    }

    private static String getAllExceptionMessages(Throwable e) {
        StringBuilder sb = new StringBuilder();
        Throwable thisException = e;
        while (thisException != null) {
            sb.append(thisException.getMessage());
            thisException = thisException.getCause();
            if (thisException != null) sb.append('\n');
        }
        return sb.toString();
    }

}
