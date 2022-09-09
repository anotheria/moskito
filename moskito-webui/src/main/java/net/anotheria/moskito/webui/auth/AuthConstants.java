package net.anotheria.moskito.webui.auth;

/**
 * Constants for authorization.
 */
public class AuthConstants {

    /**
     * Name of the authorization cookie
     */
    public static final String AUTH_COOKIE_NAME = "mskAuthorization";
    /**
     * Name of session attribute indicates user is authorized
     */
    public static final String AUTH_SESSION_ATTR_NAME = "mskAuth";
    /**
     * Name of session attribute. Url of page, from where user be redirected to login page
     * stores there.
     */
    public static final String LOGIN_REFER_PAGE_SESSION_ATTR_NAME = "loginReferrerPage";

    /**
     * Array of urls required for authorization.
     * Whitelist for authorization filter
     */
    public static final String LOGIN_PAGE = "mskSignIn";

}
