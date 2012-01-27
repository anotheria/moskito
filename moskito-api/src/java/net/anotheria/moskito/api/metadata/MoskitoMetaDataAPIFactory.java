package net.anotheria.moskito.api.metadata;

/**
 * Factory for getting {@link MoskitoMetaDataAPITest} implementation.
 * 
 * @author Alexandr Bolbat
 */
public final class MoskitoMetaDataAPIFactory {
	/**
	 * {@link MoskitoMetaDataAPITest} instance.
	 */
	private static MoskitoMetaDataAPI INSTANCE;
	/**
	 * Lock object for synchronization purposes on API instance creation.
	 */
	private static final Object INSTANCE_LOCK = new Object();

	/**
	 * Get {@link MoskitoMetaDataAPITest} instance.
	 * 
	 * @return {@link MoskitoMetaDataAPITest}
	 */
	public static MoskitoMetaDataAPI getAPI() {
		if (INSTANCE == null)
			synchronized (INSTANCE_LOCK) {
				if (INSTANCE == null)
					INSTANCE = new MoskitoMetaDataAPIImpl();
			}

		return INSTANCE;
	}

}
