package net.java.dev.moskito.webcontrol.guards.rules;

import net.java.dev.moskito.webcontrol.guards.Condition;

public interface Rule {
	
	public Condition getCondition();
	
	public Condition checkValue(Double value);
	
}
