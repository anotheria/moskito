package net.java.dev.moskito.central.demo;

import net.java.dev.moskito.central.storages.XmlStatStorage;

import java.io.File;
import java.io.Serializable;

/**
 * TODO: purpose
 *
 * @author imercuriev
 *         Date: Mar 1, 2010
 *         Time: 10:21:17 AM
 */
public class TestLocalClient {
    public static void main(String[] args) {
        File storageDir = new File("/projects/companies/Anotheria/SVN/tests/xml");
        storageDir.mkdirs();
        IDemoService service = new DemoServiceImpl(
                new XmlStatStorage(
                        storageDir
                )
        );

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
