package net.anotheria.moskito.core.dynamic;

import net.anotheria.moskito.core.predefined.ServiceStatsFactory;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;
import static org.junit.Assert.*;


public class EntryCountLimitedOnDemandStatsProducerTest {
	@Test public void testLimit() throws Exception{
		int limit = 100;
		EntryCountLimitedOnDemandStatsProducer p = new EntryCountLimitedOnDemandStatsProducer("aProducerId", "aCategory", "aSubsystem", new ServiceStatsFactory(),
				limit);
		assertNotNull(p.toString());
		testWithLimit(p, 100);
		testWithLimit(p, 1000);
	}
	
	private void testWithLimit(EntryCountLimitedOnDemandStatsProducer p, int limit) throws Exception{
		p.setLimit(limit);
		assertEquals("expected previously set limit", limit, p.getLimit());
		for (int i=0; i<limit; i++)
			p.getStats(""+i);
		//now limit should be reached.
		try{
			p.getStats("bla");
			fail("Exception expected");
		}catch(OnDemandStatsProducerException e){
			
		}
		
	}
}
