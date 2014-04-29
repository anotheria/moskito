package net.anotheria.inspect.testing;

import net.anotheria.maf.action.ActionMappings;
import net.anotheria.maf.action.ActionMappingsConfigurator;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 23.04.14 16:38
 */
public class InspectMappingsConfigurator implements ActionMappingsConfigurator {

	@Override public void configureActionMappings(ActionMappings mappings) {
		mappings.addMapping("fibonacci", FibonacciAction.class);
	}
}
