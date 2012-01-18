package net.java.dev.moskito.core.helper;

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
	 * Creates a new tieable.
	 * @param aDefinition definition parameter.
	 */
	protected AbstractTieable(D aDefinition){
		definition = aDefinition;
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
}
