package net.anotheria.moskito.aop.annotation_inheritance;

import net.anotheria.moskito.aop.annotation.Accumulate;
import net.anotheria.moskito.aop.annotation.Accumulates;
import net.anotheria.moskito.aop.annotation.Monitor;
import net.anotheria.moskito.aop.util.MoskitoUtils;
import net.anotheria.moskito.core.accumulation.Accumulator;
import net.anotheria.moskito.core.accumulation.AccumulatorRepository;
import net.anotheria.moskito.core.registry.IProducerRegistry;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import org.junit.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * @author bvanchuhov
 */
public class AnnotationInheritanceTest {

	private static String INTERVAL_VALUE = "5m";
	static {
		try {
			INTERVAL_VALUE = Accumulate.class.getMethod("intervalName").getDefaultValue().toString();
		} catch(NoSuchMethodException e) {
			fail("Can't fetch default interval value from @Accumulate!");
		}
	}
	/**
	 * Config anno - with no Monitor anno in it.
	 */
	@Accumulates ( {
			@Accumulate (valueName = "A"),
			@Accumulate (valueName = "B")
	})
	@Accumulate (valueName = "C")
	@Retention (RetentionPolicy.RUNTIME)
	private @interface Config {
	}

	/**
	 * Directly Monitor annotated clazz  with @Config -on class.
	 */
	@Monitor
	@Config
	static class DirectMonitorAnnotationOnThisClass implements TestingInterface {
		public void execute() {
		}
	}

	/**
	 * Directly Monitor annotated clazz  with @Config -on method.
	 */
	@Monitor
	static class DirectMonitorAnnotationOnThisClassAndAccumulateOnMethod implements TestingInterface {
		@Config
		public void execute() {
		}
	}

	/**
	 * Monitor provided as meta-annotation.
	 */
	@Monitor
	@Accumulates ( {
			@Accumulate (valueName = "A"),
			@Accumulate (valueName = "B")
	})
	@Accumulate (valueName = "C")
	@Retention (RetentionPolicy.RUNTIME)
	@Target ( {ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
	private @interface MonitorAsMetaAnnotation {
	}

	/**
	 * Monitor as meta on class.
	 */
	@MonitorAsMetaAnnotation
	static class MonitorAsMetaAnnotationOnClass implements TestingInterface {
		public void execute() {
		}
	}

	/**
	 * Monitor as meta on method.
	 */
	static class MonitorAsMetaAnnotationOnMethod implements TestingInterface {
		@MonitorAsMetaAnnotation
		public void execute() {
		}
	}

	/**
	 * General interface.
	 */
	interface TestingInterface {
		void execute();
	}


	@Test
	public void testMonitoringAsMetaAnnotationOnClass()  {
		performCheck(MonitorAsMetaAnnotationOnClass.class);
	}

	@Test
	public void testMonitoringAsMetaAnnotationOnMethod()  {
		performCheck(MonitorAsMetaAnnotationOnMethod.class);
	}

	@Test
	public void testMonitorAsClassAnnotation() {
		performCheck(DirectMonitorAnnotationOnThisClass.class);
	}

	@Test
	public void testMonitorAsClassAnnotationWhichAccumulateOnMethod() {
		performCheck(DirectMonitorAnnotationOnThisClassAndAccumulateOnMethod.class);
	}

	@MonitorAsMetaAnnotation
	@interface ThirdLevel{}

	@ThirdLevel
	private  class  ThirdLevelClass implements TestingInterface{
		public void execute() {
		}
	}


	@Test /// TODO : What about N level of Monitor aNNO ?? how to deal - correctly without N pointCuts add?
	public void test3dLvl() {
		TestingInterface test3 = new ThirdLevelClass();
		test3.execute();
		IProducerRegistry producerRegistry = ProducerRegistryFactory.getProducerRegistryInstance();
		assertThat("Producer  found! - should not!", producerRegistry.getProducer(MoskitoUtils.producerName(ThirdLevelClass.class.getName())), nullValue());
	}

	// checking
	private <Impl extends TestingInterface> void performCheck(Class<Impl> clazz) {
		try {
			clazz.newInstance().execute();
		} catch(Exception e){
			fail(e.getMessage());
		}

		IProducerRegistry producerRegistry = ProducerRegistryFactory.getProducerRegistryInstance();
		final String producerId = MoskitoUtils.producerName(clazz.getName());

		assertThat("Producer not found!", producerRegistry.getProducer(producerId), notNullValue());

		AccumulatorRepository<?> accumulatorRepository = AccumulatorRepository.getInstance();

		for (String valueName: new String[]{"A","B","C"}) {
			String accumulatorName = producerId + "." + valueName + "." + INTERVAL_VALUE;//@see AbstractMoskitoAspect#formAccumulatorNameForClass
			Accumulator acc = accumulatorRepository.getByName(accumulatorName);
			if (acc == null) {
				accumulatorName = producerId + ".execute."+ valueName + "." + INTERVAL_VALUE;//@see AbstractMoskitoAspect#formAccumulatorNameForMethod
				acc = accumulatorRepository.getByName(accumulatorName);
			}
			assertThat("Accumulator '" + accumulatorName + "' not FOUND!", acc, notNullValue());
		}

	}

}
