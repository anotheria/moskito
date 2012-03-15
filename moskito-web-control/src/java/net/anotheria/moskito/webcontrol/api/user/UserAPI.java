package net.anotheria.moskito.webcontrol.api.user;

/**
 * {@link UserAPI} interface.
 * 
 * @author Alexandr Bolbat
 */
public interface UserAPI {

	/**
	 * Is user exist with given login.
	 * 
	 * @param login
	 *            - user login
	 * @return <code>true</code> if exist or <code>false</code>
	 */
	boolean isExist(String login);

	/**
	 * Is user can login with given login and password.
	 * 
	 * @param login
	 *            - user login
	 * @param password
	 *            - user password
	 * @return <code>true</code> if can login or <code>false</code>
	 */
	boolean isCanLogin(String login, String password);

}
