package net.anotheria.moskito.aop;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import net.anotheria.moskito.core.accumulation.AccumulatorRepositoryCleaner;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.registry.IProducerRegistry;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import net.anotheria.moskito.core.stats.Interval;
import net.anotheria.moskito.core.stats.impl.IntervalRegistry;
import net.anotheria.moskito.core.timing.IUpdateable;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author sshscp
 */
public class AccumulateWithSubClassesTest {

	@Before
	public void before() {
		ProducerRegistryFactory.getProducerRegistryInstance().cleanup();
		AccumulatorRepositoryCleaner.getInstance().cleanAccumulatorRepository();
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
		assertNotNull("Producer not found!", getProducer(ParentRegularAccumulatorClass.class));
		assertNotNull("Producer not found!", getProducer(RegularAccumulatorTestClass.class));
		assertNotNull("Producer not found!", getProducer(ChildAccumulatorTestClass.class));
		assertNotNull("Producer not found!", getProducer(AnotherChildAccumulatorTestClass.class));


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
			assertNotNull("Accumulator not found!", accumulator);

			List<AccumulatedValue> stats = accumulator.getValues();
			assertTrue("Accumulated values are absent!", stats.size() > 0);

			Long expectedCounter = expectedValue.getValue();
			assertEquals("Expected other value!", expectedCounter.toString(), stats.get(stats.size() - 1 ).getValue());
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
		assertNotNull("Producer not found!", getProducer(ParentMonitoredClass.class));
		assertNotNull("Producer not found!", getProducer(RegularMonitoredClass.class));
		assertNotNull("Producer not found!", getProducer(ChildMonitoredClass.class));

		//check accumulators count
		assertEquals("Wrong accumulators count!", 3, AccumulatorRepository.getInstance().getAccumulators().size());


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
		assertNotNull("Producer not found!", getProducer(ChildWithoutAccumulator.class));
		assertNotNull("Producer not found!", getProducer(ParentMonitoredClassWithoutAccumulators.class));

		//check accumulators count
		assertEquals("Wrong accumulators count!", 0, AccumulatorRepository.getInstance().getAccumulators().size());

		new ChildWithAccumulator().execute();//goes to accumulator
		new ChildWithAccumulator().execute2();//don't accumulate

		assertNotNull("Producer not found!", getProducer(ChildWithAccumulator.class));
		assertEquals("Wrong accumulators count!", 1, AccumulatorRepository.getInstance().getAccumulators().size());

		forceIntervalUpdate("1h");
		Accumulator accumulator = getAccumulator(findAccumulatorName(ChildWithAccumulator.class.getSimpleName()));
		assertNotNull("Accumulator not found!", accumulator);

		List<AccumulatedValue> stats = accumulator.getValues();
		assertTrue("Accumulated values are absent!", stats.size() > 0);
		assertEquals("Expected other value!", "1", stats.get(stats.size() - 1 ).getValue());
	}


	/**
	 * Purpose of this test is to check that @Monitor annotation does not prevent @MonitorWithSubClasses
	 * from inheriting further(TestClassC) and that @DontMonitor works as expected(TestClassD)
	 */
	@Test
	@Ignore
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
		assertEquals("Wrong accumulators count!", 3, AccumulatorRepository.getInstance().getAccumulators().size());

		forceIntervalUpdate(interval);

		for (Class clazz : new Class[]{TestClassA.class, TestClassB.class, TestClassC.class}) {
			//check producers presence
			assertNotNull("Producer not found!", getProducer(TestClassA.class));

			//check accumulator presence
			Accumulator accumulator = getAccumulator(findAccumulatorName(clazz.getSimpleName()));
//			accumulator.tieToStats();
			assertNotNull("Accumulator not found!", accumulator);

			//check accumulated values
			List<AccumulatedValue> stats = accumulator.getValues();
			assertEquals("Expected single accumulatd value!", 1, stats.size());
			assertEquals("Expected other value!", "1", stats.get(0).getValue());
		}

		assertNull("Producer found!", getProducer(TestClassD.class));
		assertNull("Accumulator found!", findAccumulatorName(TestClassD.class.getSimpleName()));

	}
}
