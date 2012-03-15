package net.anotheria.moskito.webcontrol.api.user;

/**
 * {@link UserAPI} implementation.
 * 
 * @author Alexandr Bolbat
 */
public class UserAPIImpl implements UserAPI {

	@Override
	public boolean isExist(final String login) {
		return true;
	}

	@Override
	public boolean isCanLogin(final String login, final String password) {
		return true;
	}

}
