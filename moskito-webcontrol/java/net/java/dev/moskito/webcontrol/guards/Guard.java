package net.java.dev.moskito.webcontrol.guards;

import net.java.dev.moskito.webcontrol.configuration.ViewField;
import net.java.dev.moskito.webcontrol.repository.Attribute;
import net.java.dev.moskito.webcontrol.repository.Snapshot;

public interface Guard {
	
	public void execute(Snapshot ss, ViewField field, Attribute attr) throws GuardException;
	
	public void setRules(String rules);

}
