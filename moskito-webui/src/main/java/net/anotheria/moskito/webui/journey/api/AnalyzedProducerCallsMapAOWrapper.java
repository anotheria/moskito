package net.anotheria.moskito.webui.journey.api;

/**
 * Contains AnalyzedProducerCallsMapAO for producer, category and subsytem, to be used as parameter in methods.
 *
 * @author lrosenberg
 * @since 27.09.18 17:44
 */
public class AnalyzedProducerCallsMapAOWrapper {
	/**
	 * Calls by producer.
	 */
	private AnalyzedProducerCallsMapAO byProducer;
	/**
	 * Calls by category.
	 */
	private AnalyzedProducerCallsMapAO byCategory;
	/**
	 * Calls by subsystem.
	 */
	private AnalyzedProducerCallsMapAO bySubsystem;

	public AnalyzedProducerCallsMapAO getByProducer() {
		return byProducer;
	}

	public void setByProducer(AnalyzedProducerCallsMapAO byProducer) {
		this.byProducer = byProducer;
	}

	public AnalyzedProducerCallsMapAO getByCategory() {
		return byCategory;
	}

	public void setByCategory(AnalyzedProducerCallsMapAO byCategory) {
		this.byCategory = byCategory;
	}

	public AnalyzedProducerCallsMapAO getBySubsystem() {
		return bySubsystem;
	}

	public void setBySubsystem(AnalyzedProducerCallsMapAO bySubsystem) {
		this.bySubsystem = bySubsystem;
	}
}
