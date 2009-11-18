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
package net.java.dev.moskito.webui.decorators.predefined;

public class ActionStatsDecorator extends RequestOrientedStatsDecorator{
	
	private static final String EXPLANATIONS[] = {
		"Total number of requests to an action (in the defined interval or since start, depending on your interval selection).",
		"Total amount of time spent in the action. Although, if the called action is waiting for something to be transported through network or from disk, the value is not equal to spent processor time, this value is usually an important indicator to determine how much some user-clicks cost.",
		"Number of concurrent requests to the action. This value is not of much interest for time intervals, since it will be any value which was set at the moment of the interval update (and can be even negative since interval updates are fires unsynchronized to prevent performance loss). However, with the default interval (since start) selected it will tell you how many requests are served in the moment.",
		"Max concurrent requests. Unlike the CR value, this value is interesting for intervals; it gives you the info how much parallel load the action suffers.",
		"The minimum amount of time (in milliseconds) spent in an action. For most use-cases you can expect this value to be pretty low or even zero.",
		"The maximum amount of time (in milliseconds) spent in an action.",
		"The average amount of time spent in a action. This method will give you the average duration of a request. This is especially interesting if you have different load through the day, by comparing or drawing for example the 5 mins value of AVG you can determine how well your system handles different load. This value is calculated by simple division time / requests and can be slightly incorrent, if you have very many requests which have short duration. ",
		"The duration of the last request.",
		"Total number of uncaught errors of the action (Of course only the doExecute / moskitoDoExecute are monitored).",
		"Error Rate in Percent",
	};

	
	public ActionStatsDecorator(){
		super("Action", CAPTIONS, SHORT_EXPLANATIONS, EXPLANATIONS);
	}
}
