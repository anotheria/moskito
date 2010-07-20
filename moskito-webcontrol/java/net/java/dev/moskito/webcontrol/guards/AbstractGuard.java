package net.java.dev.moskito.webcontrol.guards;

import java.util.List;

import net.java.dev.moskito.webcontrol.configuration.ViewField;
import net.java.dev.moskito.webcontrol.guards.rules.Rule;
import net.java.dev.moskito.webcontrol.repository.Attribute;
import net.java.dev.moskito.webcontrol.repository.NumberAttribute;
import net.java.dev.moskito.webcontrol.repository.Snapshot;

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
