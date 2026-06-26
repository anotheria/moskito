package net.anotheria.moskito.core.dynamic;

import net.anotheria.moskito.core.predefined.ServiceStatsFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.*;


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
		assertEquals(limit, p.getLimit(), "expected previously set limit");
		for (int i=0; i<limit; i++)
			p.getStats(String.valueOf(i));
		//now limit should be reached.
		try{
			p.getStats("bla");
			fail("Exception expected");
		}catch(OnDemandStatsProducerException e){
			
		}
		
	}
}
