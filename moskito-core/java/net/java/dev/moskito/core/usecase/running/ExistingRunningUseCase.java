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

/**
 * A currently being recorded use case.
 * @author lrosenberg
 *
 */
public class ExistingRunningUseCase implements RunningUseCase{
	/**
	 * Name of the use case.
	 */
	private String name;
	/**
	 * Root element of the use case.
	 */
	private PathElement root = new PathElement("");
	/**
	 * Current element along the use case path.
	 */
	private PathElement current;
	/**
	 * Creation timestamp.
	 */
	private long created;
	/**
	 * Creates a new ExistingRunningUseCase.
	 * @param aName
	 */
	public ExistingRunningUseCase(String aName){
		name = aName;
		current = root;
		created = System.currentTimeMillis();
	}
	
	@Override public boolean useCaseRunning(){
		return true;
	}
	
	@Override public String toString(){
		return "RunningUseCase: "+name;
	}
	
	public String toDetails(){
		return toString()+": \n"+root.toDetails(1);
	}

	/**
	 * Creates a new element of the use case path. The new use case path element will be recorded as a child of the current use case path element.
	 */
	public PathElement startPathElement(String call){
		PathElement last = current;
		current = new PathElement(call);
		last.addChild(current);
		return current;
	}
	
	public void endPathElement(){
		current = current.getParent(); 
	}

	public String getUseCasePath(){
		return root.generatePath();
	}
	
	public String getName(){
		return name;
	}

	public long getCreated() {
		return created;
	}
	
	public PathElement getRootElement(){
		return root;
	}
	/**
	 * Returns the first use case path element.
	 * @return
	 */
	public PathElement getFirstElement(){
		return root.getChildren().get(0);
	}
}
