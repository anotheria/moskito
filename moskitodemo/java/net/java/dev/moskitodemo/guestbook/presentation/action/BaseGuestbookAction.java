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
package net.java.dev.moskitodemo.guestbook.presentation.action;

import javax.servlet.http.HttpServletRequest;

import net.anotheria.maf.action.AbstractAction;
import net.anotheria.util.NumberUtils;
import net.java.dev.moskitodemo.guestbook.business.IAuthorizationService;
import net.java.dev.moskitodemo.guestbook.business.ICommentService;
import net.java.dev.moskitodemo.guestbook.business.MonitorableAuthorizationServiceFactory;
import net.java.dev.moskitodemo.guestbook.business.MonitorableCommentServiceFactory;

public abstract class BaseGuestbookAction extends AbstractAction{
	public static final String BEAN_AUTHORIZATION = "guestbook.Authorization";
	
	public static final String PARAM_COMMENT_ID = "pComment";
	
	private static IAuthorizationService authorizationService;
	private static ICommentService commentService;
	
	private String myProducerId;

	static{
		authorizationService = MonitorableAuthorizationServiceFactory.getAuthorizationService();
		commentService = MonitorableCommentServiceFactory.getCommentService();
	}
	
	protected BaseGuestbookAction(){
		super();
	}
	
	private String extractClassName(){
		String fullName = getClass().getName();
		int lastDot = fullName.lastIndexOf('.');
		return fullName.substring(lastDot+1);
	}
	
	protected boolean isAuthorized(HttpServletRequest req){
		Boolean authorization = (Boolean)req.getSession().getAttribute(BEAN_AUTHORIZATION);
		return authorization!=null && authorization.equals(Boolean.TRUE);
	}
	
	protected void authorizeUser(HttpServletRequest req){
		req.getSession().setAttribute(BEAN_AUTHORIZATION, Boolean.TRUE);
	}
	
	protected ICommentService getCommentService(){
		return commentService;
	}
	
	protected IAuthorizationService getAuthorizationService(){
		return authorizationService;
	}
	
	protected static String obfuscateEmail(String anEmail){
		if (anEmail==null || anEmail.length()==0)
			return "";
		int atIndex = anEmail.indexOf("@");
		if (atIndex==-1)
			return x(anEmail);
		String firstPart = "NoSpam-"+anEmail.substring(0, atIndex+1); 
		int dotIndex = anEmail.lastIndexOf('.');
		if (dotIndex==-1)
			return firstPart+x(anEmail.substring(atIndex)+1);
		return firstPart+x(anEmail.substring(atIndex+1, dotIndex))+anEmail.substring(dotIndex);
	}
	
	private static String x(String src){
		String x = "";
		for (int i=0; i<src.length(); i++){
			x+="#";
		}
		return x;
	}
	
	protected String makeDateString(long timestamp){
		return NumberUtils.makeDigitalDateStringLong(timestamp)+" "+NumberUtils.makeTimeString(timestamp);
	}

	//@Override
	public String getProducerId() {
		if (myProducerId==null)
			myProducerId = "gbook."+extractClassName();
		return myProducerId;
	}

	//@Override
	public String getSubsystem() {
		return "guestbook";
	}


}
