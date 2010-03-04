package net.java.dev.moskito.central.demo;


import net.anotheria.anoprise.metafactory.Extension;
import net.anotheria.anoprise.metafactory.MetaFactory;
import net.anotheria.anoprise.metafactory.ServiceFactory;

import java.io.Serializable;

/**
 * TODO: purpose
 *
 * @author imercuriev
 *         Date: Mar 1, 2010
 *         Time: 12:05:25 PM
 */
public class TestRemoteClient {
    public static void main(String a[]) throws Exception{
        MetaFactory.addFactoryClass(IDemoService.class, Extension.REMOTE, (Class<ServiceFactory<IDemoService>>)Class.forName("net.java.dev.moskito.central.demo.generated.RemoteIDemoServiceFactory"));
        IDemoService service = MetaFactory.get(IDemoService.class, Extension.REMOTE);

        long limit = 1000;
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
