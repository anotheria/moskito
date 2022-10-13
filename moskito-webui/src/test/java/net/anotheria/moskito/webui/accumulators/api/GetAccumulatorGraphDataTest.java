package net.anotheria.moskito.webui.accumulators.api;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.moskito.core.accumulation.AccumulatorRepository;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.thresholds.GuardConfig;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.predefined.AbstractStatsFactory;
import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.producers.AbstractStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.IProducerRegistry;
import net.anotheria.moskito.core.registry.ProducerRegistryAPIFactory;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.core.stats.TimeUnit;
import net.anotheria.moskito.core.threshold.ThresholdRepository;
import net.anotheria.moskito.webui.shared.api.TieablePO;
import net.anotheria.moskito.webui.threshold.api.ThresholdAPI;
import net.anotheria.moskito.webui.threshold.api.ThresholdAPIFactory;
import net.anotheria.moskito.webui.threshold.api.ThresholdPO;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GetAccumulatorGraphDataTest {

    @BeforeClass
    public static void startUpTest() {
        System.setProperty("JUNITTEST", "true");
        APIFinder.setMockingEnabled(true);
    }

    @AfterClass
    public static void shutDownTests() {
        System.clearProperty("JUNITTEST");
        APIFinder.cleanUp();
        MoskitoConfigurationHolder.resetConfiguration();
    }

    @Before
    public void startUp() {
        APIFinder.addAPIFactory(AccumulatorAPI.class, new AccumulatorAPIFactory());
        APIFinder.addAPIFactory(ThresholdAPI.class, new ThresholdAPIFactory());

        ProducerRegistryAPIFactory.resetForUnitTest();
        ProducerRegistryFactory.reset();

        AccumulatorRepository.resetForUnitTests();
        ThresholdRepository.resetForUnitTests();
    }

    @After
    public void shutDown() {
        APIFinder.cleanUp();
        ProducerRegistryAPIFactory.resetForUnitTest();
        ProducerRegistryFactory.reset();
    }

    @Test
    public void test() throws APIException {
        IProducerRegistry registry = ProducerRegistryFactory.getProducerRegistryInstance();
        assertEquals(registry.getProducers().size(), 0);

        registerFakeProducers(new String[]{"p1", "p2", "p3", "p4", "p5"});
        assertEquals(registry.getProducers().size(), 5);


        AccumulatorRepository aRepository = AccumulatorRepository.getInstance();

        assertEquals(aRepository.getAccumulators().size(), 0);
        createFakeAccumulators(new String[]{"a1", "a2", "a3", "a4", "a5", "a6", "a7"});
        assertEquals(aRepository.getAccumulators().size(), 7);
        assertEquals("Expected 6: "+registry.getProducers(), 6, registry.getProducers().size());


        ThresholdRepository tRepository = ThresholdRepository.getInstance();

        assertEquals(tRepository.getThresholds().size(), 0);
        createFakeThresholds(new String[]{"t1", "t2", "t3", "t4", "t5", "t6"});
        assertEquals(tRepository.getThresholds().size(), 6);
        assertEquals(registry.getProducers().size(), 7);


        createFakeAccumulatoTresholdPairs(new String[]{"at1", "at2", "at3", "at4"});
        assertEquals(registry.getProducers().size(), 11);
        assertEquals(aRepository.getAccumulators().size(), 11);
        assertEquals(tRepository.getThresholds().size(), 10);


        AccumulatorAPI api = APIFinder.findAPI(AccumulatorAPI.class);
        assertNotNull(api.getAccumulatorGraphDataByName("a2"));
        assertNotNull(api.getAccumulatorGraphDataByName("a2").getThreshold());
        assertEquals(api.getAccumulatorGraphDataByName("a2").getThreshold().size(), 0);
        assertEquals(api.getAccumulatorGraphDataByName("a2").getThreshold().size(), 0);
        assertEquals(api.getAccumulatorGraphDataByName("a7").getThreshold().size(), 0);

        assertNotNull(api.getAccumulatorGraphDataByName("at1-a"));
        assertNotNull(api.getAccumulatorGraphDataByName("at1-a").getThreshold());
        assertEquals(api.getAccumulatorGraphDataByName("at1-a").getThreshold().size(), 5);

        GuardConfig config = api.getAccumulatorGraphDataByName("at1-a").getThreshold().get(2);
        assertNotNull(config);
        assertNotNull(config.getDirection());
        assertNotNull(config.getStatus());
        assertNotNull(config.getValue());
    }

    private class FakeProducer extends OnDemandStatsProducer implements IStatsProducer {

        private String producerId;

        FakeProducer(String id) {
            super(id, "cat", "sys", new FakeStatsFactory());
            this.producerId = id;
        }

        @Override
        public List getStats() {
            return new ArrayList();
        }

        @Override
        public String getProducerId() {
            return producerId;
        }

        @Override
        public String getCategory() {
            return "";
        }

        @Override
        public String getSubsystem() {
            return "";
        }
    }

    private class FakeStats extends AbstractStats {
        FakeStats(String name) {
            super(name);
        }

        @Override
        public String toStatsString(String aIntervalName, TimeUnit unit) {
            return aIntervalName + " " + unit;
        }
    }

    private class FakeStatsFactory extends AbstractStatsFactory<FakeStats> {

        FakeStatsFactory() {
            super();
        }

        @Override
        public FakeStats createStatsObject(String name) {
            return new FakeStats(name);
        }
    }

    private void registerFakeProducers(String[] names) {
        IProducerRegistry registry = ProducerRegistryFactory.getProducerRegistryInstance();

        for (String name : names) {
            registry.registerProducer(new FakeProducer(name));
        }
    }

    private void createFakeAccumulatoTresholdPairs(String[] names) {
        IProducerRegistry registry = ProducerRegistryFactory.getProducerRegistryInstance();

        for (String name : names) {
            FakeProducer producer = new FakeProducer(name);
            registry.registerProducer(producer);

            createFakeAccumulator(getFakeTieable(name + "-a", name));
            createFakeThreshold(getFakeTieable(name + "-t", name));
        }
    }




    private void createFakeAccumulators(String[] names) {
        createFakeAccumulators(getFakeTieableS(names));
    }

    private void createFakeAccumulators(List<TieablePO> pos) {
        for (TieablePO t : pos) {
                createFakeAccumulator(t);
        }
    }

    private void createFakeAccumulator(TieablePO t) {
        AccumulatorAPI api = APIFinder.findAPI(AccumulatorAPI.class);

        AccumulatorPO po = new AccumulatorPO();

        po.setProducerId(t.getProducerId());
        po.setValueName(t.getValueName());
        po.setName(t.getName());
        po.setUnit(t.getUnit());
        po.setInterval(t.getInterval());
        po.setStatName(t.getStatName());

        try {
            api.createAccumulator(po);
        } catch (APIException e) {
            e.printStackTrace();
        }
    }

    private void createFakeThresholds(String[] names) {
        createFakeThresholds(getFakeTieableS(names));
    }

    private void createFakeThresholds(List<TieablePO> pos) {
        for (TieablePO t : pos) {
            createFakeThreshold(t);
        }
    }

    private void createFakeThreshold(TieablePO t) {
        ThresholdAPI api = APIFinder.findAPI(ThresholdAPI.class);

        ThresholdPO po = new ThresholdPO();

        po.setProducerId(t.getProducerId());
        po.setValueName(t.getValueName());
        po.setName(t.getName());
        po.setUnit(t.getUnit());
        po.setInterval(t.getInterval());
        po.setStatName(t.getStatName());

        po.setGreenDir("below");
        po.setGreenValue("100");
        po.setYellowDir("above");
        po.setYellowValue("100");
        po.setOrangeDir("above");
        po.setOrangeValue("200");
        po.setRedDir("above");
        po.setRedValue("300");
        po.setPurpleDir("above");
        po.setPurpleValue("500");

        try {
            api.createThreshold(po);
        } catch (APIException e) {
            e.printStackTrace();
        }
    }


    private List<TieablePO> getFakeTieableS(String[] names) {
        List<TieablePO> list = new ArrayList<>();
        for (String name : names) {
            list.add(getFakeTieable(name));
        }

        return list;
    }

    private TieablePO getFakeTieable(String name){
        TieablePO po = new TieablePO();

        po.setProducerId("" + Math.random());
        po.setValueName("" + Math.random());
        po.setName(name);
        po.setUnit("");
        po.setInterval(Constants.INTERVAL_FIVE_MINUTES);
        po.setStatName(("" + Math.random()));

        return po;
    }

    private TieablePO getFakeTieable(String name, String producerId){
        if (producerId == null || producerId.length() == 0) {
            return getFakeTieable(name);
        }
        
        TieablePO po = new TieablePO();

        po.setProducerId(producerId);
        po.setValueName(producerId + "-value");
        po.setName(name);
        po.setUnit("");
        po.setInterval(Constants.INTERVAL_FIVE_MINUTES);
        po.setStatName(producerId + "-stat");

        return po;
    }
}
