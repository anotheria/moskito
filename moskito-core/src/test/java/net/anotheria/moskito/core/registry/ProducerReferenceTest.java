package net.anotheria.moskito.core.registry;

import junit.framework.Assert;
import net.anotheria.moskito.core.producers.IStatsProducer;
import org.junit.Test;

import java.util.List;

/**
 * {@link net.anotheria.moskito.core.registry.ProducerReference} test.
 *
 * @author Alex Osadchy
 */
public class ProducerReferenceTest {

    @Test(expected = IllegalArgumentException.class)
    public void shouldThrowIllegalArgumentException() {
        new ProducerReference(null);
    }

    @Test
    public void shouldBeEquals() {
        IStatsProducer producer = createTestProducer("test_producer_id");

        ProducerReference ref_1 = new ProducerReference(producer);
        ProducerReference ref_2 = new ProducerReference(producer);

        Assert.assertNotSame("References should have different identities", ref_1, ref_2);
        Assert.assertEquals("References should be equal", ref_1, ref_2);
    }

    /**
     * Creates {@link net.anotheria.moskito.core.producers.IStatsProducer} for testing.
     *
     * @param producerId producer id
     * @return {@link net.anotheria.moskito.core.producers.IStatsProducer}
     */
    private IStatsProducer createTestProducer(final String producerId) {
        return new IStatsProducer() {
            @Override
            public List getStats() {
                return null;
            }

            @Override
            public String getProducerId() {
                return producerId;
            }

            @Override
            public String getCategory() {
                return null;
            }

            @Override
            public String getSubsystem() {
                return null;
            }
        };
    }
}