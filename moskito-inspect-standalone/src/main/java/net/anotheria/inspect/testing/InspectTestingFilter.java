package net.anotheria.inspect.testing;

import net.anotheria.maf.MAFFilter;
import net.anotheria.maf.action.ActionMappingsConfigurator;

import java.util.Arrays;
import java.util.List;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 23.04.14 16:37
 */
public class InspectTestingFilter extends MAFFilter {
	@Override
	protected List<ActionMappingsConfigurator> getConfigurators(){
		return Arrays.asList(new ActionMappingsConfigurator[]{new InspectMappingsConfigurator()});
	}
}
