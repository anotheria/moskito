package net.java.dev.moskito.webui.bean;

import org.junit.Test;
import static org.junit.Assert.*;

public class DoubleValueBeanTest {
	@Test public void testFormatting(){
		DoubleValueBean b1 = new DoubleValueBean("foo", 0.0);
		assertEquals("0.000", b1.getValue());

		DoubleValueBean b3 = new DoubleValueBean("foo", 1.0);
		assertEquals("1.000", b3.getValue());

		DoubleValueBean b2 = new DoubleValueBean("foo", 1.111);
		assertEquals(b2.getValue().length(), 5);
	
		b2 = new DoubleValueBean("foo", 1.11);
		assertEquals(b2.getValue().length(), 5);

		b2 = new DoubleValueBean("foo", 1.1);
		assertEquals(b2.getValue().length(), 5);

		b2 = new DoubleValueBean("foo", 1.1111);
		assertEquals(b2.getValue().length(), 5);

	}
}
