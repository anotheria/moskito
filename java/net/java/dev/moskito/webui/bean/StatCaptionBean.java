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
package net.java.dev.moskito.webui.bean;

import net.anotheria.util.StringUtils;

/**
 * Caption for a stat bean. 
 * @author lrosenberg.
 */
public class StatCaptionBean {
	private String caption;
	private String explanation;
	private String shortExplanation;
	
	public StatCaptionBean(){
		
	}
	
	public StatCaptionBean(String aCaption, String aShortExplanation, String anExplanation){
		caption = aCaption;
		shortExplanation = aShortExplanation;
		explanation = anExplanation;
	}
	
	public String getCaption(){
		return caption;
	}
	
	@Override public String toString(){
		return caption;
	}
	
	public void setCaption(String aCaption){
		caption = aCaption;
	}

	public String getExplanation() {
		return explanation;
	}

	public void setExplanation(String explanation) {
		this.explanation = explanation;
	}

	public String getShortExplanation() {
		return shortExplanation;
	}
	
	public String getShortExplanationLowered(){
		return shortExplanation.toLowerCase();
	}

	public void setShortExplanation(String shortExplanation) {
		this.shortExplanation = shortExplanation;
	}

	public String getJsVariableName(){
		return getJsVariableName(getCaption());
	}
	
	private static final char[] TO_REMOVE = new char[]{' ', '\t', '\n', '\r', '/'};
	public static final String getJsVariableName(String caption){
		return "v"+StringUtils.removeChars(caption, TO_REMOVE);
		
	}

}
