package net.anotheria.moskito.web;

import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

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
