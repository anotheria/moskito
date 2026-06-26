package net.anotheria.moskito.aop;

import net.anotheria.moskito.aop.annotation.Accumulate;
import net.anotheria.moskito.aop.annotation.DontMonitor;
import net.anotheria.moskito.aop.annotation.Monitor;
import net.anotheria.moskito.aop.annotation.withsubclasses.AccumulateWithSubClasses;
import net.anotheria.moskito.aop.annotation.withsubclasses.AccumulatesWithSubClasses;
import net.anotheria.moskito.aop.annotation.withsubclasses.MonitorWithSubClasses;
import net.anotheria.moskito.aop.util.MoskitoUtils;
import net.anotheria.moskito.core.accumulation.AccumulatedValue;
import net.anotheria.moskito.core.accumulation.Accumulator;
import net.anotheria.moskito.core.accumulation.AccumulatorRepository;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.IProducerRegistry;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.impl.IntervalRegistry;
import net.anotheria.moskito.core.timing.IUpdateable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author sshscp
 */
public class AccumulateWithSubClassesTest {

	@BeforeEach
	public void before() {
		ProducerRegistryFactory.getProducerRegistryInstance().cleanup();
		AccumulatorRepository.resetForUnitTests();
	}

	private interface TestingInterface {
		void execute();
	}

	private static final String PARENT = "ParentAccumulator";
	private static final String REGULAR = "RegularAccumulator";
	private static final String CHILD = "ChildAccumulator";

	@Accumulate(name = PARENT, valueName = "TR", intervalName = "snapshot")
	@MonitorWithSubClasses
	private abstract static class ParentRegularAccumulatorClass implements TestingInterface {
		public void someMethod(){
			//call records should go to PARENT accumulator
		}
	}
	@Accumulate(name = REGULAR, valueName = "TR", intervalName = "snapshot")
	private static class RegularAccumulatorTestClass extends ParentRegularAccumulatorClass {
		@Override
		public void execute() {
			//call records should go to REGULAR accumulator
		}
		void execute2() {
			//call records should go nowhere - method is not monitored!
		}
	}
	private static class ChildAccumulatorTestClass extends RegularAccumulatorTestClass {
		@Override
		@Accumulate(name = CHILD, valueName = "TR", intervalName = "snapshot")
		public void execute() {
			//call records should go to CHILD accumulator
		}
	}
	private static class AnotherChildAccumulatorTestClass extends RegularAccumulatorTestClass {
		@Override
		public void someMethod() {
			//call records should go nowhere - there is no suitable accumulator!
		}
	}

	/**
	 * Check how {@link Accumulate} and {@link MonitorWithSubClasses} work together.
	 */
	@Test
	public void testAccumulateAnnotation() {
		//execute all available methods
		new RegularAccumulatorTestClass().execute();//+1 to REGULAR
		new RegularAccumulatorTestClass().execute2();// nothing
		new RegularAccumulatorTestClass().someMethod();//+1 to PARENT

		new ChildAccumulatorTestClass().execute();//+1 to CHILD
		new ChildAccumulatorTestClass().execute2();// nothing
		new ChildAccumulatorTestClass().someMethod();//+1 to PARENT

		new AnotherChildAccumulatorTestClass().execute();//+1 to REGULAR
		new AnotherChildAccumulatorTestClass().execute2();// nothing
		new AnotherChildAccumulatorTestClass().someMethod();// nothing

		//check producers presence
		assertNotNull(getProducer(ParentRegularAccumulatorClass.class), "Producer not found!");
		assertNotNull(getProducer(RegularAccumulatorTestClass.class), "Producer not found!");
		assertNotNull(getProducer(ChildAccumulatorTestClass.class), "Producer not found!");
		assertNotNull(getProducer(AnotherChildAccumulatorTestClass.class), "Producer not found!");


		final ExpectedAccumulatorValues expected = new ExpectedAccumulatorValues();

		//check initial calls result
		checkAccumulatedValues(expected.set(PARENT, 2).set(REGULAR, 2).set(CHILD, 1));

		//check interval without calls
		checkAccumulatedValues(expected.reset());//all zeroes

		//check each of available methods
		final int counter = 5;

		for (int i = 0; i < counter; i++)
			new RegularAccumulatorTestClass().execute();//+1 to REGULAR
		checkAccumulatedValues(expected.reset().set(REGULAR, counter));

		for (int i = 0; i < counter; i++)
			new RegularAccumulatorTestClass().execute2();//nothing
		checkAccumulatedValues(expected.reset());

		for (int i = 0; i < counter; i++)
			new RegularAccumulatorTestClass().someMethod();//+1 to PARENT
		checkAccumulatedValues(expected.reset().set(PARENT, counter));



		for (int i = 0; i < counter; i++)
			new ChildAccumulatorTestClass().execute();//+1 to CHILD
		checkAccumulatedValues(expected.reset().set(CHILD, counter));

		for (int i = 0; i < counter; i++)
			new ChildAccumulatorTestClass().execute2();//nothing
		checkAccumulatedValues(expected.reset());

		for (int i = 0; i < counter; i++)
			new ChildAccumulatorTestClass().someMethod();//+1 to PARENT
		checkAccumulatedValues(expected.reset().set(PARENT, counter));



		for (int i = 0; i < counter; i++)
			new AnotherChildAccumulatorTestClass().execute();//+1 to REGULAR
		checkAccumulatedValues(expected.reset().set(REGULAR, counter));

		for (int i = 0; i < counter; i++)
			new AnotherChildAccumulatorTestClass().execute2();//nothing
		checkAccumulatedValues(expected.reset());

		for (int i = 0; i < counter; i++)
			new AnotherChildAccumulatorTestClass().someMethod();//nothing
		checkAccumulatedValues(expected.reset());

	}

	private static class ExpectedAccumulatorValues {
		private final Map<String, Long> values = new HashMap<>();

		ExpectedAccumulatorValues set(String accName, long value) {
			values.put(accName, value);
			return this;
		}
		Set<Map.Entry<String, Long>> entries() {
			return values.entrySet();
		}
		ExpectedAccumulatorValues reset() {
			for (Map.Entry<String, Long> entry : values.entrySet())
				entry.setValue(0L);
			return this;
		}
	}

	private void checkAccumulatedValues(ExpectedAccumulatorValues values) {
		forceIntervalUpdate("snapshot");
		for (Map.Entry<String, Long> expectedValue : values.entries()) {
			Accumulator accumulator = getAccumulator(expectedValue.getKey());
			assertNotNull(accumulator, "Accumulator not found!");

			List<AccumulatedValue> stats = accumulator.getValues();
			assertTrue(stats.size() > 0, "Accumulated values are absent!");

			Long expectedCounter = expectedValue.getValue();
			assertEquals(expectedCounter.toString(), stats.get(stats.size() - 1 ).getValue(), "Expected other value!");
		}
	}

	private static Accumulator getAccumulator(String name){
		return AccumulatorRepository.getInstance().getByName(name);
	}

	private static IStatsProducer<?> getProducer(Class<? extends TestingInterface> clazz) {
		final IProducerRegistry producerRegistry = ProducerRegistryFactory.getProducerRegistryInstance();
		final String producerId = MoskitoUtils.producerName(clazz.getName());
		return producerRegistry.getProducer(producerId);
	}

	private static void forceIntervalUpdate(String intervalName){
		IntervalRegistry registry = IntervalRegistry.getInstance();
		Interval interval = registry.getInterval(intervalName);
		((IUpdateable)interval).update();
	}


	@AccumulatesWithSubClasses({
			@AccumulateWithSubClasses(name = "AccumulateWithSubClasses", valueName = "TR", intervalName = "snapshot")
	})
	@MonitorWithSubClasses
	private abstract static class ParentMonitoredClass implements TestingInterface {
		@Override public void execute() {
			//call records should go to PARENT accumulator
		}
		public void someMethod(){
			//call records should go to PARENT accumulator
		}
	}
	/**
	 * This class should gain its own accumulator automatically.
	 */
	private static class RegularMonitoredClass extends ParentMonitoredClass {
		@Override public void execute() {
			//call records should go to REGULAR accumulator
		}
	}
	/**
	 * This class should gain its own accumulator automatically.
	 */
	private static class ChildMonitoredClass extends RegularMonitoredClass {
		@Override public void someMethod(){
			//call records should go to own "child" accumulator
		}
	}

	/**
	 * Check how {@link AccumulateWithSubClasses} and {@link MonitorWithSubClasses} work together.
	 */
	@Test
	public void testAccumulateWithSubClasses() throws Exception {
		//execute all available methods
		new ParentMonitoredClass(){}.execute();//+1 to PARENT
		new ParentMonitoredClass(){}.someMethod();//+1 to PARENT
		new RegularMonitoredClass().execute();//+1 to REGULAR
		new RegularMonitoredClass().someMethod();//+1 to PARENT
		new ChildMonitoredClass().execute();//+1 to REGULAR
		new ChildMonitoredClass().someMethod();//+1 to CHILD

		//check producers presence
		assertNotNull(getProducer(ParentMonitoredClass.class), "Producer not found!");
		assertNotNull(getProducer(RegularMonitoredClass.class), "Producer not found!");
		assertNotNull(getProducer(ChildMonitoredClass.class), "Producer not found!");

		//check accumulators count
		assertEquals(3, AccumulatorRepository.getInstance().getAccumulators().size(), "Wrong accumulators count!");


		//find out generated accumulator names
		final String PARENT_ACC = findAccumulatorName(ParentMonitoredClass.class.getSimpleName());
		final String REGULAR_ACC = findAccumulatorName(RegularMonitoredClass.class.getSimpleName());
		final String CHILD_ACC = findAccumulatorName(ChildMonitoredClass.class.getSimpleName());


		final ExpectedAccumulatorValues expected = new ExpectedAccumulatorValues();

		//check initial calls result
		checkAccumulatedValues(expected.set(PARENT_ACC, 3).set(REGULAR_ACC, 2).set(CHILD_ACC, 1));

		//check interval without calls
		checkAccumulatedValues(expected.reset());//all zeroes

		//check each of available methods
		final int counter = 5;

		for (int i = 0; i < counter; i++)
			new ParentMonitoredClass(){}.execute();//+1 to PARENT_ACC
		checkAccumulatedValues(expected.reset().set(PARENT_ACC, counter));

		for (int i = 0; i < counter; i++)
			new ParentMonitoredClass(){}.someMethod();//+1 to PARENT_ACC
		checkAccumulatedValues(expected.reset().set(PARENT_ACC, counter));


		for (int i = 0; i < counter; i++)
			new RegularMonitoredClass().execute();//+1 to REGULAR_ACC
		checkAccumulatedValues(expected.reset().set(REGULAR_ACC, counter));

		for (int i = 0; i < counter; i++)
			new RegularMonitoredClass().someMethod();//+1 to PARENT_ACC
		checkAccumulatedValues(expected.reset().set(PARENT_ACC, counter));


		for (int i = 0; i < counter; i++)
			new ChildMonitoredClass().execute();//+1 to REGULAR_ACC
		checkAccumulatedValues(expected.reset().set(REGULAR_ACC, counter));

		for (int i = 0; i < counter; i++)
			new ChildMonitoredClass().someMethod();//+1 to CHILD_ACC
		checkAccumulatedValues(expected.reset().set(CHILD_ACC, counter));

		ChildMonitoredClass child = new ChildMonitoredClass(){
			@Override public void execute() {
				// this call should be recorded into its very own accumulator created
				// specially for this anonymous class
			}
		};
		child.execute();

		String ANONYMOUS_CLASS_ACC_NAME = findAccumulatorName(child.getClass().getName());
		if (ANONYMOUS_CLASS_ACC_NAME == null) { //for the case when found annotation does not have name
			String className = child.getClass().getName();
			className = className.substring(className.lastIndexOf('.') + 1);
			ANONYMOUS_CLASS_ACC_NAME = findAccumulatorName(className);
		}

		checkAccumulatedValues(expected.reset().set(ANONYMOUS_CLASS_ACC_NAME, 1));

	}

	private static String findAccumulatorName(String part) {
		final List<String> accNames = new ArrayList<>();
		for (Accumulator acc : AccumulatorRepository.getInstance().getAccumulators()) {
			accNames.add(acc.getName());
		}
		return find(accNames, part);
	}

	private static String find(List<String> names, String part) {
		for (String name : names)
			if (name != null && name.contains(part))
				return name;
		return null;
	}


	@AccumulatesWithSubClasses({})
	@MonitorWithSubClasses
	private abstract static class ParentMonitoredClassWithoutAccumulators implements TestingInterface {
		void execute2() {
		}
	}
	private static class ChildWithoutAccumulator extends ParentMonitoredClassWithoutAccumulators {
		@Override public void execute() {
		}
	}
	@AccumulateWithSubClasses(valueName = "TR", intervalName = "1h")
	private static class ChildWithAccumulator extends ParentMonitoredClassWithoutAccumulators {
		@Override public void execute() {
		}
	}

	@Test
	public void testCornerCases() {
		new ChildWithoutAccumulator().execute();//don't accumulate
		new ChildWithoutAccumulator().execute2();//don't accumulate

		//check producers presence
		assertNotNull(getProducer(ChildWithoutAccumulator.class), "Producer not found!");
		assertNotNull(getProducer(ParentMonitoredClassWithoutAccumulators.class), "Producer not found!");

		//check accumulators count
		assertEquals(0, AccumulatorRepository.getInstance().getAccumulators().size(), "Wrong accumulators count!");

		new ChildWithAccumulator().execute();//goes to accumulator
		new ChildWithAccumulator().execute2();//don't accumulate

		assertNotNull(getProducer(ChildWithAccumulator.class), "Producer not found!");
		assertEquals(1, AccumulatorRepository.getInstance().getAccumulators().size(), "Wrong accumulators count!");

		forceIntervalUpdate("1h");
		Accumulator accumulator = getAccumulator(findAccumulatorName(ChildWithAccumulator.class.getSimpleName()));
		assertNotNull(accumulator, "Accumulator not found!");

		List<AccumulatedValue> stats = accumulator.getValues();
		assertTrue(stats.size() > 0, "Accumulated values are absent!");
		assertEquals("1", stats.get(stats.size() - 1 ).getValue(), "Expected other value!");
	}


	/**
	 * Purpose of this test is to check that @Monitor annotation does not prevent @MonitorWithSubClasses
	 * from inheriting further(TestClassC) and that @DontMonitor works as expected(TestClassD)
	 */
	@Test
	@Disabled
	public void testMonitorOverriding() {
		final String interval = "snapshot";

		@MonitorWithSubClasses
		@AccumulateWithSubClasses(name = "inherited-TR", valueName = "TR", intervalName = interval)
		class TestClassA implements TestingInterface {
			@Override public void execute() {
			}
		}
		@Monitor
		class TestClassB extends TestClassA {
			@Override public void execute() {
			}
		}
		class TestClassC extends TestClassB {
			@Override public void execute() {
			}
		}
		class TestClassD extends TestClassB {
			@DontMonitor
			@Override public void execute() {
			}
		}

		new TestClassA().execute();
		new TestClassB().execute();
		new TestClassC().execute();
		new TestClassD().execute();

		//check accumulators count
		assertEquals(3, AccumulatorRepository.getInstance().getAccumulators().size(), "Wrong accumulators count!");

		forceIntervalUpdate(interval);

		for (Class clazz : new Class[]{TestClassA.class, TestClassB.class, TestClassC.class}) {
			//check producers presence
			assertNotNull(getProducer(TestClassA.class), "Producer not found!");

			//check accumulator presence
			Accumulator accumulator = getAccumulator(findAccumulatorName(clazz.getSimpleName()));
//			accumulator.tieToStats();
			assertNotNull(accumulator, "Accumulator not found!");

			//check accumulated values
			List<AccumulatedValue> stats = accumulator.getValues();
			assertEquals(1, stats.size(), "Expected single accumulated value!");
			assertEquals("1", stats.get(0).getValue(), "Expected other value!");
		}

		assertNull(getProducer(TestClassD.class), "Producer found!");
		assertNull(findAccumulatorName(TestClassD.class.getSimpleName()), "Accumulator found!");

	}
}
