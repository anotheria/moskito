package net.anotheria.moskito.core.plugins;

/**
 * A noop plugin is used for generic testing of plugin interface.
 *
 * @author lrosenberg
 * @since 19.03.13 15:55
 */
public class NoOpPlugin extends AbstractMoskitoPlugin{
	@Override
	public void initialize() {
		System.out.println(this.getClass().getSimpleName()+" initialized");
	}

	@Override
	public void deInitialize() {
		System.out.println(this.getClass().getSimpleName()+" de-initialized");
	}
}
