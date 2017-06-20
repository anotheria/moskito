package net.anotheria.moskito.webui.auth;

public class AuthConstants {

    /**
     * Name of the authorization cookie
     */
    public static String AUTH_COOKIE_NAME = "mskAuthorization";
    /**
     * Name of session attribute indicates user is authorized
     */
    public static String AUTH_SESSION_ATTR_NAME = "mskAuth";
    /**
     * Name of session attribute. Url of page, from where user be redirected to login page
     * stores there.
     */
    public static String LOGIN_REFER_PAGE_SESSION_ATTR_NAME = "loginReferrerPage";

    /**
     * Array of urls required for authorization.
     * Whitelist for authorization filter
     */
    public static String[] LOGIN_PAGES = new String[]{
            "/moskito-inspect/mskSignIn"
    };

}
