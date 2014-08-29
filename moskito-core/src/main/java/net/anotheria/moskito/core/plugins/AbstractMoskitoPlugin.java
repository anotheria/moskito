package net.anotheria.moskito.core.plugins;

/**
 * Adapter that allows to implement moskito plugins by overriding only the needed methods.
 *
 * @author lrosenberg
 * @since 19.03.13 15:43
 */
public abstract class AbstractMoskitoPlugin implements MoskitoPlugin{
	@Override
	public void setConfigurationName(String configurationName) {
	}

	@Override
	public void initialize() {
	}

	@Override
	public void deInitialize() {
	}
}

