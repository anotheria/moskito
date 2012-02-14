package net.java.dev.moskito.core.blueprint;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * The factory for creation of blueprint producer objects. The blueprint producer instances are created on the fly. There is 
 * only one instance of the same producer allowed.
 * @author lrosenberg
 */
public class BlueprintProducersFactory {
	
	/**
	 * Logger.
	 */
	private static Logger log = Logger.getLogger(BlueprintProducersFactory.class);
	/**
	 * Already instantiated producers.
	 */
	private static Map<String, BlueprintProducer> producers = new HashMap<String, BlueprintProducer>();
	
	
	/**
	 * Returns the blueprint producer with this id. Creates one if none is existing. Uses DLC pattern to reduce synchronization overhead.
	 * @param producerId
	 * @param category
	 * @param subsystem
	 * @return
	 */
	public static BlueprintProducer getBlueprintProducer(String producerId, String category, String subsystem){
		try{
			//first find producer
			BlueprintProducer producer = producers.get(producerId);
			if (producer==null){
				synchronized(producers){
					producer = producers.get(producerId);
					if (producer==null){
						producer = new BlueprintProducer(producerId, category, subsystem);
						producers.put(producerId, producer);
					}
				}
			}
			//end producer lookup
			return producer;
		}catch(Exception e){
			log.error("getBlueprintProducer("+producerId+", "+category+", "+subsystem+")", e);
			throw new RuntimeException("Handler instantiation failed - "+e.getMessage());
		}
		
	}
}
