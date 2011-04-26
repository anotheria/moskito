package net.java.dev.moskito.core.helper;

import net.java.dev.moskito.core.producers.IStats;

public interface Tieable extends IntervalUpdateable{

	boolean isActivated();

	Object getTargetStatName();

	void tieToStats(IStats s);
	
	TieableDefinition getDefinition();
	
	String getName();

}
