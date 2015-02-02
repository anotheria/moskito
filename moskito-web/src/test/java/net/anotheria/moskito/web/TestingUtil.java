package net.anotheria.moskito.web;

import net.anotheria.anoprise.mocking.MockFactory;
import net.anotheria.anoprise.mocking.Mocking;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class TestingUtil {
	public static FilterConfig createFilterConfig(){
		return createFilterConfig(new ServletConfigGetParameter());
	}
	
	public static FilterConfig createFilterConfig(Mocking ... mockings){
		FilterConfig config = MockFactory.createMock(FilterConfig.class, mockings);
		return config;
	}

	public static FilterChain createFilterChain(){
		FilterChain chain = MockFactory.createMock(FilterChain.class, new FilterChainDoFilter());
		return chain;
	}
	
	public static class ServletConfigGetParameter implements Mocking{
		public String getInitParameter(String name) {
			return null;
		}
	}
	
	public static class ServletConfigGetLimitParameter implements Mocking{
		private String value ;
		public ServletConfigGetLimitParameter(String aValue){
			value = aValue;
		}
		
		public String getInitParameter(String name) {
			return value;
		}
	}

	public static class FilterChainDoFilter implements Mocking{
		public void doFilter(ServletRequest req, ServletResponse response) throws ServletException{
			//do nothing
		}
	}
}
