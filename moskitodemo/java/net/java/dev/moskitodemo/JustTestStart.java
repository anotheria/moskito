package net.java.dev.moskitodemo;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;
import javax.management.ObjectName;

public class JustTestStart {
	public static void main(String[] args) throws Exception {
	    MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

	    	// Construct the ObjectName for the MBean we will register
	    	ObjectName name = new ObjectName("moskito:type=JustTest");

	    	// Create the Hello World MBean
	    	JustTest mbean = new JustTest();

	    	// Register the Hello World MBean
	    	mbs.registerMBean(mbean, name);

	    	// Wait forever
	    	System.out.println("Waiting forever...");
	    	Thread.sleep(Long.MAX_VALUE);
	        }
	   }		


