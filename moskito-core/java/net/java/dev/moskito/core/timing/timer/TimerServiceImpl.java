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
package net.java.dev.moskito.core.timing.timer;


import java.util.ArrayList;
import java.util.List;

/**
 * This class implements a basic timer. The timer sleeps unified time, then
 * checks if any consumer want to have a "tick". Each consumer can specify
 * the number of "ticks" in which interval they want to be called. <B>
 *
 * @author     Leon Rosenberg
 */

public class TimerServiceImpl implements Runnable, ITimerService {

	private static TimerServiceImpl instance;

	private TimerServiceImpl() {
		objects = new ArrayList<TimerObject>(30);
	}

	public static TimerServiceImpl getInstance() {
		if (instance == null) {
			instance = new TimerServiceImpl();
			instance.init();
		}
		return instance;
	}

	int sleepTime;

	private List<TimerObject> objects;

	volatile boolean running; 

	/**
	 *  The ticker-thread.
	 */
	Thread thread;


	/**
	 *  Description of the Method
	 */
	public void init() {
		thread = new Thread(this);
		thread.setDaemon(true);
		thread.start();
	}


	/**
	 *  Description of the Method
	 */
	public void deInit() {
		running = false;
		objects.clear();
	}


	/**
	 *  Adds a feature to the Consumer attribute of the CIPTimer object
	 *
	 *@param  object  The feature to be added to the Consumer attribute
	 *@param  amount  The feature to be added to the Consumer attribute
	 */
	public void addConsumer(ITimerConsumer object, int amount) {
		TimerObject t = new TimerObject(amount);
		int i = objects.indexOf(t);
		if (i != -1) {
			objects.get(i).addConsumer(object);
		}else{
			t.addConsumer(object);
			objects.add(t);
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  object  Description of Parameter
	 *@param  amount  Description of Parameter
	 */
	public void removeConsumer(ITimerConsumer object, int amount) {
		TimerObject t = new TimerObject(amount);
		int i = objects.indexOf(t);
		if (i == -1) {
			return;
		}
		//no such amount -> probably a logical programerror.
		objects.get(i).consumers.remove(object);
		if (objects.get(i).consumers.size() == 0) {
			objects.remove(i);
		}
	}


	/**
	 *  Description of the Method
	 *
	 *@param  object  Description of Parameter
	 */
	public void removeConsumer(ITimerConsumer object) {
		if (objects == null || objects.size() == 0) {
			return;
		}
		int temp[] = new int[objects.size()];
		int i;
		for (i = 0; i < objects.size(); i++) {
			temp[i] = objects.get(i).amount;
		}
		for (i = 0; i < objects.size(); i++) {
			int l = objects.size();
			removeConsumer(object, temp[i]);
			if (l > objects.size()) {
				//if the object was the last receiver in this ticker, remove the whole ticker.
				i--;
			}
		}
	}




	/**
	 *  Main processing method for the CIPTimer object
	 */
	public void run() {
		running = true;
		int sleepingTime = TimerServiceConstantsUtility.getSleepingUnit();
		int sleptTime = 0;
		while (running) {
			sleptTime = 0;
			long startedToSleep = System.currentTimeMillis();
			if (sleepingTime>0){
				try {
					Thread.sleep(sleepingTime);
				}catch (InterruptedException ignored) {}
				sleptTime = (int)(System.currentTimeMillis()-startedToSleep);
			}
			long executionStart = System.currentTimeMillis();
			for (int i = 0, in=objects.size(); i < in; i++) {
				TimerObject o = objects.get(i);
				o.remaining--;
				if (o.remaining == 0) {
					o.remaining = o.amount;

					for (int y = 0, yn = o.consumers.size(); y<yn; y++) {
						try {
							o.consumers.get(y).receiveTimerEvent(o.amount);
						}catch (Exception e) {
							//;log.warn("caught exception (uncritical)", e);
						}catch(Throwable t){
							//;log.error("caught error in timer (will survive it)", t);
						}
					}
				}
			}
			long executionEnd = System.currentTimeMillis();
			int duration = (int)(executionEnd-executionStart);
			sleepingTime = TimerServiceConstantsUtility.getSleepingUnit()-duration;
			if (sleptTime>TimerServiceConstantsUtility.getSleepingUnit())
				sleepingTime -= sleptTime - TimerServiceConstantsUtility.getSleepingUnit();
			if (sleepingTime<=0){
				sleepingTime = 0;
			}
		}
		/*
		 * ...while (outer)...
		 */

	}



	/**
	 *  The Holder class for the information of upcoming events.
	 *
	 *@author     hwa
	 *@created    12. Februar 2001
	 */
	class TimerObject {

		/**
		 *  Amount of time remaining for activation
		 */
		int remaining;

		/**
		 *  Amount for this timer object to sleep.
		 */
		int amount;

		/**
		 *  Receiver of this Timer Event.
		 */
		List<ITimerConsumer> consumers;


		/**
		 *  Default constructor.
		 *
		 *@param  amount  Description of Parameter
		 */
		public TimerObject(int amount) {
			this.amount = amount;
			this.remaining = amount;
		}


		/**
		 *  Adds a new receiver to an existing TimerObject.
		 *
		 *@param  rec  The feature to be added to the Receiver attribute
		 */
		public void addConsumer(ITimerConsumer rec) {
			if (this.consumers == null) {
				consumers = new ArrayList<ITimerConsumer>(10);
			}
			consumers.add(rec);
		}


		/**
		 *  Removes the given receiver from this TimerObject. If this receiver
		 *  doesn't exist, the method call will be simply ignored.
		 *
		 *@param  rec  Description of Parameter
		 */
		public void removeConsumer(ITimerConsumer rec) {
			if (consumers == null) {
				return;
			}
			consumers.remove(rec);
		}


		/**
		 *  For indexOf in java.util.Vector
		 *
		 *@param  o  Description of Parameter
		 *@return    Description of the Returned Value
		 */
		public boolean equals(Object o) {
			if (!(o instanceof TimerObject)) {
				return false;
			}
			return this.amount == ((TimerObject) o).amount;
		}
	}

}
