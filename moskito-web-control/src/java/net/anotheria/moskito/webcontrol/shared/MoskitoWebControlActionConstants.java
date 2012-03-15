package net.anotheria.moskito.webcontrol.shared;

/**
 * Actions constants.
 * 
 * @author Alexandr Bolbat
 */
public final class MoskitoWebControlActionConstants {
	/**
	 * Login action.
	 */
	public static final String LOGIN_ACTION = "mwc.login";
	/**
	 * Login action.
	 */
	public static final String LOGOUT_ACTION = "mwc.logout";
	/**
	 * Root page action.
	 */
	public static final String ROOT_PAGE_ACTION = "";
	/**
	 * Main page for logged in user.
	 */
	public static final String DASHBOARD_PAGE_ACTION = "mwc.dashboard";

	/**
	 * Default constructor.
	 */
	private MoskitoWebControlActionConstants() {
		throw new IllegalAccessError();
	}

}
