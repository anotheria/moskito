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
package net.java.dev.moskito.core.timing;

import net.java.dev.moskito.core.timing.timer.ITimerService;
import net.java.dev.moskito.core.timing.timer.TimerServiceConstantsUtility;
import net.java.dev.moskito.core.timing.timer.TimerServiceFactory;

/**
 * This class implements the UpdateTriggerService. 
 * It depends on ITimerService that will be used to do the work.
 * 
 * @author dvayanu
 */
class UpdateTriggerServiceImpl implements IUpdateTriggerService {
	/**
	 * The time service instance
	 */
	private ITimerService timerService;

	/**
	 * The constructor.
	 */
	UpdateTriggerServiceImpl() {
		timerService = TimerServiceFactory.createTimerService();
	}

	/**
	 * @see net.java.dev.moskito.core.timing.IUpdateTriggerService#addUpdateable(net.java.dev.moskito.core.timing.IUpdateable,int)
	 */
	public void addUpdateable(IUpdateable aUpdateable, int aUpdateSequenceInSeconds) {
		UpdateableWrapper adapter = new UpdateableWrapper(aUpdateable);
		int intervalLength = aUpdateSequenceInSeconds * 1000 / TimerServiceConstantsUtility.getSleepingUnit();
		timerService.addConsumer(adapter, intervalLength);
	}

}
