package net.anotheria.moskito.webcontrol.guards.rules;

import net.anotheria.moskito.webcontrol.guards.Condition;

public abstract class AbstractRule implements Rule {

	private Double min;
	private Double max;

	public String toString() {
		return "min=" + min + ", max=" + max;
	}

	public AbstractRule(Double min, Double max) {
		this.min = min;
		this.max = max;
	}

	@Override
	public Condition checkValue(Double value) {
		if (value.doubleValue() > min.doubleValue() && value.doubleValue() <= max.doubleValue()) {
			return getCondition();
		} else {
			return null;
		}
	}

}
