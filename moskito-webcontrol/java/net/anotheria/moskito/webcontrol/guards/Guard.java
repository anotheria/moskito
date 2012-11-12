package net.anotheria.moskito.webcontrol.guards;

import net.anotheria.moskito.webcontrol.configuration.ViewField;
import net.anotheria.moskito.webcontrol.repository.Attribute;
import net.anotheria.moskito.webcontrol.repository.Snapshot;

public interface Guard {
	
	public void execute(Snapshot ss, ViewField field, Attribute attr) throws GuardException;
	
	public void setRules(String rules);

}
