package net.anotheria.moskito.webcontrol.guards.rules;

import net.anotheria.moskito.webcontrol.guards.Condition;

public class GreenRule extends AbstractRule {

	@Override
	public Condition getCondition() {
		return Condition.GREEN;
	}
	
	public GreenRule(Double min, Double max) {
		super(min, max);
	}
	
	
}
