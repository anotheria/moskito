package net.anotheria.moskito.webui.journey.api;

/**
 * Contains AnalyzedProducerCallsMapAO for producer, category and subsytem, to be used as parameter in methods.
 *
 * @author lrosenberg
 * @since 27.09.18 17:44
 */
public class AnalyzedProducerCallsMapAOWrapper {
	private AnalyzedProducerCallsMapAO byProducer;
	private AnalyzedProducerCallsMapAO byCategory;
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
