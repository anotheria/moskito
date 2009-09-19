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
package net.java.dev.moskitodemo.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TomcatServlet extends HttpServlet{
		
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
	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
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
	
	private void writeResponse(HttpServletResponse res, int amountOfTimeSlept) throws IOException{
		res.setContentType("text/html");
		PrintWriter writer = res.getWriter();
		writer.write("<html>\n");
		writeHtmlHead(writer, "SimpleServlet for Tomcat Response");
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