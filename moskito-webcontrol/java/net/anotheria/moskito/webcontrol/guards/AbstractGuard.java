package net.anotheria.moskito.webcontrol.guards;

import java.util.List;

import net.anotheria.moskito.webcontrol.configuration.ViewField;
import net.anotheria.moskito.webcontrol.guards.rules.Rule;
import net.anotheria.moskito.webcontrol.repository.Attribute;
import net.anotheria.moskito.webcontrol.repository.NumberAttribute;
import net.anotheria.moskito.webcontrol.repository.Snapshot;

public abstract class AbstractGuard implements Guard {

	@Override
	public void execute(Snapshot ss, ViewField field, Attribute attr) throws GuardException {
		invokeGuard();
		for (Rule rule : getRules()) {
			Condition cond = rule.checkValue(((NumberAttribute<?>) attr).getValue().doubleValue());
			if (cond != null) {
				attr.setCondition(cond);
				
				break;
			}
		}
	}

	public abstract void parseRules(String rules);
	
	public abstract List<Rule> getRules();

	public abstract void invokeGuard();

	public void setRules(String rules) {
		parseRules(rules);
	}

}
