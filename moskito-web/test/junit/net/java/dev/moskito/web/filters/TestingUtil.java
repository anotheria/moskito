package net.java.dev.moskito.web.filters;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import net.anotheria.anoprise.mocking.MockFactory;
import net.anotheria.anoprise.mocking.Mocking;

public class TestingUtil {
	public static FilterConfig createFilterConfig(){
		FilterConfig config = MockFactory.createMock(FilterConfig.class, new ServletConfigGetParameter());
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
	
	public static class FilterChainDoFilter implements Mocking{
		public void doFilter(ServletRequest req, ServletResponse response) throws ServletException{
			//do nothing
		}
	}
}
