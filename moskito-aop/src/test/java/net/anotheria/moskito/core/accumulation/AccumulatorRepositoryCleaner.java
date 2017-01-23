package net.anotheria.moskito.core.accumulation;

/**
 * Helper class that provides method for cleaning accumulator registry between junit tests.
 * @author sshscp
 */
public final class AccumulatorRepositoryCleaner {

	/**
	 * The singleton instance.
	 */
	private static final AccumulatorRepositoryCleaner INSTANCE = new AccumulatorRepositoryCleaner();

	/**
	 * Returns the singleton instance of the AccumulatorRepositoryCleaner.
	 *
	 * @return the one and only instance.
	 */
	public static AccumulatorRepositoryCleaner getInstance() {
		return INSTANCE;
	}

	private AccumulatorRepositoryCleaner() {
	}

	/**
	 * Detach accumulators from listeners, remove them from repository and recreate AccumulatorRepository.INSTANCE
	 */
	public void cleanAccumulatorRepository() {
		AccumulatorRepository<?> accumulatorRepository = AccumulatorRepository.getInstance();
		for (Accumulator accumulator : accumulatorRepository.getAccumulators()) {
			accumulatorRepository.removeTieable(accumulator.getDefinition());
		}
		accumulatorRepository.reset();
	}
}
