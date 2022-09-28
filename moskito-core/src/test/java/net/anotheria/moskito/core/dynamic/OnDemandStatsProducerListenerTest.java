package net.anotheria.moskito.core.dynamic;

import net.anotheria.moskito.core.predefined.ServiceStats;
import net.anotheria.moskito.core.predefined.ServiceStatsFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OnDemandStatsProducerListenerTest {

    @Test
    public void testEventNotificationDoesHappenWithListener() throws Exception{
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
    public void testEventNotificationDoesntHappenWithoutListener() throws Exception{
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

    class TestOnDemandStatsProducerListener implements OnDemandStatsProducerListener{

        private  HashSet<String> calledStatNames = new java.util.HashSet<>();

        @Override
        public void notifyStatCreated(OnDemandStatsProducer producer, String statName) {
            calledStatNames.add(statName);
        }

        public boolean hasBeenCalled(String statName){
            return calledStatNames.contains(statName);
        }
    }
}
