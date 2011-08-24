package net.java.dev.moskito.core.registry;

import java.lang.management.ManagementFactory;
import java.util.List;

import javax.management.InstanceAlreadyExistsException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;

import org.apache.log4j.Logger;

import net.java.dev.moskito.core.helper.RuntimeConstants;
import net.java.dev.moskito.core.producers.IStats;
import net.java.dev.moskito.core.producers.IStatsProducer;
import net.java.dev.moskito.core.util.BuiltInProducer;

public class JMXBridgeListener implements IProducerRegistryListener{

	private static Logger log = Logger.getLogger(JMXBridgeListener.class);
	
	@Override
	public void notifyProducerRegistered(IStatsProducer producer) {
		if (producer instanceof BuiltInProducer)
			return;
		List<IStats> stats = producer.getStats();
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
	    if (stats == null)
	    	return;
		for (IStats s : stats){
			try{
				ObjectName name = createName(producer.getProducerId(), s.getName());
		    	mbs.registerMBean(s, name);
			}catch(InstanceAlreadyExistsException e){ 
				log.warn("can't register "+s.getName()+" in "+producer.getProducerId()+", ignored InstanceAlreadyExistsException.");
			}catch(Exception e){
				log.warn("can't register "+s.getName()+" in "+producer.getProducerId()+", ignored.", e);
			}
			
		}
	}

	@Override
	public void notifyProducerUnregistered(IStatsProducer producer) {
		List<IStats> stats = producer.getStats();
		MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
	    if (stats == null)
	    	return;
		for (IStats s : stats){
			try{
				ObjectName name = createName(producer.getProducerId(), s.getName());
		    	mbs.unregisterMBean(name);
			}catch(InstanceNotFoundException e){
				log.debug("can't unregister "+s.getName()+" in "+producer.getProducerId()+", ignored InstanceNotFoundException.");
			}catch(Exception e){
				log.warn("can't unregister "+s.getName()+" in "+producer.getProducerId()+", ignored.", e);
			}
			
		}
	}

	private ObjectName createName(String producerId, String statName) throws MalformedObjectNameException{
		String appName = RuntimeConstants.getApplicationName();
		String objectName = "moskito."+(appName.length()>0 ? appName+".":"")+"producers.:type="+producerId+"."+statName;
		ObjectName objName = new ObjectName(objectName);
		return objName;
	}

}
