package net.anotheria.moskito.integration.cdi;

import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;

import javax.interceptor.InvocationContext;
import java.io.Serializable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 21.11.12 10:57
 */
public abstract class BaseInterceptor<S extends IStats> implements Serializable  {

	/**
	 * Serialization version unique identifier.
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * The internal producer instances.
	 */
	private ConcurrentMap<String, OnDemandStatsProducer<S>> producers = new ConcurrentHashMap<String, OnDemandStatsProducer<S>>();

	protected OnDemandStatsProducer<S> getProducer(ProducerRuntimeDefinition prd){
		String producerId = prd.getProducerId();
		OnDemandStatsProducer<S> onDemandProducer = producers.get(producerId);
		if (onDemandProducer == null) {
			onDemandProducer = new OnDemandStatsProducer(prd.getProducerId(), prd.getCategory(), prd.getSubsystem(), getFactory());
			OnDemandStatsProducer p = producers.putIfAbsent(producerId, onDemandProducer);
			if (p == null) {
				ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(onDemandProducer);
			} else {
				onDemandProducer = p;
			}
		}
		return onDemandProducer;

	}

	protected abstract IOnDemandStatsFactory<S> getFactory();

	/**
	 * Extract case name from execution context.
	 *
	 * @param ctx execution context
	 * @return case name
	 */
	protected String extractCaseName(InvocationContext ctx) {
		return ctx.getMethod().getName();
	}

	/**
	 * Get producer id.
	 *
	 * @param ctx invocation context
	 * @return string producer id
	 */
	protected ProducerRuntimeDefinition extractProducerDefinition(InvocationContext ctx) {
		return getProducerDefinitionFromClassOrAnnotation(ctx);
	}

	/**
	 * Get class name by invocation context.
	 *
	 * @param ctx invocation context
	 * @return string class name
	 */
	private ProducerRuntimeDefinition getProducerDefinitionFromClassOrAnnotation(InvocationContext ctx) {
		Class c = ctx.getMethod().getDeclaringClass();
		ProducerDefinition ann = (ProducerDefinition)c.getAnnotation(ProducerDefinition.class);

		ProducerRuntimeDefinition ret = new ProducerRuntimeDefinition();
		if (ann==null){
			ret.setProducerId(c.getSimpleName());
			ret.setCategory(getCategory());
			ret.setSubsystem(getSubsystem());
		}else{
			ret.setProducerId(ann.producerId());
			ret.setCategory(ann.category());
			ret.setSubsystem(ann.subsystem());
		}
		return ret;
	}

	protected String getCategory(){
		return "cdi";
	}

	protected String getSubsystem(){
		return "default";
	}

}
