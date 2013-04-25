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
package net.anotheria.moskito.web.filters;

import net.anotheria.moskito.web.MoskitoFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * This filter measures calls by referer.
 * @author lrosenberg
 *
 */
public class RefererFilter extends MoskitoFilter {
	
	public static final String HTTP_PROTOCOL = "http://";
	public static final String HTTPS_PROTOCOL = "https://";

	@Override
	protected String extractCaseName(ServletRequest req, ServletResponse res) {
		if (!(req instanceof HttpServletRequest))
			return null;
		HttpServletRequest r = (HttpServletRequest)req;
		String referer = r.getHeader("referer");
		if (referer==null || referer.length()==0)
			return null;
		if (referer.startsWith(HTTP_PROTOCOL))
			referer = referer.substring(HTTP_PROTOCOL.length());
		if (referer.startsWith(HTTPS_PROTOCOL))
			referer = referer.substring(HTTPS_PROTOCOL.length());
		
		String currentServerName = r.getServerName();
		String refererServerName = extractServerName(referer);
		if (currentServerName.equals(refererServerName))
			return "_this_server_";
		
		return referer;
	}

	private static String extractServerName(String referer){
		int end; 
		end = referer.indexOf(':');
		if (end==-1)
			end = referer.indexOf('/');
		if (end==-1)
			return null;
		return referer.substring(0,end);
	}
}
