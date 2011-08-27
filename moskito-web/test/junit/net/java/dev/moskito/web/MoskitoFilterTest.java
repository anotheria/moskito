package net.java.dev.moskito.web;

import javax.servlet.FilterConfig;

import org.junit.Test;
import static org.junit.Assert.*;

public class MoskitoFilterTest {
	@Test public void testProducerId(){
		assertEquals("TestFilter", new TestFilter().getProducerId());
	}
	
	//this test checks whether illegal parameter crashes limit parsing.
	@Test public void testLimitParsing() throws Exception{
		TestFilter filter = new TestFilter();
		FilterConfig config = TestingUtil.createFilterConfig(new TestingUtil.ServletConfigGetLimitParameter("bla"));
		filter.init(config);
		//if we passed the config has been read properly
	}

	//this test checks whether illegal parameter crashes limit parsing.
	@Test public void testLimitParsing2() throws Exception{
		TestFilter filter = new TestFilter();
		FilterConfig config = TestingUtil.createFilterConfig(new TestingUtil.ServletConfigGetLimitParameter("5"));
		filter.init(config);
		//if we passed the config has been read properly
	}
}
