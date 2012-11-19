package net.anotheria.moskito.annotation.callingAspect;

import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import org.aspectj.lang.ProceedingJoinPoint;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 19.11.12 00:00
 */
public class AbstractMoskitoAspect<S extends IStats> {

	/**
	 * Map with created producers.
	 */
	private ConcurrentMap<String, OnDemandStatsProducer<S>> producers = new ConcurrentHashMap<String, OnDemandStatsProducer<S>>();

	/**
	 * Returns the producer for the given pjp and producerId. Registers the producer in the registry if it's not already registered.
	 * @param pjp the pjp is used to obtain the producer id automatically if it's not submitted.
	 * @param aProducerId submitted producer id, used if configured in annotation.
	 * @param aCategory submitted category.
	 * @param aSubsystem submitted subsystem.
	 * @param withMethod if true the name of the method will be part of the automatically generated producer id.
	 * @return
	 */
	protected  OnDemandStatsProducer<S> getProducer(ProceedingJoinPoint pjp, String aProducerId, String aCategory, String aSubsystem, boolean withMethod, IOnDemandStatsFactory<S> factory){
		String producerId = null;
		if (aProducerId!=null && aProducerId.length()>0){
			producerId = aProducerId;
		}else{
			producerId = pjp.getSignature().getDeclaringTypeName();
			try{
				producerId = producerId.substring(producerId.lastIndexOf('.')+1);
			}catch(RuntimeException ignored){/* ignored */}
		}

		if (withMethod)
			producerId += "."+pjp.getSignature().getName();

		OnDemandStatsProducer<S> producer = producers.get(producerId);
		if (producer==null){

			producer = new OnDemandStatsProducer(producerId, getCategory(aCategory), getSubsystem(aSubsystem), factory);
			OnDemandStatsProducer<S> p = producers.putIfAbsent(producerId, producer);
			if (p==null){
				ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(producer);
			}else{
				producer = p;
			}
		}
		return producer;

	}


	public String getCategory(String proposal) {
		return proposal==null || proposal.length()==0 ? "annotated" : proposal;
	}

	public String getSubsystem(String proposal) {
		return proposal==null || proposal.length()==0 ? "default" : proposal;
	}

	public void reset() {
		producers.clear();
	}

}
