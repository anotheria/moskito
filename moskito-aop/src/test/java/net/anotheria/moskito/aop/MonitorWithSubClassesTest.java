package net.anotheria.moskito.aop;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

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
	interface ParentInterfaceAnnotated {
		void execute();
	}

	private static class InterfaceImplementation implements ParentInterfaceAnnotated {
		@Override
		public void execute() {
		}
	}

	@Test
	public void monitorOnInterfaceTest() throws Exception {
		final ParentInterfaceAnnotated child = new InterfaceImplementation();
		child.execute();

		IProducerRegistry producerRegistry = ProducerRegistryFactory.getProducerRegistryInstance();
		assertThat("error!", producerRegistry.getProducer(MoskitoUtils.producerName(InterfaceImplementation.class.getName())), notNullValue());
	}


	@Test
	public void monitorOnExtendingInterfaceTest() throws Exception {
		final SomeOtherMarkerInterface child = new SomeOtherImpl();
		child.execute();
		IProducerRegistry producerRegistry = ProducerRegistryFactory.getProducerRegistryInstance();
		assertThat("error!", producerRegistry.getProducer(MoskitoUtils.producerName(SomeOtherImpl.class.getName())), notNullValue());
	}

	@Test
	public void monitorOnExtendingInterfaceTest2() throws Exception {
		final SomeOtherMarkerInterface child = new SomeOtherImpl();
		child.executeSomethingElse();
		IProducerRegistry producerRegistry = ProducerRegistryFactory.getProducerRegistryInstance();
		assertThat("error!", producerRegistry.getProducer(MoskitoUtils.producerName(SomeOtherImpl.class.getName())), nullValue());
	}


	/**
	 * Monitored interface.!
	 */
	@MonitorWithSubClasses
	interface SupperMarkerInterface {
		void execute();
	}

	/**
	 * Another - not monitored interface part....!
	 */
	interface SomeOtherMarkerInterface extends SupperMarkerInterface {
		void executeSomethingElse();
	}

	/**
	 * Implementation...
	 */
	private static final class SomeOtherImpl implements SomeOtherMarkerInterface {
		@Override
		public void execute() {
		}

		@Override
		public void executeSomethingElse() {
		}
	}
}
