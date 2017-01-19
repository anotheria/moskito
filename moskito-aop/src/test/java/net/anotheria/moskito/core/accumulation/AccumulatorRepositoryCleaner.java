package net.anotheria.moskito.core.accumulation;

/**
 * @author sshscp
 */
public final class AccumulatorRepositoryCleaner {

	/**
	 * The singleton instance.
	 */
	private static AccumulatorRepositoryCleaner INSTANCE = new AccumulatorRepositoryCleaner();
	/**
	 * Returns the singleton instance of the AccumulatorRepositoryCleaner.
	 * @return the one and only instance.
	 */
	public static AccumulatorRepositoryCleaner getInstance(){
		return INSTANCE;
	}

	private AccumulatorRepositoryCleaner() {
	}

	/**
	 * Unregister existing accumulators and recreate AccumulatorRepository.INSTANCE
	 */
	public void cleanAccumulatorRepository() {
		AccumulatorRepository.getInstance().reset();
	}
}
