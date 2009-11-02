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
 * Generic interface for currently executed (and recorded) use cases. There are basically two opportunities, either there is 
 * a running use case, or there is none. In the later case the code which works with use cases don't have to perform null checks, but
 * can rely on fact, that there will be always an instance of  RunningUseCase available. Whether a useCase is running or not can be determined
 * by calling useCaseRunning().
 * @author lrosenberg
 *
 */
public interface RunningUseCase {
	/**
	 * Returns true if there is currently a running use case. In fact, if true is returned, one can safely assume 
	 * that the implementation of this interface one is dealing with is ExistingRunningUseCase. Otherwise its NoRunningUseCase.
	 * @return
	 * @see ExistingRunningUseCase
	 * @see NoRunningUseCase
	 */
	boolean useCaseRunning();
}
