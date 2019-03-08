package net.anotheria.moskito.webui.dashboards;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.anoplass.api.APIFinder;
import net.anotheria.anoplass.api.APIInitException;
import net.anotheria.moskito.core.accumulation.AccumulatorRepository;
import net.anotheria.moskito.core.config.MoskitoConfiguration;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.accumulators.AccumulatorSetMode;
import net.anotheria.moskito.core.config.dashboards.ChartConfig;
import net.anotheria.moskito.core.config.dashboards.ChartPattern;
import net.anotheria.moskito.core.config.dashboards.DashboardConfig;
import net.anotheria.moskito.core.config.dashboards.DashboardsConfig;
import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.IProducerRegistry;
import net.anotheria.moskito.core.registry.ProducerRegistryAPIFactory;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.webui.accumulators.api.AccumulatorAPI;
import net.anotheria.moskito.webui.accumulators.api.AccumulatorAPIFactory;
import net.anotheria.moskito.webui.accumulators.api.AccumulatorPO;
import net.anotheria.moskito.webui.dashboards.api.DashboardAO;
import net.anotheria.moskito.webui.dashboards.api.DashboardAPI;
import net.anotheria.moskito.webui.dashboards.api.DashboardAPIFactory;
import net.anotheria.moskito.webui.dashboards.api.DashboardChartAO;
import net.anotheria.moskito.webui.gauges.api.GaugeAPI;
import net.anotheria.moskito.webui.gauges.api.GaugeAPIFactory;
import net.anotheria.moskito.webui.producers.api.ProducerAPI;
import net.anotheria.moskito.webui.producers.api.ProducerAPIFactory;
import net.anotheria.moskito.webui.threshold.api.ThresholdAPI;
import net.anotheria.moskito.webui.threshold.api.ThresholdAPIFactory;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class getDashboardTest {

    private DashboardAPI api;
    private DashboardConfig testDashboard;

    @BeforeClass
    public static void setup() {
        System.setProperty("JUNITTEST", "true");
        APIFinder.setMockingEnabled(true);
    }

    @AfterClass
    public static void shutDownTests() {
        System.clearProperty("JUNITTEST");
        APIFinder.cleanUp();
        ProducerRegistryAPIFactory.resetForUnitTest();
        ProducerRegistryFactory.reset();
        MoskitoConfigurationHolder.resetConfiguration();
    }

    @Before
    public void startUp() {
        APIFinder.addAPIFactory(AccumulatorAPI.class, new AccumulatorAPIFactory());
        APIFinder.addAPIFactory(GaugeAPI.class, new GaugeAPIFactory());
        APIFinder.addAPIFactory(ProducerAPI.class, new ProducerAPIFactory());
        APIFinder.addAPIFactory(ThresholdAPI.class, new ThresholdAPIFactory());

        ProducerRegistryAPIFactory.resetForUnitTest();
        ProducerRegistryFactory.reset();
        AccumulatorRepository.resetForUnitTests();

        try {
            api = new DashboardAPIFactory().createAPI();
            api.init();
        } catch (APIInitException e) {
            fail("Could not init DashboeardAPIImpl");
            e.printStackTrace();
        }
    }

    @Before
    public void prepareMoskitoConfig() {
        MoskitoConfigurationHolder.resetConfiguration();
        MoskitoConfiguration moskitoConfiguration = MoskitoConfigurationHolder.getConfiguration();

        testDashboard = new DashboardConfig();
        testDashboard.setName("TestDashboard");
        DashboardsConfig dashboardsConfig = new DashboardsConfig();
        dashboardsConfig.setDashboards(new DashboardConfig[]{testDashboard});

        moskitoConfiguration.setDashboardsConfig(dashboardsConfig);
        MoskitoConfigurationHolder.INSTANCE.setConfiguration(moskitoConfiguration);
    }

    @After
    public void shutDown() {
        APIFinder.cleanUp();
        ProducerRegistryAPIFactory.resetForUnitTest();
        ProducerRegistryFactory.reset();
    }

    @Test
    public void testProducersConfigImmutability() {
        registerFakeProducers(new String[]{"pr1", "pr2", "pr3", "pr4", "pr4_1", "pr4_2"});

        DashboardConfig controlDashboard = new DashboardConfig();
        controlDashboard.setName("ControlDashboard");
        String[] producers = new String[]{"pr1", "pr2", "pr3"};
        String[] producerNamePatterns = new String[]{"pr3", "pr4(.*)", "pr2"};

        controlDashboard.setProducers(producers);
        testDashboard.setProducers(producers);
        controlDashboard.setProducerNamePatterns(producerNamePatterns);
        testDashboard.setProducerNamePatterns(producerNamePatterns);

        try {
            api.getDashboard(testDashboard.getName());

            assertArrayEquals(testDashboard.getProducers(), controlDashboard.getProducers());
            assertArrayEquals(testDashboard.getProducerNamePatterns(), controlDashboard.getProducerNamePatterns());

        } catch (APIException e) {
            fail("Expected dashboard with " + testDashboard.getName() + " name");
            e.printStackTrace();
        }
    }

    @Test
    public void testChartsConfigImmutability() {
        createFakeAccumulators(new String[]{"a1", "a1_1", "a2", "a2_1", "a2_2", "a3", "a3_1", "a3_2", "a3_3",
                "a4", "a4_1", "a4_2", "a4_3", "a4_4", "a5", "a5_1", "a5_2", "a5_3", "a5_4", "a5_5"});

        ChartConfig cc1 = new ChartConfig(); cc1.setCaption("cc1"); cc1.setAccumulators(new String[]{"a1"});
        ChartConfig cc2 = new ChartConfig(); cc2.setCaption("cc2"); cc2.setAccumulators(new String[]{"a2_1", "a2_2"});
        ChartConfig cc3 = new ChartConfig(); cc3.setCaption("cc3"); cc3.setAccumulators(new String[]{"a1", "a2", "a3", "a4"});
        ChartConfig[] chartConfigs = new ChartConfig[]{cc1, cc2, cc3};

        ChartPattern cp1 = new ChartPattern(); cp1.setCaption("cp1"); cp1.setAccumulatorPatterns(new String[]{"a1"}); cp1.setMode(AccumulatorSetMode.COMBINED);
        ChartPattern cp2 = new ChartPattern(); cp2.setCaption("cp2"); cp2.setAccumulatorPatterns(new String[]{"a2(.*)", "a3(.*)"}); cp2.setMode(AccumulatorSetMode.COMBINED);
        ChartPattern cp3 = new ChartPattern(); cp3.setCaption("cp3"); cp3.setAccumulatorPatterns(new String[]{"a4_(.*)", "a5"}); cp3.setMode(AccumulatorSetMode.MULTIPLE);
        ChartPattern[] chartPatterns = new ChartPattern[]{cp1, cp2, cp3};

        DashboardConfig controlDashboard = new DashboardConfig();
        controlDashboard.setName("ControlDashboard");

        controlDashboard.setCharts(chartConfigs);
        testDashboard.setCharts(chartConfigs);
        controlDashboard.setChartPatterns(chartPatterns);
        testDashboard.setChartPatterns(chartPatterns);

        try {
            api.getDashboard(testDashboard.getName());

            assertArrayEquals(testDashboard.getCharts(), controlDashboard.getCharts());
            assertArrayEquals(testDashboard.getChartPatterns(), controlDashboard.getChartPatterns());

        } catch (APIException e) {
            fail("Expected dashboard with " + testDashboard.getName() + " name");
            e.printStackTrace();
        }
    }

    @Test
    public void testProducerListOrder() {
        registerFakeProducers(new String[]{"pr1", "pr2", "pr3", "pr4", "pr4_1", "pr4_2"});

        String[] producers = new String[]{"pr1", "pr2", "pr3"};
        String[] producerNamePatterns = new String[]{"pr2", "pr4(.*)", "pr5"};

        testDashboard.setProducers(producers);
        testDashboard.setProducerNamePatterns(producerNamePatterns);

        try {
            DashboardAO dashboard = api.getDashboard(testDashboard.getName());
            String[] retProducers = dashboard.getProducers().toArray(new String[0]);

            assertTrue(retProducers.length >= producers.length);

            for (int i = 0; i < producers.length; i++) {
                assertEquals(retProducers[i], producers[i]);
            }

        } catch (APIException e) {
            fail("Expected dashboard with " + testDashboard.getName() + " name");
            e.printStackTrace();
        }
    }

    @Test
    public void testChartsListOrder() {
        createFakeAccumulators(new String[]{"a1", "a1_1", "a2", "a2_1", "a2_2", "a3", "a3_1", "a3_2", "a3_3",
                "a4", "a4_1", "a4_2", "a4_3", "a4_4", "a5", "a5_1", "a5_2", "a5_3", "a5_4", "a5_5"});

        ChartConfig cc1 = new ChartConfig(); cc1.setCaption("cc1"); cc1.setAccumulators(new String[]{"a1"});
        ChartConfig cc2 = new ChartConfig(); cc2.setCaption("cc2"); cc2.setAccumulators(new String[]{"a2_1", "a2_2"});
        ChartConfig cc3 = new ChartConfig(); cc3.setCaption("cc3"); cc3.setAccumulators(new String[]{"a1", "a2", "a3", "a4"});
        ChartConfig[] chartConfigs = new ChartConfig[]{cc1, cc2, cc3};

        ChartPattern cp1 = new ChartPattern(); cp1.setCaption("cp1"); cp1.setAccumulatorPatterns(new String[]{"a1"}); cp1.setMode(AccumulatorSetMode.COMBINED);
        ChartPattern cp2 = new ChartPattern(); cp2.setCaption("cp2"); cp2.setAccumulatorPatterns(new String[]{"a2(.*)", "a3(.*)"}); cp2.setMode(AccumulatorSetMode.COMBINED);
        ChartPattern cp3 = new ChartPattern(); cp3.setCaption("cp3"); cp3.setAccumulatorPatterns(new String[]{"a4_(.*)", "a5"}); cp3.setMode(AccumulatorSetMode.MULTIPLE);
        ChartPattern[] chartPatterns = new ChartPattern[]{cp1, cp2, cp3};

        testDashboard.setCharts(chartConfigs);
        testDashboard.setChartPatterns(chartPatterns);

        try {
            DashboardAO dashboard = api.getDashboard(testDashboard.getName());
            DashboardChartAO[] retCharts = dashboard.getCharts().toArray(new DashboardChartAO[0]);

            assertTrue(retCharts.length >= chartConfigs.length);

            for (int i = 0; i < chartConfigs.length; i++) {
                assertEquals(retCharts[i].getCaption(), chartConfigs[i].getCaption());
                assertArrayEquals(retCharts[i].getChart().getNames().toArray(new String[0]), chartConfigs[i].getAccumulators());
            }

        } catch (APIException e) {
            fail("Expected dashboard with " + testDashboard.getName() + " name");
            e.printStackTrace();
        }
    }

    @Test
    public void testProducerNamePatternsWork() {
        registerFakeProducers(new String[]{"pr1", "pr2", "pr3", "pr4", "pr4_1", "pr4_2", "pr5"});

        String[] producers = new String[]{"pr1", "pr2", "pr3"};
        String[] producerNamePatterns = new String[]{"pr4", "pr4(.*)", "pr6"};

        testDashboard.setProducers(producers);
        testDashboard.setProducerNamePatterns(producerNamePatterns);

        try {
            DashboardAO dashboard = api.getDashboard(testDashboard.getName());
            List<String> retProducers = dashboard.getProducers();

            assertTrue(retProducers.containsAll(Arrays.asList(producers)));
            assertTrue(retProducers.contains("pr4"));
            assertTrue(retProducers.containsAll(Arrays.asList("pr4_1", "pr4_2")));

            assertFalse(retProducers.contains("pr5"));
            assertFalse(retProducers.contains("pr6"));

        } catch (APIException e) {
            fail("Expected dashboard with " + testDashboard.getName() + " name");
            e.printStackTrace();
        }

    }

    @Test
    public void testChartPatternsWork() {
        createFakeAccumulators(new String[]{"a1", "a1_1", "a2", "a2_1", "a2_2", "a3", "a3_1", "a3_2", "a3_3",
                "a4", "a4_1", "a4_2", "a4_3", "a4_4", "a5", "a5_1", "a5_2", "a5_3", "a5_4", "a5_5"});

        ChartConfig cc1 = new ChartConfig(); cc1.setCaption("cc1"); cc1.setAccumulators(new String[]{"a1"});
        ChartConfig cc2 = new ChartConfig(); cc2.setCaption("cc2"); cc2.setAccumulators(new String[]{"a2_1", "a2_2"});
        ChartConfig cc3 = new ChartConfig(); cc3.setCaption("cc3"); cc3.setAccumulators(new String[]{"a1", "a2", "a3", "a4"});
        ChartConfig[] chartConfigs = new ChartConfig[]{cc1, cc2, cc3};

        ChartPattern cp1 = new ChartPattern(); cp1.setCaption(""); cp1.setAccumulatorPatterns(new String[]{"a5_1", "a5_2"}); cp1.setMode(AccumulatorSetMode.COMBINED);
        ChartPattern cp2 = new ChartPattern(); cp2.setCaption("cp2"); cp2.setAccumulatorPatterns(new String[]{"a1"}); cp2.setMode(AccumulatorSetMode.COMBINED);
        ChartPattern cp3 = new ChartPattern(); cp3.setCaption("cp3"); cp3.setAccumulatorPatterns(new String[]{"a2(.*)", "a3(.*)"}); cp3.setMode(AccumulatorSetMode.COMBINED);
        ChartPattern cp4 = new ChartPattern(); cp4.setCaption("cp4"); cp4.setAccumulatorPatterns(new String[]{"a4_(.*)", "a5"}); cp4.setMode(AccumulatorSetMode.MULTIPLE);
        ChartPattern[] chartPatterns = new ChartPattern[]{cp1, cp2, cp3, cp4};

        testDashboard.setCharts(chartConfigs);
        testDashboard.setChartPatterns(chartPatterns);

        try {
            DashboardAO dashboard = api.getDashboard(testDashboard.getName());

            Map<String, List> mappedCharts = new HashMap<>();
            for (DashboardChartAO chart : dashboard.getCharts()) {
                mappedCharts.put(chart.getCaption(), chart.getChart().getNames());
            }

            // asssert that "@charts" part from config is working
            for (ChartConfig chartConfig : chartConfigs) {
                assertTrue(mappedCharts.containsKey(chartConfig.getCaption()));
                assertEquals(mappedCharts.get(chartConfig.getCaption()).size(), chartConfig.getAccumulators().length);
                assertTrue(mappedCharts.get(chartConfig.getCaption()).containsAll(Arrays.asList(chartConfig.getAccumulators())));
            }


            String generated = cp1.getAccumulatorPatterns()[0] + " " + cp1.getAccumulatorPatterns()[1];
            //caption generation
            assertTrue(mappedCharts.containsKey(generated));
            assertEquals(mappedCharts.get(generated).size(), cp1.getAccumulatorPatterns().length);
            assertTrue(mappedCharts.get(generated).containsAll(Arrays.asList(cp1.getAccumulatorPatterns())));


            //patterns work
            assertTrue(mappedCharts.keySet().containsAll(Arrays.asList("cp2", "cp3"))); //captions in COPBINED mode
            assertTrue(mappedCharts.keySet().containsAll(Arrays.asList("a4_1", "a4_2", "a4_3", "a4_4", "a5"))); //captions in MULTIPLE mode
            assertNull(mappedCharts.get("a5_5"));


            String[] expected;
            expected = new String[]{"a1"};
            assertEquals(mappedCharts.get("cp2").size(), expected.length);
            assertTrue(mappedCharts.get("cp2").containsAll(Arrays.asList(expected)));

            expected = new String[]{"a2", "a2_1", "a2_2", "a3", "a3_1", "a3_2", "a3_3"};
            assertEquals(mappedCharts.get("cp3").size(), expected.length);
            assertTrue(mappedCharts.get("cp3").containsAll(Arrays.asList(expected)));

            //chart generation in MULTIPLE mode. 1 accumulatior -> 1 chart
            for (String name : new String[]{"a4_1", "a4_2", "a4_3", "a4_4", "a5"}) {
                assertEquals(mappedCharts.get(name).size(), 1);
                assertEquals(mappedCharts.get(name).get(0), name);
            }

        } catch (APIException e) {
            fail("Expected dashboard with " + testDashboard.getName() + " name");
            e.printStackTrace();
        }
    }

    private class FakeProducer implements IStatsProducer {

        private String id;

        FakeProducer(String id) {
            this.id = id;
        }

        @Override
        public List getStats() {
            return null;
        }

        @Override
        public String getProducerId() {
            return id;
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

    private void registerFakeProducers(String[] names) {
        IProducerRegistry registry = ProducerRegistryFactory.getProducerRegistryInstance();

        for (String name : names) {
            registry.registerProducer(new FakeProducer(name));
        }
    }

    private void createFakeAccumulators(String[] names) {
        AccumulatorAPI api = APIFinder.findAPI(AccumulatorAPI.class);

        for (String name : names) {
            AccumulatorPO acc = new AccumulatorPO();

            acc.setProducerId("");
            acc.setValueName("");
            acc.setName(name);
            acc.setUnit("");
            acc.setInterval(Constants.INTERVAL_FIVE_MINUTES);
            acc.setStatName("");

            try {
                api.createAccumulator(acc);
            } catch (APIException e) {
                e.printStackTrace();
            }
        }
    }
}
