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
package net.java.dev.moskito.core.usecase.running;

import java.util.ArrayList;
import java.util.List;

public class PathElement {
	private String call;
	private List<PathElement> children;
	private PathElement parent;
	
	private long duration;
	private boolean aborted;
	
	public PathElement(String aCall){
		call = aCall;
		children = new ArrayList<PathElement>();
	}
	
	public String getCall(){
		return call;
	}
	
	public List<PathElement> getChildren(){
		return children;
	}
	
	public void setParent(PathElement element){
		parent = element;
	}
	
	public PathElement getParent(){
		return parent;
	}
	
	public String toString(){
		StringBuilder ret = new StringBuilder(getCall()).append(" D: ").append(getDuration()).append(" ms");
		if (isAborted())
			ret.append(" aborted.");
		else
			ret.append(".");
		return ret.toString();
	}
	
	public String toDetails(int ident){
		StringBuilder ret = new StringBuilder();
		ret.append(getIdent(ident)).append(this);
		for (PathElement p : children)
			ret.append('\n').append(p.toDetails(ident+1));
		return ret.toString();
	}
	
	private static String getIdent(int ident){
		StringBuilder ret = new StringBuilder();
		for (int i=0; i<ident; i++)
			ret.append('\t');
		return ret.toString();
	}
	
	public void addChild(PathElement p){
		children.add(p);
		p.setParent(this);
	}
	
	public String generatePath(){
		return _generatePath().toString();
	}
	
	private StringBuilder _generatePath(){
		StringBuilder b = new StringBuilder(call);
		if (children.size()>0){
			b.append('[');
			for (int i=0; i<children.size(); i++){
				b.append(children.get(i)._generatePath());
				if (i<children.size()-1)
					b.append(", ");
			}
			b.append(']');
		}
	
		
		return b;
	}

	public boolean isAborted() {
		return aborted;
	}

	public void setAborted(boolean aborted) {
		this.aborted = aborted;
	}

	public void setAborted(){
		aborted = true;
	}
	
	public long getDuration() {
		return duration;
	}

	public void setDuration(long duration) {
		this.duration = duration;
	}

	public void appendToCall(String s){
		call += s;
	}
	
}
