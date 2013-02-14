package net.anotheria.moskito.webui.threads.api;

import net.anotheria.anoplass.api.APIFactory;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 14.02.13 11:46
 */
public class ThreadAPIFactory implements APIFactory<ThreadAPI> {
	@Override
	public ThreadAPI createAPI() {
		return new ThreadAPIImpl();
	}
}
