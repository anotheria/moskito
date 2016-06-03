package net.anotheria.moskito.webui.shared.bean;

import net.anotheria.moskito.core.decorators.value.DoubleValueAO;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

@Ignore
public class DoubleValueBeanTest {
	@Test public void testFormatting(){
		DoubleValueAO b1 = new DoubleValueAO("foo", 0.0);
		assertEquals("0.000", b1.getValue());

		DoubleValueAO b3 = new DoubleValueAO("foo", 1.0);
		assertEquals("1.000", b3.getValue());

		DoubleValueAO b2 = new DoubleValueAO("foo", 1.111);
		assertEquals(b2.getValue().length(), 5);
	
		b2 = new DoubleValueAO("foo", 1.11);
		assertEquals(b2.getValue().length(), 5);

		b2 = new DoubleValueAO("foo", 1.1);
		assertEquals(b2.getValue().length(), 5);

		b2 = new DoubleValueAO("foo", 1.1111);
		assertEquals(b2.getValue().length(), 5);

	}
}
