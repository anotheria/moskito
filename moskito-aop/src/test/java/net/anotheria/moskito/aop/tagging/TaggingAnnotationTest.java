package net.anotheria.moskito.aop.tagging;

import net.anotheria.moskito.core.context.MoSKitoContext;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * @author esmakula
 */
public class TaggingAnnotationTest {
	@Test
	public void testTaggedMethods() {
		TaggedClass taggedClass = new TaggedClass();
		taggedClass.tagReturnValue();
		taggedClass.tagParameter("singleParam");
		taggedClass.tagMultipleParameters("multipleParamFirst", 22, "multipleParamThird");

		Map<String, String> tags = MoSKitoContext.getTags();

		assertEquals(4, tags.size());
		assertEquals("value", tags.get("tagReturn"));
		assertEquals("singleParam", tags.get("tagParamSingle"));
		assertEquals("multipleParamFirst", tags.get("tagMultipleParamFirst"));
		assertEquals("22", tags.get("tagMultipleParamSecond"));
	}
}
