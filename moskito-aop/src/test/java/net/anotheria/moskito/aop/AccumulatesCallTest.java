package net.anotheria.moskito.aop;

import net.anotheria.moskito.aop.annotation.Accumulate;
import net.anotheria.moskito.aop.annotation.Accumulates;
import net.anotheria.moskito.aop.annotation.Monitor;
import net.anotheria.moskito.core.accumulation.Accumulator;
import net.anotheria.moskito.core.accumulation.AccumulatorRepository;
import net.anotheria.moskito.core.accumulation.AccumulatorRepositoryCleaner;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Roman Stetsiuk
 */
public class AccumulatesCallTest {

    @Before
    public void before() {
        ProducerRegistryFactory.getProducerRegistryInstance().cleanup();
        AccumulatorRepositoryCleaner.getInstance().cleanAccumulatorRepository();
    }

    @Test
    public void testAccumulatesClass() {
        AccumulatesAnnotatedClass monitorable = new AccumulatesAnnotatedClass();

        monitorable.annotatedFuncAll();
        monitorable.notAnnotatedFunc();
        monitorable.annotatedFuncHolder();
        monitorable.annotatedFuncSingle();

        List<Accumulator> accumulators = AccumulatorRepository.getInstance().getAccumulators();
        assertEquals(9, accumulators.size());
    }

    @Monitor
    @Accumulates({
            @Accumulate(valueName = "Class1", intervalName = "1m"),
            @Accumulate(valueName = "Class2", intervalName = "5m")
    })
    @Accumulate(valueName = "Class3", intervalName = "1m")
    private static class AccumulatesAnnotatedClass {

        public void notAnnotatedFunc() {}

        @Accumulates({
                @Accumulate(valueName = "All1", intervalName = "1m"),
                @Accumulate(valueName = "All2", intervalName = "1m")
        })
        @Accumulate(valueName = "All3", intervalName = "1m")
        public void annotatedFuncAll() {}

        @Accumulate(valueName = "Single", intervalName = "1m")
        public void annotatedFuncSingle() {}

        @Accumulates({
                @Accumulate(valueName = "Holder1", intervalName = "1m"),
                @Accumulate(valueName = "Holder2", intervalName = "5m")
        })
        public void annotatedFuncHolder() {}
    }
}
