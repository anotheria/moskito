/*
 * $Id$
 * 
 * This file is part of the MoSKito software project
 * that is hosted at http://moskito.dev.java.net.
 * 
 * All MoSKito files are distributed under MIT License:
 * 
 * Copyright (c) 2006 The MoSKito Project Team.
 * 
 * Permission is hereby granted, free of charge,
 * to any person obtaining a copy of this software and
 * associated documentation files (the "Software"), 
 * to deal in the Software without restriction, 
 * including without limitation the rights to use,
 * copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit 
 * persons to whom the Software is furnished to do so, 
 * subject to the following conditions:
 * 
 * The above copyright notice and this permission notice
 * shall be included in all copies 
 * or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY
 * OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT 
 * LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS 
 * BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, 
 * WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, 
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR
 * THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */	
package net.anotheria.moskitodemo.servlet;

import net.anotheria.moskito.core.predefined.ServletStats;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.web.MoskitoHttpServlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Random;

/**
 * This is a simple example how to use moskito in a servlet. Basically it provides all the functionality the simple service
 * provider over web.
 * @author another
 *
 */

public class SimpleServlet extends MoskitoHttpServlet {
	
	public static final int MODE_NORMAL_PROCESSING = 1;
	public static final int MODE_RANDOM = 2;
	public static final int MODE_ERROR = 3;
	
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	// I know this is not how you are supposed to write a web-app, but I wanted to keep the example SIMPLE! //
	//////////////////////////////////////////////////////////////////////////////////////////////////////////
	
	private Random rnd;
	
	public void init(){
		rnd = new Random(System.currentTimeMillis());
	}
	
	@Override
	protected void moskitoDoGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		//emulate working
		String showStatsParameterValue = req.getParameter("pShowInterval");
		if (showStatsParameterValue!=null && showStatsParameterValue.length()>0){
			moskitoShowStats(req, res);
			return;
		}
		
		//ok, emulate normal servlet working...
		
		int mode = MODE_NORMAL_PROCESSING;
		try{
			mode = Integer.parseInt(req.getParameter("pMode"));
		}catch(Exception ignored){}
		
		int sleepTime = rnd.nextInt(1000);
		try{
			Thread.sleep(sleepTime);
		}catch(InterruptedException e){
			throw new RuntimeException("Thread interrupted: "+e.getMessage());
		}
		
		if (mode==MODE_ERROR)
			throw new RuntimeException("Emulating exception in processing.");
		
		if (mode==MODE_RANDOM){
			int throwExceptionIf5 = rnd.nextInt(10);
			if (throwExceptionIf5 == 5)
				throw new RuntimeException("Emulating exception in processing.");
		}
		writeResponse(res, sleepTime);
	}

	protected void moskitoShowStats(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String interval = req.getParameter("pShowInterval");
		if (interval=="default"){
			interval = null;
		}
		
		List<IStats> stats = getStats();
		
		res.setContentType("text/html");
		PrintWriter writer = res.getWriter();
		writer.write("<html>\n");
		writeHtmlHead(writer, "SimpleServlet Stats for interval: "+interval);
		writer.write("<body>");
		
		writer.write("<H2>Stats for interval "+interval+" </H2>");
		writer.write("\n<br>\n");
		writer.write("<table border=\"1\" cellpadding=\"1\" cellspacing=\"1\">\n"); 
		writer.write("<tr>");
		writer.write("<th>method</th>");
		writer.write("<th>total request</th>");
		writer.write("<th>total time</th>");
		writer.write("<th>current requests</th>");
		writer.write("<th>max current requests</th>");
		writer.write("<th>errors</th>");
		writer.write("<th>io exc</th>");
		writer.write("<th>servlet exc</th>");
		writer.write("<th>runtime exc</th>");
		writer.write("<th>last time</th>");
		writer.write("<th>min time</th>");
		writer.write("<th>max time</th>");
		writer.write("<th>avg time</th>");
		writer.write("</tr>");
		for (IStats statObject : stats){
			ServletStats stat = (ServletStats)statObject;
			writer.write("<tr>\n");
			writer.write("<td>"+stat.getMethodName()+"</td>");
			writer.write("<td>"+stat.getTotalRequests(interval)+"</td>");
			writer.write("<td>"+stat.getTotalTime(interval)+"</td>");
			writer.write("<td>"+stat.getCurrentRequests(interval)+"</td>");
			writer.write("<td>"+stat.getMaxCurrentRequests(interval)+"</td>");
			writer.write("<td>"+stat.getErrors(interval)+"</td>");
			writer.write("<td>"+stat.getIoExceptions(interval)+"</td>");
			writer.write("<td>"+stat.getServletExceptions(interval)+"</td>");
			writer.write("<td>"+stat.getRuntimeExceptions(interval)+"</td>");
			writer.write("<td>"+stat.getLastRequest(interval)+"</td>");
			writer.write("<td>"+stat.getMinTime(interval)+"</td>");
			writer.write("<td>"+stat.getMaxTime(interval)+"</td>");
			writer.write("<td>"+stat.getAverageRequestDuration(interval)+"</td>");
			writer.write("</tr>\n");
		}
		
		writer.write("</table>\n");
		writer.write("</body></html>");
		writer.close();
		
		
	}
		
	@Override
	protected boolean useShortStatList() {
		return true;
	}
	
	private void writeResponse(HttpServletResponse res, int amountOfTimeSlept) throws IOException{
		res.setContentType("text/html");
		PrintWriter writer = res.getWriter();
		writer.write("<html>\n");
		writeHtmlHead(writer, "SimpleServlet Response");
		writer.write("<body><br>Servlet slept : "+amountOfTimeSlept+"</body></html>");
		writer.close();
	}
	
	private void writeHtmlHead(PrintWriter writer, String title){
		writer.write("\n");
		writer.write("<head>\n");
		writer.write("<title>"+title+"</title>");
		writer.write("<META http-equiv=\"pragma\" content=\"no-cache\">\n");
		writer.write("<META http-equiv=\"Cache-Control\" content=\"no-cache, must-revalidate\">\n");
		writer.write("<META name=\"Expires\" content=\"0\">\n");
		writer.write("<META http-equiv=\"Content-Type\" content=\"text/html; charset=iso-8859-1\">\n");
		writer.write("</head>\n");
	}
	
	
	
}
