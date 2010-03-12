package net.java.dev.moskito.central.demo;

import net.anotheria.anoprise.metafactory.ServiceFactory;

/**
 * TODO: purpose
 *
 * @author imercuriev
 *         Date: Feb 26, 2010
 *         Time: 9:46:41 AM
 */
public class DemoServiceFactory implements ServiceFactory<IDemoService> {
    public IDemoService create() {
        return new DemoServiceImpl();
    }
}
