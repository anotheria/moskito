package net.anotheria.moskito.core.registry;

import net.anotheria.moskito.core.producers.IStatsProducer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

/**
 * {@link net.anotheria.moskito.core.registry.ProducerReference} test.
 *
 * @author Alex Osadchy
 */
public class ProducerReferenceTest {

    @Test
    public void shouldThrowIllegalArgumentException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> new ProducerReference(null));
    }

    @Test
    public void shouldBeEquals() {
        IStatsProducer producer = createTestProducer("test_producer_id");

        ProducerReference ref_1 = new ProducerReference(producer);
        ProducerReference ref_2 = new ProducerReference(producer);

        Assertions.assertNotSame(ref_1, ref_2, "References should have different identities");
        Assertions.assertEquals(ref_1, ref_2, "References should be equal");
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