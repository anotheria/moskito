package net.anotheria.moskito.webui.shared.api;

import net.anotheria.anoplass.api.AbstractAPIImpl;
import net.anotheria.moskito.core.config.MoskitoConfiguration;
import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 11.02.13 18:39
 */
public class AbstractMoskitoAPIImpl extends AbstractAPIImpl{
	protected MoskitoConfiguration getConfiguration(){
		return MoskitoConfigurationHolder.getConfiguration();
	}
}
