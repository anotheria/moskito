package net.anotheria.moskito.web.filters.sessionbytld;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 26.04.13 23:40
 */
public class SessionByTldListener implements HttpSessionListener {
	@Override
	public void sessionCreated(HttpSessionEvent se) {
		//do nothing.
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		SessionByTldProducer.INSTANCE.sessionDestroyed((String)se.getSession().getAttribute(SessionByTldFilter.ATT_NAME));
	}
}
