package net.java.dev.moskito.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TestServlet extends MoskitoHttpServlet{

	private boolean throwErrors;
	
	public TestServlet(){
		this(false);
	}
	
	public TestServlet(boolean aThrowErrors){
		throwErrors = aThrowErrors;
	} 
	
	@Override
	protected void moskitoDoDelete(HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException {
		if (throwErrors)
			throw new ServletException("Error requested");
	}

	@Override
	protected void moskitoDoGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		if (throwErrors)
			throw new ServletException("Error requested");
	}

	@Override
	protected void moskitoDoHead(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		if (throwErrors)
			throw new ServletException("Error requested");
	}

	@Override
	protected void moskitoDoOptions(HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException {
		if (throwErrors)
			throw new ServletException("Error requested");
	}

	@Override
	protected void moskitoDoPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		if (throwErrors)
			throw new ServletException("Error requested");
	}

	@Override
	protected void moskitoDoPut(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		if (throwErrors)
			throw new ServletException("Error requested");
	}

	@Override
	protected void moskitoDoTrace(HttpServletRequest req,
			HttpServletResponse res) throws ServletException, IOException {
		if (throwErrors)
			throw new ServletException("Error requested");
	}

	public boolean useShortStatList(){ return false; }

	@Override
	public String getProducerId() {
		return "TestServlet";
	}
	
	
}
