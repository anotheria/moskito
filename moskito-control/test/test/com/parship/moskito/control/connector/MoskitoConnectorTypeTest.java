package test.com.parship.moskito.control.connector;

import junit.framework.Assert;

import net.java.dev.moskito.control.configuration.MoskitoConnectorType;

import org.junit.Test;



public class MoskitoConnectorTypeTest {

	@Test
	public void getCOnectorByType() {
		MoskitoConnectorType t = MoskitoConnectorType.getMoskitoConnectorByType("t");
		MoskitoConnectorType p = MoskitoConnectorType.getMoskitoConnectorByType("p");
		MoskitoConnectorType a = MoskitoConnectorType.getMoskitoConnectorByType("accumulatorsconnector");
		MoskitoConnectorType jmx = MoskitoConnectorType.getMoskitoConnectorByType("jmx");
		MoskitoConnectorType d = MoskitoConnectorType.getMoskitoConnectorByType("asdfasdf");
		
		Assert.assertEquals("thresholdsconnector", t.getTypeName());
		Assert.assertEquals("t", t.getShortAliasName());
		
		Assert.assertEquals("producersconnector", p.getTypeName());
		Assert.assertEquals("p", p.getShortAliasName());
		
		Assert.assertEquals("accumulatorsconnector", a.getTypeName());
		Assert.assertEquals("a", a.getShortAliasName());
		
		Assert.assertEquals("jmxconnector", jmx.getTypeName());
		Assert.assertEquals("jmx", jmx.getShortAliasName());
		
		Assert.assertEquals("defaultdummyconnector", d.getTypeName());
		Assert.assertEquals("d", d.getShortAliasName());
		
	}
	
}
