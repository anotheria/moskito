package net.anotheria.moskito.core.plugins;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A noop plugin is used for generic testing of plugin interface.
 *
 * @author lrosenberg
 * @since 19.03.13 15:55
 */
public class NoOpPlugin extends AbstractMoskitoPlugin{
	private static final Logger LOGGER = LoggerFactory.getLogger(NoOpPlugin.class);

	@Override
	public void initialize() {
		LOGGER.info(this.getClass().getSimpleName()+" initialized");
	}

	@Override
	public void deInitialize() {
		LOGGER.info(this.getClass().getSimpleName()+" de-initialized");
	}
}
