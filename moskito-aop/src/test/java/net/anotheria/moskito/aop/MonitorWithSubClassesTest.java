package net.anotheria.moskito.aop;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import net.anotheria.moskito.aop.annotation.DontMonitor;
import net.anotheria.moskito.aop.annotation.withsubclasses.MonitorWithSubClasses;
import net.anotheria.moskito.aop.util.MoskitoUtils;
import net.anotheria.moskito.core.registry.IProducerRegistry;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import org.junit.Before;
import org.junit.Test;

/**
 * @author bvanchuhov
 */
public class MonitorWithSubClassesTest {

	@Before
	public void before() {
		ProducerRegistryFactory.getProducerRegistryInstance().cleanup();
	}

	@MonitorWithSubClasses
	private abstract static class ParentClassAnnotated {
		abstract void execute();
	}

	private static class TestClassImplementation extends ParentClassAnnotated {
		@Override
		public void execute() {
		}
	}

	@Test
	public void monitorOnParentClassTest() throws Exception {
		final ParentClassAnnotated child = new TestClassImplementation();
		child.execute();

		IProducerRegistry producerRegistry = ProducerRegistryFactory.getProducerRegistryInstance();
		assertThat("error!", producerRegistry.getProducer(MoskitoUtils.producerName(TestClassImplementation.class.getName())), notNullValue());
	}


	@Test
	public void monitorOnParentClassTest2() throws Exception {
		final ParentClassAnnotated child = new ParentClassAnnotated() {
			@Override void execute() {
			}
		};
		child.execute();
		IProducerRegistry producerRegistry = ProducerRegistryFactory.getProducerRegistryInstance();
		assertThat("error!", producerRegistry.getProducer(MoskitoUtils.producerName(child.getClass().getName())), notNullValue());
	}


	@Test
	public void monitorOnSuperParentClassTest() throws Exception {
		final ParentClassNotAnnotated child = new SomeOtherImpl();
		child.execute();
		IProducerRegistry producerRegistry = ProducerRegistryFactory.getProducerRegistryInstance();
		assertThat("error!", producerRegistry.getProducer(MoskitoUtils.producerName(SomeOtherImpl.class.getName())), notNullValue());
	}

	@Test
	public void monitorOnSuperParentClassTest2() throws Exception {
		final ParentClassNotAnnotated child = new SomeOtherImpl();
		child.executeSomethingElse();
		IProducerRegistry producerRegistry = ProducerRegistryFactory.getProducerRegistryInstance();
		assertThat("error!", producerRegistry.getProducer(MoskitoUtils.producerName(SomeOtherImpl.class.getName())), nullValue());
	}

	@Test
	public void monitorOnSuperDontMonitorOnChildTest() throws Exception {
		final SupperParentClassAnnotated child = new DontMonitoredImpl();
		child.execute();
		IProducerRegistry producerRegistry = ProducerRegistryFactory.getProducerRegistryInstance();
		assertThat("error!", producerRegistry.getProducer(MoskitoUtils.producerName(DontMonitoredImpl.class.getName())), nullValue());
	}


	/**
	 * Monitored superParentClass.!
	 */
	@MonitorWithSubClasses
	private abstract static class SupperParentClassAnnotated {
		abstract void execute();
	}

	/**
	 * Another - not monitored superclass!
	 */
	private abstract static class ParentClassNotAnnotated extends SupperParentClassAnnotated {
		abstract void executeSomethingElse();
	}

	/**
	 * Implementation...
	 */
	private static final class SomeOtherImpl extends ParentClassNotAnnotated {
		@Override
		public void execute() {
		}

		@Override
		public void executeSomethingElse() {
		}
	}

	/**
	 * Not monitored implementation!
	 */
	private static class DontMonitoredImpl extends SupperParentClassAnnotated {
		@DontMonitor
		@Override
		public void execute() {
		}
	}

}
