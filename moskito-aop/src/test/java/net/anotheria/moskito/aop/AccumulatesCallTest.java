package net.anotheria.moskito.aop;

import net.anotheria.moskito.core.accumulation.Accumulator;
import net.anotheria.moskito.core.accumulation.AccumulatorRepository;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by Roman Stetsiuk on 9/29/15.
 */
public class AccumulatesCallTest {
    @Test
    public void testAccumulatesClass(){
        AccumulatesAnnotatedClass monitorable = new AccumulatesAnnotatedClass();

        int N = 10;
        for (int i = 0; i < N; i++) {
            monitorable.notAnnotatedFunc();
        }

        monitorable.annotatedFunc();

        List<Accumulator> accumulators= AccumulatorRepository.getInstance().getAccumulators();
        assertEquals(6, accumulators.size());
    }
}
