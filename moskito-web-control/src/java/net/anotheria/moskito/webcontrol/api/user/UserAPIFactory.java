package net.anotheria.moskito.webcontrol.api.user;

/**
 * Factory for getting {@link UserAPI} implementation.
 * 
 * @author Alexandr Bolbat
 */
public final class UserAPIFactory {
	/**
	 * {@link UserAPI} instance.
	 */
	private static final UserAPI API_INSTANCE = new UserAPIImpl();

	/**
	 * Private constructor.
	 */
	private UserAPIFactory() {
	}

	/**
	 * Get {@link UserAPI} instance.
	 * 
	 * @return {@link UserAPI}
	 */
	public static UserAPI getAPI() {
		return API_INSTANCE;
	}

}
