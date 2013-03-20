package net.anotheria.moskito.central.connectors.embedded;

import net.anotheria.moskito.core.plugins.AbstractMoskitoPlugin;

/**
 * This connector allows to run moskito central embedded in any moskito instance.
 * This is useful for 1 server systems to reduce complexity.
 *
 * @author lrosenberg
 * @since 20.03.13 14:17
 */
public class EmbeddedConnector extends AbstractMoskitoPlugin{
	@Override
	public void initialize() {
		super.initialize();    //To change body of overridden methods use File | Settings | File Templates.
	}

	@Override
	public void deInitialize() {
		super.deInitialize();    //To change body of overridden methods use File | Settings | File Templates.
	}
}
