package net.anotheria.moskito.core.tracer;

import net.anotheria.moskito.core.config.MoskitoConfiguration;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.config.tracing.ShrinkingStrategy;
import net.anotheria.moskito.core.config.tracing.TracingConfiguration;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 07.05.15 23:28
 */
public class TracerTest {
	@Test
	public void test20_22(){
		testResize(20, 22);
	}

	@Test
	public void test50_55(){
		testResize(50, 55);
	}

	public void testResize(int max, int tolerated){

		//prepare config
		MoskitoConfiguration configuration = new MoskitoConfiguration();
		TracingConfiguration tc = new TracingConfiguration();
		tc.setMaxTraces(max);
		tc.setShrinkingStrategy(ShrinkingStrategy.FIFO);
		configuration.setTracingConfig(tc);
		MoskitoConfigurationHolder.INSTANCE.setConfiguration(configuration);

		Tracer tracer = new Tracer("tracer");
		assertEquals(tracer.getEntryCount(), 0);

		//ensure our config works
		assertEquals(MoskitoConfigurationHolder.getConfiguration().getTracingConfig().getMaxTraces(), max);
		assertEquals(MoskitoConfigurationHolder.getConfiguration().getTracingConfig().getToleratedTracesAmount(), tolerated);

		//now fill first 20 traces
		for (int i=0; i<max; i++){
			Trace t = new Trace();
			t.setCall(String.valueOf(i));
			tracer.addTrace(t, tolerated, max);
		}

		assertEquals(tracer.getEntryCount(), max);

		for (int i=max; i<tolerated; i++){
			Trace t = new Trace();
			t.setCall(String.valueOf(i));
			tracer.addTrace(t, tolerated, max);
		}

		assertEquals(tracer.getEntryCount(), tolerated);
		assertEquals(tracer.getTraces().get(0).getCall(), "0");
		String call = tracer.getTraces().get(tracer.getTraces().size()-1).getCall();

		//now force overflow.
		Trace overflow = new Trace();
		overflow.setCall("OVERFLOW");
		tracer.addTrace(overflow, tolerated, max);


		assertEquals(max, tracer.getEntryCount());
		assertEquals(String.valueOf(tolerated - max + 1), tracer.getTraces().get(0).getCall());
		assertEquals("Ensure we have same last element, ", overflow.getCall(), tracer.getTraces().get(tracer.getTraces().size() - 1).getCall());



	}
}
