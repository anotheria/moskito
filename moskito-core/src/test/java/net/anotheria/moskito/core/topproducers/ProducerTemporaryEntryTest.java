package net.anotheria.moskito.core.topproducers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProducerTemporaryEntryTest {

	@Test
	public void testInsertIntoEmptyList() {
		ProducerTemporaryEntry root = new ProducerTemporaryEntry("root", 10);
		ProducerTemporaryEntry followup = new ProducerTemporaryEntry("followup", 5);

		root.insert(followup);

		assertEquals(1, root.getSize());
		assertEquals(10, root.getValue());
		assertEquals(5, root.getNext().getValue());
	}

	@Test
	public void testInsertingElementLargerThanRoot() {
		ProducerTemporaryEntry root = new ProducerTemporaryEntry("root", 10);
		ProducerTemporaryEntry first = new ProducerTemporaryEntry("first", 5);
		root.insert(first);

		ProducerTemporaryEntry followup = new ProducerTemporaryEntry("followup", 50);

		root.insert(followup);

		assertEquals(2, root.getSize());
		assertEquals(50, root.getValue());
		assertEquals(10, root.getNext().getValue());
		assertEquals(5, root.getNext().getNext().getValue());

	}

	@Test
	public void testInsert() {
		ProducerTemporaryEntry root = new ProducerTemporaryEntry("root", 10);
		ProducerTemporaryEntry followup = new ProducerTemporaryEntry("followup", 5);
		ProducerTemporaryEntry inserted = new ProducerTemporaryEntry("inserted", 7);

		root.insert(followup);

		assertEquals(1, root.getSize());
		assertEquals(10, root.getValue());
		assertEquals(5, root.getNext().getValue());

		root.insert(inserted);
		assertEquals(2, root.getSize());
		assertEquals(10, root.getValue());
		assertEquals(7, root.getNext().getValue());
		assertEquals(5, root.getNext().getNext().getValue());

	}

	@Test
	public void testSizeCalculation() {
		ProducerTemporaryEntry root = new ProducerTemporaryEntry("root", 10);
		ProducerTemporaryEntry first = new ProducerTemporaryEntry("first", 20);
		ProducerTemporaryEntry second = new ProducerTemporaryEntry("second", 5);
		ProducerTemporaryEntry second2 = new ProducerTemporaryEntry("second2", 5);
		ProducerTemporaryEntry third = new ProducerTemporaryEntry("third", 1);


		root.insert(first, second, second2, third);

		assertEquals("first", root.getProducerId());
		assertEquals(20, root.getValue());
		assertEquals(4, root.getScore());
		assertEquals("root", root.getNext().getProducerId());
		assertEquals(10, root.getNext().getValue());
		assertEquals(3, root.getNext().getScore());


		root.insert(new ProducerTemporaryEntry("fourth", 0));
		assertEquals(5, root.getScore());
		assertEquals(4, root.getNext().getScore());

		ProducerTemporaryEntry intermediate = new ProducerTemporaryEntry("1.5", 15);
		root.insert(intermediate);
		assertEquals(6, root.getScore());
		assertEquals(5, intermediate.getScore());
	}

}
