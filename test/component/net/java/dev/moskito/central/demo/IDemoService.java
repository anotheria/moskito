package net.java.dev.moskito.central.demo;

import java.io.Serializable;

import net.anotheria.anoprise.metafactory.Service;

import org.distributeme.annotation.DistributeMe;

/**
 * Sample of remode
 *
 * @author imercuriev
 *         Date: Feb 26, 2010
 *         Time: 9:41:57 AM
 */
@DistributeMe(
		initcode={"MetaFactory.addFactoryClass(net.java.dev.moskito.central.demo.IDemoService.class, Extension.LOCAL, net.java.dev.moskito.central.demo.DemoServiceFactory.class);"}
)
public interface IDemoService extends Service {
    <T extends Serializable> T echo(T aValue) throws DemoServiceException;
}
