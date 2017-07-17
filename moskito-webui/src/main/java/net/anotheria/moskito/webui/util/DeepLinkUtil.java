package net.anotheria.moskito.webui.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Utility class for building internal moskito-inspect links
 * with ability to share remote connection information between
 * users.
 */
public class DeepLinkUtil {

    /**
     * GET query parameter name, where remote connection link stores
     */
    private static final String PARAM_REMOTE_CONNECTION = "remoteConnection";

    /**
     * Urlencoded column symbol (`:`). Used to build remote connection query
     */
    private static final String COLON_URL_ENCODED = "%3A";

    /**
     * Checks, is link, passed to method, contains remote connection parameter.
     * @param link link
     * @return true  - link contains remote connection parameter
     *         false - no
     */
    private static boolean containsRemoteParameter(String link){
        return link.matches("^.+(\\?|&)" + PARAM_REMOTE_CONNECTION + "=.+$");
    }

    /**
     * TODO : RENAME METHOD;
     * Returns current remote connection link, that
     * contains connection host and port. (Example : `localhost:9401`).
     * Returns null, of current connection is local
     * @return current remote connection link or null, if current connection is local
     */
    public static String getCurrentRemoteLink(){

        if(!APILookupUtility.isLocal()) try{

            return URLEncoder.encode(APILookupUtility.getCurrentRemoteInstance().getHost(), "UTF-8") +
                            COLON_URL_ENCODED +
                            APILookupUtility.getCurrentRemoteInstance().getPort();

        }catch (IllegalStateException | UnsupportedEncodingException ignored){/*null be returned*/}

        return null;

    }

    /**
     * Adds remote connection parameter to link GET query,
     * if parameter yet not present in this link.
     * @param link link to add connection parameter
     * @return link with remote connection GET parameter
     */
    public static String makeDeepLink(String link){

        if(getCurrentRemoteLink() != null && !containsRemoteParameter(link))
            link += ( link.contains("?") ? "&" : "?" ) + PARAM_REMOTE_CONNECTION + "=" + getCurrentRemoteLink();

        return link;

    }

}
