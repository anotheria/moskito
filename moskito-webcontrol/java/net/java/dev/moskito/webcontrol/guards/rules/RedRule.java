package net.java.dev.moskito.webcontrol.guards.rules;

import net.java.dev.moskito.webcontrol.guards.Condition;

public class RedRule extends AbstractRule {
	
	@Override
	public Condition getCondition() {
		return Condition.RED;
	}
	
	public RedRule(Double min, Double max) {
		super(min, max);
	}
	
}
