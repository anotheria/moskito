package net.anotheria.moskito.webcontrol.guards.rules;

import net.anotheria.moskito.webcontrol.guards.Condition;

public class YellowRule extends AbstractRule {
	
	@Override
	public Condition getCondition() {
		return Condition.YELLOW;
	}
	
	public YellowRule(Double min, Double max) {
		super(min, max);
	}
	
}
