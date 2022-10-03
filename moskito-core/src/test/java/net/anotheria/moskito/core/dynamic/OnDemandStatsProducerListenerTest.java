package net.anotheria.moskito.core.dynamic;

import net.anotheria.moskito.core.accumulation.Accumulator;
import net.anotheria.moskito.core.accumulation.AccumulatorRepository;
import net.anotheria.moskito.core.accumulation.AutoAccumulatorDefinition;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.accumulators.AccumulatorsConfig;
import net.anotheria.moskito.core.config.accumulators.AutoAccumulatorConfig;
import net.anotheria.moskito.core.predefined.ServiceStats;
import net.anotheria.moskito.core.predefined.ServiceStatsFactory;
import net.anotheria.moskito.core.registry.IProducerRegistry;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OnDemandStatsProducerListenerTest {

    @Test
    public void testEventNotificationDoesHappenWithListener() throws Exception {
        TestOnDemandStatsProducerListener listener = new TestOnDemandStatsProducerListener();
        OnDemandStatsProducer<ServiceStats> producer = new OnDemandStatsProducer<>(
                "anId", "aCategory", "aSubsystem", ServiceStatsFactory.DEFAULT_INSTANCE
        );
        producer.addListener(listener);

        producer.getStats("first").addRequest();
        producer.getStats("second").addRequest();

        assertTrue(listener.hasBeenCalled("first"));
        assertTrue(listener.hasBeenCalled("second"));
        assertFalse(listener.hasBeenCalled("third"));
    }

    @Test
    public void testEventNotificationDoesntHappenWithoutListener() throws Exception {
        TestOnDemandStatsProducerListener listener = new TestOnDemandStatsProducerListener();
        OnDemandStatsProducer<ServiceStats> producer = new OnDemandStatsProducer<>(
                "anId", "aCategory", "aSubsystem", ServiceStatsFactory.DEFAULT_INSTANCE
        );

        producer.getStats("first").addRequest();
        producer.getStats("second").addRequest();

        assertFalse(listener.hasBeenCalled("first"));
        assertFalse(listener.hasBeenCalled("second"));
        assertFalse(listener.hasBeenCalled("third"));

    }

    /**
     * A test when autoAccumulator has an exact statName but producer doesn't create it, so no accumulator will be created
     */
    @Test
    public void testAccumulatorAutoCreation1() {
        String testStatName = "testStatName";

        // getting an instance of config to add custom autoAccumulators
        AccumulatorsConfig accumulatorsConfig = MoskitoConfigurationHolder.getConfiguration().getAccumulatorsConfig();
        AutoAccumulatorConfig[] autoAccumulatorConfigs = new AutoAccumulatorConfig[1];
        AutoAccumulatorConfig config = new AutoAccumulatorConfig();
        config.setAccumulationAmount(250);
        config.setNamePattern("$PRODUCERNAME");
        config.setProducerNamePattern("anId");
        config.setValueName("aValue");
        config.setIntervalName("default");
        config.setStatNamePattern(testStatName);
        autoAccumulatorConfigs[0] = config;
        accumulatorsConfig.setAutoAccumulators(autoAccumulatorConfigs);

        // getting instance of AccumulatorRepository
        AccumulatorRepository repository = AccumulatorRepository.getInstance();
        AccumulatorRepository.resetForUnitTests();

        // getting instance of producerRegistry to register a producer
        IProducerRegistry producerRegistry = ProducerRegistryFactory.getProducerRegistryInstance();

        // creating producer and adding listener to him
        OnDemandStatsProducer<ServiceStats> producer = new OnDemandStatsProducer<>("anId", "aCategory", "aSubsystem", ServiceStatsFactory.DEFAULT_INSTANCE);
        producerRegistry.registerProducer(producer);

        List<Accumulator> accumulators = repository.getAccumulators();

        assertTrue(accumulators.isEmpty());
    }

    /**
     * A test when autoAccumulator has statNamePattern to create accumulators for every stat in producer.
     * Producer also has a custom stat named "testStatName"
     */
    @Test
    public void testAccumulatorAutoCreation2() throws Exception {
        String testStatName = "testStatName";

        // getting an instance of config to add custom autoAccumulators
        AccumulatorsConfig accumulatorsConfig = MoskitoConfigurationHolder.getConfiguration().getAccumulatorsConfig();
        AutoAccumulatorConfig[] autoAccumulatorConfigs = new AutoAccumulatorConfig[1];
        AutoAccumulatorConfig config = new AutoAccumulatorConfig();
        config.setAccumulationAmount(250);
        config.setNamePattern("$PRODUCERNAME");
        config.setProducerNamePattern("anId");
        config.setValueName("aValue");
        config.setIntervalName("default");
        config.setStatNamePattern("(.*)");
        autoAccumulatorConfigs[0] = config;
        accumulatorsConfig.setAutoAccumulators(autoAccumulatorConfigs);

        // getting instance of AccumulatorRepository
        AccumulatorRepository repository = AccumulatorRepository.getInstance();
        AccumulatorRepository.resetForUnitTests();

        // getting instance of producerRegistry to register a producer
        IProducerRegistry producerRegistry = ProducerRegistryFactory.getProducerRegistryInstance();

        // creating producer and adding listener to him
        OnDemandStatsProducer<ServiceStats> producer = new OnDemandStatsProducer<>("anId", "aCategory", "aSubsystem", ServiceStatsFactory.DEFAULT_INSTANCE);
        producer.getStats(testStatName);
        producerRegistry.registerProducer(producer);

        List<Accumulator> accumulators = repository.getAccumulators();
        boolean containsCumulated = false;
        boolean containsTestStatName = false;

        for (Accumulator accumulator : accumulators) {
            if (accumulator.getTargetStatName().equals("cumulated")) {
                containsCumulated = true;
            }
            if (accumulator.getTargetStatName().equals(testStatName)) {
                containsTestStatName = true;
            }
        }

        // accumulators list is not empty
        assertFalse(accumulators.isEmpty());

        // accumulators list contains "cumulated" accumulator
        assertTrue(containsCumulated);

        // accumulators list contains "testStatName" accumulator
        assertTrue(containsTestStatName);
    }

    /**
     * A test when autoAccumulator has exact statName "testStatName" to create accumulator only for this stat.
     * Producer also has a custom stat named "testStatName"
     */
    @Test
    public void testAccumulatorAutoCreation3() throws Exception {
        String testStatName = "testStatName";

        // getting an instance of config to add custom autoAccumulators
        AccumulatorsConfig accumulatorsConfig = MoskitoConfigurationHolder.getConfiguration().getAccumulatorsConfig();
        AutoAccumulatorConfig[] autoAccumulatorConfigs = new AutoAccumulatorConfig[1];
        AutoAccumulatorConfig config = new AutoAccumulatorConfig();
        config.setAccumulationAmount(250);
        config.setNamePattern("$PRODUCERNAME");
        config.setProducerNamePattern("anId");
        config.setValueName("aValue");
        config.setIntervalName("default");
        config.setStatName(testStatName);
        autoAccumulatorConfigs[0] = config;
        accumulatorsConfig.setAutoAccumulators(autoAccumulatorConfigs);

        // getting instance of AccumulatorRepository
        AccumulatorRepository repository = AccumulatorRepository.getInstance();
        AccumulatorRepository.resetForUnitTests();

        // getting instance of producerRegistry to register a producer
        IProducerRegistry producerRegistry = ProducerRegistryFactory.getProducerRegistryInstance();

        // creating producer and adding listener to him
        OnDemandStatsProducer<ServiceStats> producer = new OnDemandStatsProducer<>("anId", "aCategory", "aSubsystem", ServiceStatsFactory.DEFAULT_INSTANCE);
        producer.getStats(testStatName);
        producerRegistry.registerProducer(producer);

        List<Accumulator> accumulators = repository.getAccumulators();
        boolean containsTestStatName = false;

        for (Accumulator accumulator : accumulators) {
            if (accumulator.getTargetStatName().equals(testStatName)) {
                containsTestStatName = true;
            }
        }

        // accumulators list is not empty
        assertFalse(accumulators.isEmpty());

        // accumulators list contains "testStatName" accumulator
        assertTrue(containsTestStatName);
    }

    /**
     * A test when autoAccumulator has exact statName "testStatName" and statNamePattern.
     * In this case exact statName is in action. So only accumulator for "testStatName" will be created
     */
    @Test
    public void testAccumulatorAutoCreation4() throws Exception {
        String testStatName = "testStatName";

        // getting an instance of config to add custom autoAccumulators
        AccumulatorsConfig accumulatorsConfig = MoskitoConfigurationHolder.getConfiguration().getAccumulatorsConfig();
        AutoAccumulatorConfig[] autoAccumulatorConfigs = new AutoAccumulatorConfig[1];
        AutoAccumulatorConfig config = new AutoAccumulatorConfig();
        config.setAccumulationAmount(250);
        config.setNamePattern("$PRODUCERNAME");
        config.setProducerNamePattern("anId");
        config.setValueName("aValue");
        config.setIntervalName("default");
        config.setStatName(testStatName);
        config.setStatNamePattern("(.*)"); // also added statNamePattern
        autoAccumulatorConfigs[0] = config;
        accumulatorsConfig.setAutoAccumulators(autoAccumulatorConfigs);

        // getting instance of AccumulatorRepository
        AccumulatorRepository repository = AccumulatorRepository.getInstance();
        AccumulatorRepository.resetForUnitTests();

        // getting instance of producerRegistry to register a producer
        IProducerRegistry producerRegistry = ProducerRegistryFactory.getProducerRegistryInstance();

        // creating producer and adding listener to him
        OnDemandStatsProducer<ServiceStats> producer = new OnDemandStatsProducer<>("anId", "aCategory", "aSubsystem", ServiceStatsFactory.DEFAULT_INSTANCE);
        producer.getStats(testStatName);
        producerRegistry.registerProducer(producer);

        List<Accumulator> accumulators = repository.getAccumulators();
        boolean containsTestStatName = false;
        boolean containsCumulated = false;

        for (Accumulator accumulator : accumulators) {
            if (accumulator.getTargetStatName().equals(testStatName)) {
                containsTestStatName = true;
            }
            if(accumulator.getTargetStatName().equals("cumulated")) {
                containsCumulated = true;
            }
        }

        // accumulators list is not empty
        assertFalse(accumulators.isEmpty());

        // accumulators list doesnt contain "cumulated"
        assertFalse(containsCumulated);

        // accumulators list contains "testStatName" accumulator
        assertTrue(containsTestStatName);
    }
}

class TestOnDemandStatsProducerListener implements OnDemandStatsProducerListener {

    private HashSet<String> calledStatNames = new java.util.HashSet<>();

    @Override
    public void notifyStatCreated(OnDemandStatsProducer producer, String statName) {
        calledStatNames.add(statName);


    }

    public boolean hasBeenCalled(String statName) {
        return calledStatNames.contains(statName);
    }

}
