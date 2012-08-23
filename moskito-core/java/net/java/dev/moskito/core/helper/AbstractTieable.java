package net.java.dev.moskito.core.helper;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Base class for Tieables with common functionality.
 * @author lrosenberg
 *
 * @param <D>
 */
public abstract class AbstractTieable<D extends TieableDefinition> implements Tieable{
	/**
	 * The definition object.
	 */
	D definition;

	/**
	 * Id of this tieable for inner use (referencing).
	 */
	private String id;
	/**
	 * Instance counter.
	 */
	private static final AtomicInteger instanceCounter = new AtomicInteger(0);

	/**
	 * Creates a new tieable.
	 * @param aDefinition definition parameter.
	 */
	protected AbstractTieable(D aDefinition){
		definition = aDefinition;
		id = ""+instanceCounter.incrementAndGet();

	}
	
	@Override
	public String getName(){
		return getDefinition().getName();
	}
	
	@Override
	public D getDefinition(){
		return definition;
	}

	@Override
	public Object getTargetStatName() {
		return getDefinition().getStatName();
	}
	public String getId(){
		return id;
	}


}
