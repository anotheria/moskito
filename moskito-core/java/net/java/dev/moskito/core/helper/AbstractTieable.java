package net.java.dev.moskito.core.helper;

public abstract class AbstractTieable<D extends TieableDefinition> implements Tieable{
	
	D definition;
	
	protected AbstractTieable(D aDefinition){
		definition = aDefinition;
	}
	
	@Override
	public String getName(){
		return getDefinition().getName();
	}
	
	public D getDefinition(){
		return definition;
	}

	@Override
	public Object getTargetStatName() {
		return getDefinition().getStatName();
	}
}
