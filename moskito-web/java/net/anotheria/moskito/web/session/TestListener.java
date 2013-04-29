package net.anotheria.moskito.web.session;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicLong;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 28.04.13 18:10
 */
public class TestListener implements HttpSessionListener, ServletRequestListener{

	private AtomicLong counter = new AtomicLong(0);

	public TestListener() {
	}

	@Override
	public void sessionCreated(HttpSessionEvent se) {
		System.out.println("Session created "+se.getSession().getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent se) {
		//To change body of implemented methods use File | Settings | File Templates.
	}

	@Override
	public void requestDestroyed(ServletRequestEvent sre) {
		System.out.println("Request "+((HttpServletRequest) sre.getServletRequest()).getAttribute("___requestId___")+" destroyed " + ((HttpServletRequest) sre.getServletRequest()).getRequestURI());
	}

	@Override
	public void requestInitialized(ServletRequestEvent sre) {
		long requestId = counter.incrementAndGet();
		System.out.println("Request "+requestId+" created "+((HttpServletRequest)sre.getServletRequest()).getRequestURI());
		((HttpServletRequest)sre.getServletRequest()).setAttribute("___requestId___", requestId);
		HttpSession session = ((HttpServletRequest)sre.getServletRequest()).getSession(false);
		System.out.println("Session? "+ (session!=null));
		if (session!=null){
			System.out.println("Session "+session.getId()+" new? "+session.isNew());
		}
	}
}
