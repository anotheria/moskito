package net.anotheria.moskito.core.helper;

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
	private D definition;

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
		id = String.valueOf(instanceCounter.incrementAndGet());

	}
	
	@Override
	public String getName(){
        return definition.getName();
	}
	
	@Override
	public D getDefinition(){
		return definition;
	}

	@Override
	public Object getTargetStatName() {
        return definition.getStatName();
	}

	/**
	 * Returns the id of the tieable.
	 * @return
	 */
	public String getId(){
		return id;
	}


}
