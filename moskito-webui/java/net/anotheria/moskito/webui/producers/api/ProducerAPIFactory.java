package net.anotheria.moskito.webui.producers.api;

import net.anotheria.anoplass.api.APIFactory;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 14.02.13 11:48
 */
public class ProducerAPIFactory implements APIFactory<ProducerAPI> {
	@Override
	public ProducerAPI createAPI() {
		return new ProducerAPIImpl();
	}
}
