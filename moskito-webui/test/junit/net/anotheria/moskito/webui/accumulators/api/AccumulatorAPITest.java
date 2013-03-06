package net.anotheria.moskito.webui.accumulators.api;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.moskito.core.stats.DefaultIntervals;
import net.anotheria.moskito.core.stats.TimeUnit;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 06.03.13 07:25
 */
public class AccumulatorAPITest {

	@Before @After public void setup(){
		APIFinder.cleanUp();
		APIFinder.addAPIFactory(AccumulatorAPI.class, new AccumulatorAPIFactory());
	}

	@Test public void testCreateDelete() throws APIException{
		AccumulatorAPI api = APIFinder.findAPI(AccumulatorAPI.class);

		assertNotNull(api.getAccumulatorDefinitions());


		AccumulatorPO toCreate = new AccumulatorPO();
		toCreate.setInterval(DefaultIntervals.FIVE_MINUTES.getName());
		toCreate.setName("test");
		toCreate.setProducerId("Non-Existing");
		toCreate.setStatName("Foo");
		toCreate.setUnit(TimeUnit.MILLISECONDS.name());

		AccumulatorDefinitionAO ret = api.createAccumulator(toCreate);
		assertNotNull(ret);

		assertEquals(1, api.getAccumulatorDefinitions().size());

		api.removeAccumulator(ret.getId());
		assertEquals(0, api.getAccumulatorDefinitions().size());


	}
}
