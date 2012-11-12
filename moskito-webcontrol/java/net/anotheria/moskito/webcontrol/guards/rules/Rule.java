package net.anotheria.moskito.webcontrol.guards.rules;

import net.anotheria.moskito.webcontrol.guards.Condition;

public interface Rule {
	
	public Condition getCondition();
	
	public Condition checkValue(Double value);
	
}
