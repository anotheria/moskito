package net.anotheria.moskito.api.metadata;

/**
 * Factory for getting {@link MoskitoMetaDataAPI} implementation.
 * 
 * @author Alexandr Bolbat
 */
public final class MoskitoMetaDataAPIFactory {
	/**
	 * {@link MoskitoMetaDataAPI} instance.
	 */
	private static final MoskitoMetaDataAPI API_INSTANCE = new MoskitoMetaDataAPIImpl();

	/**
	 * Private constructor.
	 */
	private MoskitoMetaDataAPIFactory() {
	}

	/**
	 * Get {@link MoskitoMetaDataAPI} instance.
	 * 
	 * @return {@link MoskitoMetaDataAPI}
	 */
	public static MoskitoMetaDataAPI getAPI() {
		return API_INSTANCE;
	}

}
