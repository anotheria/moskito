package net.anotheria.moskito.aop.annotation_inheritance;

import net.anotheria.moskito.aop.annotation.Accumulate;
import net.anotheria.moskito.aop.annotation.Accumulates;
import net.anotheria.moskito.aop.annotation.Monitor;
import net.anotheria.moskito.aop.util.MoskitoUtils;
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

/**
 * @author bvanchuhov
 */
public class AnnotationInheritanceTest {

	/**
	 * Config anno - with no Monitor anno in it.
	 */
	@Accumulates ( {
			@Accumulate (name = "Config.A", valueName = "A", intervalName = "1m"),
			@Accumulate (name = "Config.B", valueName = "B", intervalName = "5m")
	})
	@Accumulate (name = "Config.C", valueName = "C", intervalName = "1m")
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
	 * Monitor providen as meta-annotation.
	 */
	@Monitor
	@Accumulates ( {
			@Accumulate (name = "Config.A", valueName = "A", intervalName = "1m"),
			@Accumulate (name = "Config.B", valueName = "B", intervalName = "5m")
	})
	@Accumulate (name = "Config.C", valueName = "C", intervalName = "1m")
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
		performCheck(new MonitorAsMetaAnnotationOnClass(), MonitorAsMetaAnnotationOnClass.class);
	}

	@Test
	public void testMonitoringAsMetaAnnotationOnMethod()  {
		performCheck(new MonitorAsMetaAnnotationOnMethod(), MonitorAsMetaAnnotationOnMethod.class);
	}

	@Test
	public void testMonitorAsClassAnnotation() {
		performCheck(new DirectMonitorAnnotationOnThisClassAndAccumulateOnMethod(), DirectMonitorAnnotationOnThisClassAndAccumulateOnMethod.class);
	}


	@Test
	public void testMonitorAsClassAnnotationWhichAccumulateOnMethod() {
		performCheck(new DirectMonitorAnnotationOnThisClassAndAccumulateOnMethod(), DirectMonitorAnnotationOnThisClassAndAccumulateOnMethod.class);
	}

	@MonitorAsMetaAnnotation
	@interface ThirdLevel{}

	@ThirdLevel
	private  class  ThirdLevelClass implements TestingInterface{
		@Override
		public void execute() {

		}
	}


	@Test /// TODO : What about N level of Monitor aNNO ?? how to deal - correclty without N pointCuts add?
	public void test3dLvl() {
		TestingInterface test3 = new ThirdLevelClass();
		test3.execute();
		IProducerRegistry producerRegistry = ProducerRegistryFactory.getProducerRegistryInstance();
		assertThat("Producer  found! - should not!", producerRegistry.getProducer(MoskitoUtils.producerName(ThirdLevelClass.class.getName())), nullValue());


	}

	// checking
	private <Impl extends TestingInterface> void performCheck(Impl instance, Class<Impl> clazz) {
		instance.execute();

		IProducerRegistry producerRegistry = ProducerRegistryFactory.getProducerRegistryInstance();
		assertThat("Producer not found!", producerRegistry.getProducer(MoskitoUtils.producerName(clazz.getName())), notNullValue());

		AccumulatorRepository accumulatorRepository = AccumulatorRepository.getInstance();
		assertThat("Config.A - NULL", accumulatorRepository.getByName("Config.A"), notNullValue());
		assertThat("Config.B - NULL", accumulatorRepository.getByName("Config.B"), notNullValue());
		assertThat("Config.C - NULL", accumulatorRepository.getByName("Config.C"), notNullValue());
	}







}
