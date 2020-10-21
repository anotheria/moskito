package net.anotheria.moskito.webui.util;

import net.anotheria.anoplass.api.APIException;
import net.anotheria.moskito.webui.MoSKitoWebUIContext;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO comment this class
 *
 * @author lrosenberg
 * @since 21.10.20 13:16
 */
public class Ping {
	public static void main(String[] a) throws APIException {
		String host = "localhost";
		int port = 9451;

		
		MoSKitoWebUIContext context = MoSKitoWebUIContext.getCallContextAndReset();
		context.setCurrentSession(new HttpSessionMock());

		APILookupUtility.setCurrentConnectivityMode(ConnectivityMode.REMOTE);
		RemoteInstance ri = new RemoteInstance();
		ri.setHost(host);
		ri.setPort(port);
		APILookupUtility.setCurrentRemoteInstance(ri);

		System.out.println(APILookupUtility.describeConnectivity());
		System.out.println(APILookupUtility.getThresholdAPI().getThresholdStatuses());
	}
	private static class HttpSessionMock implements HttpSession {

		private final Map<String, Object> attributes = new HashMap<>();

		@Override
		public long getCreationTime() {
			return 0;
		}

		@Override
		public String getId() {
			return null;
		}

		@Override
		public long getLastAccessedTime() {
			return 0;
		}

		@Override
		public ServletContext getServletContext() {
			return null;
		}

		@Override
		public void setMaxInactiveInterval(int interval) {

		}

		@Override
		public int getMaxInactiveInterval() {
			return 0;
		}

		@Override
		public HttpSessionContext getSessionContext() {
			return null;
		}

		@Override
		public Object getAttribute(String name) {
			return attributes.get(name);
		}

		@Override
		public Object getValue(String name) {
			return null;
		}

		@Override
		public Enumeration<String> getAttributeNames() {
			return null;
		}

		@Override
		public String[] getValueNames() {
			return new String[0];
		}

		@Override
		public void setAttribute(String name, Object value) {
			attributes.put(name, value);
		}

		@Override
		public void putValue(String name, Object value) {

		}

		@Override
		public void removeAttribute(String name) {

		}

		@Override
		public void removeValue(String name) {

		}

		@Override
		public void invalidate() {

		}

		@Override
		public boolean isNew() {
			return false;
		}
	}


}
