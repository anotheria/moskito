package net.anotheria.moskito.webui.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class DeepLinkUtil {

    /**
     *
     */
    private static final String PARAM_REMOTE_CONNECTION = "remoteConnection";
    /**
     *
     */
    private static final String COLON_URL_ENCODED = "%3A";

    public static String makeDeepLink(String link){

        if(!APILookupUtility.isLocal()) try{

            String connectionUrl =
                    URLEncoder.encode(APILookupUtility.getCurrentRemoteInstance().getHost(), "UTF-8") +
                            COLON_URL_ENCODED +
                            APILookupUtility.getCurrentRemoteInstance().getPort();

            link += (link.contains("?") ? "&" : "?") + PARAM_REMOTE_CONNECTION + "=" + connectionUrl;

        }
        catch (IllegalStateException | UnsupportedEncodingException ignored){}

        return link;

    }

}
