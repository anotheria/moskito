package net.anotheria.moskito.webui.accumulators.api;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.stats.DefaultIntervals;
import net.anotheria.moskito.core.stats.TimeUnit;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 06.03.13 07:25
 */
public class AccumulatorAPITest {

    @BeforeClass
    public static void setup() {
		MoskitoConfigurationHolder.resetConfiguration();
        APIFinder.cleanUp();
        APIFinder.addAPIFactory(AccumulatorAPI.class, new AccumulatorAPIFactory());
    }

    @Test
    public void testCreateDelete() throws APIException {
        AccumulatorAPI api = APIFinder.findAPI(AccumulatorAPI.class);

        assertNotNull(api.getAccumulatorDefinitions());

        final AccumulatorDefinitionAO ret = createAccumulator("test");
        assertEquals(1, api.getAccumulatorDefinitions().size());

        api.removeAccumulator(ret.getId());
        assertEquals(0, api.getAccumulatorDefinitions().size());
    }

    @Test
    public void testGetAccumulatorGraphData() throws Exception {
        final AccumulatorDefinitionAO accumulatorDef1 = createAccumulator("testAccumulator1");
        final AccumulatorDefinitionAO accumulatorDef2 = createAccumulator("testAccumulator2");
        final AccumulatorDefinitionAO accumulatorDef3 = createAccumulator("testAccumulator3");

        AccumulatorAPI api = APIFinder.findAPI(AccumulatorAPI.class);
        final AccumulatedSingleGraphAO accumulatorGraphAO1 = api.getAccumulatorGraphData(accumulatorDef1.getId());
        assertNotNull(accumulatorGraphAO1);
        assertEquals("Should be equals", "testAccumulator1", accumulatorGraphAO1.getName());
        assertEquals("Should be equals", "testColor1", accumulatorGraphAO1.getColor());

        final AccumulatedSingleGraphAO accumulatorGraphAO2 = api.getAccumulatorGraphData(accumulatorDef2.getId());
        assertNotNull(accumulatorGraphAO2);
        assertEquals("Should be equals", "testAccumulator2", accumulatorGraphAO2.getName());
        assertEquals("Should be equals", "testColor2", accumulatorGraphAO2.getColor());

        final AccumulatedSingleGraphAO accumulatorGraphAO3 = api.getAccumulatorGraphData(accumulatorDef3.getId());
        assertNotNull(accumulatorGraphAO3);
        assertEquals("Should be equals", "testAccumulator3", accumulatorGraphAO3.getName());
        assertNull("Should be null", accumulatorGraphAO3.getColor());

        //clean
        api.removeAccumulator(accumulatorDef1.getId());
        api.removeAccumulator(accumulatorDef2.getId());
        api.removeAccumulator(accumulatorDef3.getId());
    }

    /**
     * Creates {@link AccumulatorDefinitionAO}.
     *
     * @param name accumulator name
     * @return {@link AccumulatorDefinitionAO}
     */
    private AccumulatorDefinitionAO createAccumulator(final String name) throws APIException {
        final AccumulatorPO toCreate = new AccumulatorPO();
        toCreate.setInterval(DefaultIntervals.FIVE_MINUTES.getName());
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
