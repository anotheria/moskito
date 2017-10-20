package net.anotheria.moskito.core.registry;

import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.producers.IStatsProducer;
import net.anotheria.moskito.core.util.BuiltInProducer;
import net.anotheria.moskito.core.util.MBeanUtil;
import net.anotheria.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * This listener registers every new producer as jmx bean.
 */
public class JMXBridgeListener<S extends IStats> implements IProducerRegistryListener<S> {

	/**
	 * Logger.
	 */
	private static Logger log = LoggerFactory.getLogger(JMXBridgeListener.class);
	
	@Override
	public void notifyProducerRegistered(IStatsProducer<S> producer) {
		if (producer instanceof BuiltInProducer)
			return;
		List<S> stats = producer.getStats();
	    if (stats == null)
	    	return;
		for (IStats s : stats){
			try{
				MBeanUtil.getInstance().registerMBean(s, createName(producer.getProducerId(), s.getName()));
			} catch(Exception e){
				log.warn("can't register "+s.getName()+" in "+producer.getProducerId()+", ignored.", e);
			}
		}
	}

	@Override
	public void notifyProducerUnregistered(IStatsProducer<S> producer) {
		List<S> stats = producer.getStats();
	    if (stats == null)
	    	return;
		for (IStats s : stats){
			try{
		    	boolean isUnregistered =
						MBeanUtil.getInstance().unregisterMBean(createName(producer.getProducerId(), s.getName()));
		    	if(!isUnregistered)
					log.debug("can't unregister "+s.getName()+" in "+producer.getProducerId()+
							", MBean with such name is not registered");
			}catch(Exception e){
				log.warn("can't unregister "+s.getName()+" in "+producer.getProducerId()+", ignored.", e);
			}
			
		}
	}

	/**
	 * Creates JMX name for a producer.
	 * @param producerId target producerId.
	 * @param statName target statName.
	 * @return the name for JMXBean.
	 */
	private String createName(String producerId, String statName) {
		String appName = encodeAppName(MoskitoConfigurationHolder.getConfiguration().getApplicationName());
		return "MoSKito."+(appName.length()>0 ? appName+ '.' :"")+"producers:type="+producerId+ '.' +statName;
	}

	/**
	 * Chats that should be removed from bean name prior to registering it with jmx in order to create valid names.
	 */
	private static final char[] CHARS_TO_REMOVE_FROM_NAME = {
		' ','\t','\r','\n', '(', ')','+','-'
	};

	/**
	 * Removes spaces from app name.
	 * @param appName source application name.
	 * @return appName without whitespaces.
	 */
	private String encodeAppName(String appName){
		return StringUtils.removeChars(appName, CHARS_TO_REMOVE_FROM_NAME);
	}

}
