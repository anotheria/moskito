package net.anotheria.moskito.webui.accumulators.api;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.moskito.core.accumulation.AccumulatorRepository;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.stats.impl.IntervalRegistry;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;


public class AccumulatorAPITest {

    @BeforeAll
    public static void setup() {
		MoskitoConfigurationHolder.resetConfiguration();
		MoskitoConfigurationHolder.getConfiguration().getBuiltinProducersConfig().disableAll();
		AccumulatorRepository.resetForUnitTests();
        APIFinder.cleanUp();
        APIFinder.addAPIFactory(AccumulatorAPI.class, new AccumulatorAPIFactory());
    }

    @Test
    public void testCreateDelete() throws APIException {
        AccumulatorAPI api = APIFinder.findAPI(AccumulatorAPI.class);

        assertNotNull(api.getAccumulatorDefinitions());

		final AccumulatorDefinitionAO newAccumulator = createAccumulator("test");
        assertEquals(1, api.getAccumulatorDefinitions().size());

        api.removeAccumulator(newAccumulator.getId());
        assertEquals(0, api.getAccumulatorDefinitions().size());


    }

    @Disabled
	@Test
    public void testGetAccumulatorGraphData() throws Exception {
        final AccumulatorDefinitionAO accumulatorDef1 = createAccumulator("testAccumulator1");
        final AccumulatorDefinitionAO accumulatorDef2 = createAccumulator("testAccumulator2");
        final AccumulatorDefinitionAO accumulatorDef3 = createAccumulator("testAccumulator3");

        AccumulatorAPI api = APIFinder.findAPI(AccumulatorAPI.class);
        final AccumulatedSingleGraphAO accumulatorGraphAO1 = api.getAccumulatorGraphData(accumulatorDef1.getId());
        assertNotNull(accumulatorGraphAO1);
        assertEquals("testAccumulator1", accumulatorGraphAO1.getName(), "Should be equals");
        assertEquals("testColor1", accumulatorGraphAO1.getColor(), "Should be equals");

        final AccumulatedSingleGraphAO accumulatorGraphAO2 = api.getAccumulatorGraphData(accumulatorDef2.getId());
        assertNotNull(accumulatorGraphAO2);
        assertEquals("testAccumulator2", accumulatorGraphAO2.getName(), "Should be equals");
        assertEquals("testColor2", accumulatorGraphAO2.getColor(), "Should be equals");

        final AccumulatedSingleGraphAO accumulatorGraphAO3 = api.getAccumulatorGraphData(accumulatorDef3.getId());
        assertNotNull(accumulatorGraphAO3);
        assertEquals("testAccumulator3", accumulatorGraphAO3.getName(), "Should be equals");
        assertNull(accumulatorGraphAO3.getColor(), "Should be null");

    }

    /**
     * Creates {@link AccumulatorDefinitionAO}.
     *
     * @param name accumulator name
     * @return {@link AccumulatorDefinitionAO}
     */
    private AccumulatorDefinitionAO createAccumulator(final String name) throws APIException {
        final AccumulatorPO toCreate = new AccumulatorPO();
        toCreate.setInterval(IntervalRegistry.getInstance().getInterval(Constants.INTERVAL_FIVE_MINUTES).getName());
        toCreate.setName(name);
        toCreate.setProducerId("Non-Existing");
        toCreate.setStatName("Foo");
        toCreate.setUnit(TimeUnit.MILLISECONDS.name());

        AccumulatorAPI api = APIFinder.findAPI(AccumulatorAPI.class);
        final AccumulatorDefinitionAO accumulatorDefinitionAO = api.createAccumulator(toCreate);
        assertNotNull(accumulatorDefinitionAO);

        return accumulatorDefinitionAO;
    }
}
