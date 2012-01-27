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
	private static MoskitoMetaDataAPI instance;
	/**
	 * Lock object for synchronization purposes on API instance creation.
	 */
	private static final Object INSTANCE_LOCK = new Object();

	/**
	 * Private constructor.
	 */
	private MoskitoMetaDataAPIFactory() {
		throw new IllegalArgumentException();
	}

	/**
	 * Get {@link MoskitoMetaDataAPITest} instance.
	 * 
	 * @return {@link MoskitoMetaDataAPITest}
	 */
	public static MoskitoMetaDataAPI getAPI() {
		if (instance == null)
			synchronized (INSTANCE_LOCK) {
				if (instance == null)
					instance = new MoskitoMetaDataAPIImpl();
			}

		return instance;
	}

}
