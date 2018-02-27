package net.anotheria.moskito.moskitosaas.errorhandling;

import net.anotheria.moskito.core.errorhandling.BuiltInErrorProducer;
import net.anotheria.moskito.core.plugins.MoskitoPlugin;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 25.02.18 23:21
 */
public class MoskitoSaasErrorHandlingPlugin implements MoskitoPlugin{
	@Override
	public void setConfigurationName(String configurationName) {
		System.out.println("My configname "+configurationName);
	}

	@Override
	public void initialize() {
		System.out.println("Saas plugin initialized");
		MoskitoSaasErrorCatcher catcher = new MoskitoSaasErrorCatcher();
		BuiltInErrorProducer.getInstance().addCustomErrorCatcher(catcher);

	}

	@Override
	public void deInitialize() {
		System.out.println("Saas plugin deinitialized");
	}
}
