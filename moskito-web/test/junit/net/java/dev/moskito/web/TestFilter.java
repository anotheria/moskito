package net.java.dev.moskito.web;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import net.java.dev.moskito.web.MoskitoFilter;

public class TestFilter extends MoskitoFilter{

	@Override
	protected String extractCaseName(ServletRequest req, ServletResponse res) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public String getProducerId(){
		return super.getProducerId();
	}
}
