package net.anotheria.moskito.webui.util;

import net.anotheria.moskito.webui.MoSKitoWebUIContext;
import org.junit.Before;
import org.junit.Test;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Test {@link DeepLinkUtil} class
 */
public class DeepLinkUtilTest {

    /**
     * Sets HttpSession mock in call context.
     * Otherwise {@link APILookupUtility#setCurrentConnectivityMode(ConnectivityMode)} thrown NullPointerException
     */
    @Before
    public void setupWebUiContext(){
        MoSKitoWebUIContext.getCallContext().setCurrentSession(
                new HttpSessionMock()
        );
    }

    /**
     * Makes current remote connection local
     */
    private void makeLocalConnection(){
        APILookupUtility.setCurrentConnectivityMode(ConnectivityMode.LOCAL);
    }

    /**
     * Fills connection information in APILookupUtility
     * to simulate remote connection
     *
     * @param host remote server host
     * @param port remote server port
     */
    private void makeRemoteConnection(String host, int port){
        RemoteInstance newRemoteInstance = new RemoteInstance();
        newRemoteInstance.setHost(host);
        newRemoteInstance.setPort(port);
        WebUIConfig.getInstance().addRemote(newRemoteInstance);
        APILookupUtility.setCurrentConnectivityMode(ConnectivityMode.REMOTE);
        APILookupUtility.setCurrentRemoteInstance(newRemoteInstance);
    }

    @Test
    public void testMakeDeepLink(){

        final String linkWithoutRemoteParameter =
                "/mskDashboards";

        final String linkContainsRemoteParameter =
                "/mskDashboards?remoteConnection=testhost.com%3A9104";

        makeLocalConnection();

        // Link that contains remote parameter should not be changed
        assertEquals(
                linkContainsRemoteParameter,
                DeepLinkUtil.makeDeepLink(linkContainsRemoteParameter)
        );
        assertEquals(
                linkWithoutRemoteParameter,
                DeepLinkUtil.makeDeepLink(linkWithoutRemoteParameter)
        );

        makeRemoteConnection("testhost.com", 9104);

        assertEquals(
                linkContainsRemoteParameter,
                DeepLinkUtil.makeDeepLink(linkContainsRemoteParameter)
        );
        assertEquals(
                linkContainsRemoteParameter,
                DeepLinkUtil.makeDeepLink(linkWithoutRemoteParameter)
        );


    }

    @Test
    public void testGetCurrentRemoteConnectionLink(){

        makeLocalConnection();
        // Trying to get remote connection link when connection is local
        assertNull(
                DeepLinkUtil.getCurrentRemoteConnectionLink()
        );


        makeRemoteConnection("testhost.com", 9104);
        // Now connection is not local. Remote link should be returned
        assertEquals(
                "testhost.com%3A9104", DeepLinkUtil.getCurrentRemoteConnectionLink()
        );

    }

    @Test
    public void testContainsRemoteParameter(){

        final String linkThatContainsRemoteParameter
                = "/mskDashboards?remoteConnection=testhost.com%3A9104";

        final String linkThatContainsRemoteParameterAndSomeOtherBefore
                = "/mskDashboards?foo=bar&remoteConnection=testhost.com%3A9104";

        final String linkThatContainsRemoteParameterAndSomeOtherAfter
                = "/mskDashboards?remoteConnection=testhost.com%3A9104&foo=bar";

        final String linkWithoutRemoteParameter
                = "/mskDashboards";

        assertTrue(
                DeepLinkUtil.containsRemoteParameter(linkThatContainsRemoteParameter)
        );

        assertTrue(
                DeepLinkUtil.containsRemoteParameter(linkThatContainsRemoteParameterAndSomeOtherBefore)
        );

        assertTrue(
                DeepLinkUtil.containsRemoteParameter(linkThatContainsRemoteParameterAndSomeOtherAfter)
        );

        assertFalse(
                DeepLinkUtil.containsRemoteParameter(linkWithoutRemoteParameter)
        );


    }

    private static class HttpSessionMock implements HttpSession {

        private Map<String, Object> attributes = new HashMap<>();

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
