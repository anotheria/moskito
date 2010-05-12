package net.java.dev.moskito.core.dynamic;

import net.anotheria.util.IdCodeGenerator;
import net.java.dev.moskito.core.inspection.CreationInfo;
import net.java.dev.moskito.core.predefined.ServiceStatsFactory;

import org.junit.Test;
import static org.junit.Assert.*;

public class OnDemandStatsProducerTest {
	@Test public void testLimit() throws Exception {
		OnDemandStatsProducer p = new OnDemandStatsProducer("id", "aCategory", "aSubsystem", new ServiceStatsFactory()){
			
			private boolean limit = false;
			
			public boolean limitForNewEntriesReached(){	
				try{
					return limit;
				}finally{
					limit = !limit;
				}
			}
		};
		
		p.getStats("foo");
		try{
			p.getStats("this_will_throw_an_error");
			fail("Exception expected!");
		}catch(OnDemandStatsProducerException e){
			
		}
		
	}
	
	@Test public void testBasicStuff(){
		String category = IdCodeGenerator.generateCode(20);
		String subsystem = IdCodeGenerator.generateCode(20);
		String id = IdCodeGenerator.generateCode(20);
		
		OnDemandStatsProducer p = new OnDemandStatsProducer(id, category, subsystem, new ServiceStatsFactory());
		assertEquals(id, p.getProducerId());
		assertEquals(category, p.getCategory());
		assertEquals(subsystem, p.getSubsystem());
		assertNotNull(p.toString());
		
		CreationInfo info = p.getCreationInfo();
		assertNotNull(info);
		assertNotNull(info.getStackTrace());
	}

	@Test public void testNull(){
		String id = IdCodeGenerator.generateCode(20);
		
		OnDemandStatsProducer p = new OnDemandStatsProducer(id, null, null, new ServiceStatsFactory());
		assertEquals(id, p.getProducerId());
		assertEquals("default", p.getCategory());
		assertEquals("default", p.getSubsystem());
	}
}
