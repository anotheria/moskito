package net.anotheria.moskito.webui.producers.api;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.IProducerRegistry;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.util.IdCodeGenerator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 14.02.13 16:08
 */
public class ProducerAPITest {
	@Before
	public void startUp(){
		APIFinder.addAPIFactory(ProducerAPI.class, new ProducerAPIFactory());
		System.setProperty("JUNITTEST", "true");
	}

	@After
	public void shutDown(){
		APIFinder.cleanUp();
		ProducerRegistryFactory.reset();
	}

	@Test
	public void testSubsystems() throws APIException{
		IProducerRegistry registry = ProducerRegistryFactory.getProducerRegistryInstance();
		ProducerAPI api = APIFinder.findAPI(ProducerAPI.class);

		assertNotNull(api.getSubsystems());
		//the one is the api we initialized above
		assertEquals(1, api.getSubsystems().size());
		assertEquals("default", api.getSubsystems().get(0).getUnitName());
		assertEquals(1, api.getSubsystems().get(0).getUnitCount());


		registry.registerProducer(new DummyProducer(null, "foo"));
		registry.registerProducer(new DummyProducer(null, "foo"));
		registry.registerProducer(new DummyProducer(null, "foo"));
		registry.registerProducer(new DummyProducer(null, "foo"));
		registry.registerProducer(new DummyProducer(null, "foo"));

		assertEquals(2, api.getSubsystems().size());

		for (int i=0; i<10; i++){
			registry.registerProducer(new DummyProducer(null, "foo"+i));
		}

		assertEquals(12, api.getSubsystems().size());


	}
	@Test
	public void testCategories() throws APIException{
		IProducerRegistry registry = ProducerRegistryFactory.getProducerRegistryInstance();
		ProducerAPI api = APIFinder.findAPI(ProducerAPI.class);

		assertNotNull(api.getCategories());
		//the one is the api we initialized above
		assertEquals(1, api.getCategories().size());
		assertEquals("api", api.getCategories().get(0).getUnitName());
		assertEquals(1, api.getCategories().get(0).getUnitCount());


		registry.registerProducer(new DummyProducer("foo", null));
		registry.registerProducer(new DummyProducer("foo", null));
		registry.registerProducer(new DummyProducer("foo", null));
		registry.registerProducer(new DummyProducer("foo", null));
		registry.registerProducer(new DummyProducer("foo", null));

		assertEquals(2, api.getCategories().size());

		for (int i=0; i<10; i++){
			registry.registerProducer(new DummyProducer("foo"+i, null));
		}

		assertEquals(12, api.getCategories().size());


	}

	static class DummyProducer implements IStatsProducer{

		private String category, subsystem;

		DummyProducer(String category, String subsystem){
			this.category = category;
			this.subsystem = subsystem;
		}

		@Override
		public List getStats() {
			return null;
		}

		@Override
		public String getProducerId() {
			return IdCodeGenerator.generateCode(20);
		}

		@Override
		public String getCategory() {
			return category;
		}

		@Override
		public String getSubsystem() {
			return subsystem;
		}
	}
}
