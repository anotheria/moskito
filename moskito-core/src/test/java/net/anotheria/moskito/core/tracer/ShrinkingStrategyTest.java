package net.anotheria.moskito.core.tracer;

import net.anotheria.moskito.core.config.MoskitoConfiguration;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.tracing.ShrinkingStrategy;
import net.anotheria.moskito.core.config.tracing.TracingConfiguration;
import org.junit.Test;

import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 23.03.16 18:57
 */
public class ShrinkingStrategyTest {
	@Test
	public void testFIFO() {
		Tracer tracer = executeTest(ShrinkingStrategy.FIFO);
		//ensure overflow is last element
		assertTrue(tracer.getTraces().get(tracer.getTraces().size()-1).getCall().startsWith("OVERFLOW"));

	}

	@Test
	public void testKEEPLONGEST() {
		Tracer tracer = executeTest(ShrinkingStrategy.KEEPLONGEST);
		//ensure overflow is first (its also the longest) element
		assertTrue(tracer.getTraces().get(0).getCall().startsWith("OVERFLOW"));
		assertEquals(1000, tracer.getTraces().get(0).getDuration());

	}

	public Tracer executeTest(ShrinkingStrategy targetStrategy){

		MoskitoConfiguration configuration = new MoskitoConfiguration();
		TracingConfiguration tc = new TracingConfiguration();
		tc.setMaxTraces(20);
		tc.setShrinkingStrategy(targetStrategy);
		configuration.setTracingConfig(tc);
		MoskitoConfigurationHolder.INSTANCE.setConfiguration(configuration);


		Tracer tracer = new Tracer("tracer", null);
		assertEquals(tracer.getEntryCount(), 0);

		//ensure our config works
		assertEquals(MoskitoConfigurationHolder.getConfiguration().getTracingConfig().getMaxTraces(), 20);
		assertEquals(MoskitoConfigurationHolder.getConfiguration().getTracingConfig().getToleratedTracesAmount(), 22);

		Random rnd = new Random(System.nanoTime());

		//now fill first 22 traces
		for (int i=0; i<22; i++){
			Trace t = new Trace();
			t.setCall(String.valueOf(i));
			t.setDuration(rnd.nextInt(500));
			tracer.addTrace(t, 22, 20);
		}
		assertEquals(tracer.getEntryCount(), 22);

		//now force overflow.
		Trace overflow = new Trace();
		overflow.setCall("OVERFLOW");
		overflow.setDuration(1000);
		overflow.setDuration(1000L);
		tracer.addTrace(overflow, 22, 20);

		assertEquals(20, tracer.getEntryCount());

//		assertEquals("" + (tolerated - max +1), tracer.getTraces().get(0).getCall());
//		assertEquals("Ensure we have same last element, ", overflow.getCall(), tracer.getTraces().get(tracer.getTraces().size() - 1).getCall());

		return tracer;
	}
}
