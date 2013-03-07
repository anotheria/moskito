package net.anotheria.moskito.webui.threshold.api;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.core.stats.DefaultIntervals;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.threshold.Threshold;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 07.03.13 08:57
 */
public class ThresholdAPITest {
	@Before
	@After
	public void setup(){
		APIFinder.cleanUp();
		APIFinder.addAPIFactory(ThresholdAPI.class, new ThresholdAPIFactory());
		ProducerRegistryFactory.reset();
	}

	@Test
	public void testCreateDelete() throws APIException {
		ThresholdAPI api = APIFinder.findAPI(ThresholdAPI.class);

		assertNotNull(api.getThresholdDefinitions());

		ThresholdPO toCreate = createPO();

		Threshold ret = api.createThreshold(toCreate);
		assertNotNull(ret);

		assertEquals(1, api.getThresholdDefinitions().size());

		api.removeThreshold(ret.getId());
		assertEquals(0, api.getThresholdDefinitions().size());


	}

	@Test
	public void testUpdate() throws APIException {
		ThresholdAPI api = APIFinder.findAPI(ThresholdAPI.class);

		assertNotNull(api.getThresholdDefinitions());

		ThresholdPO toCreate = createPO();

		Threshold ret = api.createThreshold(toCreate);
		assertNotNull(ret);

		assertEquals(1, api.getThresholdDefinitions().size());


		toCreate.setYellowValue("10000");
		api.updateThreshold(ret.getId(), toCreate);
		//TODO add checks
	}


	@Test public void testWrongDirectionHandling() throws APIException{
		ThresholdAPI api = APIFinder.findAPI(ThresholdAPI.class);
		ThresholdPO toCreate = createPO();
		toCreate.setYellowDir("schabernack");
		try{
			Threshold ret = api.createThreshold(toCreate);
			fail("Illegal argument exception expected");
		}catch(IllegalArgumentException e){

		}
	}

	@Test public void testWrongValueFormat() throws APIException{
		ThresholdAPI api = APIFinder.findAPI(ThresholdAPI.class);
		ThresholdPO toCreate = createPO();
		toCreate.setYellowValue("schabernack");
		try{
			Threshold ret = api.createThreshold(toCreate);
			fail("Illegal argument exception expected");
		}catch(NumberFormatException e){

		}
	}

	private ThresholdPO createPO(){
		ThresholdPO toCreate = new ThresholdPO();
		toCreate.setInterval(DefaultIntervals.FIVE_MINUTES.getName());
		toCreate.setName("test");
		toCreate.setProducerId("Non-Existing");
		toCreate.setStatName("Foo");
		toCreate.setUnit(TimeUnit.MILLISECONDS.name());

		toCreate.setGreenDir("below"); toCreate.setGreenValue("100");
		toCreate.setYellowDir("above"); toCreate.setYellowValue("100");
		toCreate.setOrangeDir("above"); toCreate.setOrangeValue("200");
		toCreate.setRedDir("above"); toCreate.setRedValue("300");
		toCreate.setPurpleDir("above"); toCreate.setPurpleValue("500");

		return toCreate;
	}
}
