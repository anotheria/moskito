package net.anotheria.moskito.web;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;

import static org.mockito.Mockito.mock;

public class TestingUtil {
	public static FilterConfig createFilterConfig(){
		return createFilterConfig2(/*new ServletConfigGetParameter()*/);
	}
	
	public static FilterConfig createFilterConfig2(/*Mocking ... mockings*/){
		FilterConfig config = mock(FilterConfig.class);
		//FilterConfig config = MockFactory.createMock(FilterConfig.class, mockings);
		return config;
	}

	public static FilterChain createFilterChain(){
		FilterChain chain = mock(FilterChain.class);//MockFactory.createMock(FilterChain.class, new FilterChainDoFilter());
		return chain;
	}
	    /*
	public static class ServletConfigGetParameter implements Mocking{
		public String getInitParameter(String name) {
			return null;
		}
	}     */
	/*
	
	public static class ServletConfigGetLimitParameter implements Mocking{
		private String value ;
		public ServletConfigGetLimitParameter(String aValue){
			value = aValue;
		}
		
		public String getInitParameter(String name) {
			return value;
		}
	}
	  */
	/*
	public static class FilterChainDoFilter implements Mocking{
		public void doFilter(ServletRequest req, ServletResponse response) throws ServletException{
			//do nothing
		}
	} */
}
