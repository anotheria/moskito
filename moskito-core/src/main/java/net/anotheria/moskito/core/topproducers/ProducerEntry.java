package net.anotheria.moskito.core.topproducers;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Represents a single producer in the top-producers ranking and holds its accumulated {@link ProducerEntryValue} for
 * every {@link Category}. There is exactly one entry per producer id, shared across all categories.
 *
 * @author lrosenberg
 * @since 18.05.16 00:02
 */
public class ProducerEntry {
	/**
	 * Id of the ranked producer.
	 */
	private String producerId;
	/**
	 * Category of the ranked producer (as reported by the producer itself, not to be confused with {@link Category}).
	 */
	private String producerCategory;
	/**
	 * Subsystem of the ranked producer.
	 */
	private String producerSubsystem;

	/**
	 * Accumulated ranking values per ranking category.
	 */
	private ConcurrentMap<Category, ProducerEntryValue> values = new ConcurrentHashMap<>();

	public String getProducerId() {
		return producerId;
	}

	public void setProducerId(String producerId) {
		this.producerId = producerId;
	}

	public String getProducerCategory() {
		return producerCategory;
	}

	public void setProducerCategory(String producerCategory) {
		this.producerCategory = producerCategory;
	}

	public String getProducerSubsystem() {
		return producerSubsystem;
	}

	public void setProducerSubsystem(String producerSubsystem) {
		this.producerSubsystem = producerSubsystem;
	}

	public ConcurrentMap<Category, ProducerEntryValue> getValues() {
		return values;
	}

	public void setValues(ConcurrentMap<Category, ProducerEntryValue> values) {
		this.values = values;
	}

	/**
	 * Adds a score in the given ranking category, creating the {@link ProducerEntryValue} on first access.
	 * @param category the ranking category.
	 * @param scoreValue the score to add.
	 */
	public void addScore(Category category, int scoreValue) {
		ProducerEntryValue value = values.get(category);
		if (value == null) {
			value = new ProducerEntryValue();
			ProducerEntryValue old = values.putIfAbsent(category, value);
			if (old != null)
				value = old;
		}
		value.addScore(scoreValue);
	}

	/**
	 * Returns the accumulated value for the given ranking category, or null if this producer was never ranked in it.
	 * @param category the ranking category.
	 * @return the accumulated value or null.
	 */
	public ProducerEntryValue getValue(Category category) {
		return values.get(category);
	}

	@Override
	public String toString() {
		return "ProducerEntry{" +
				"producerId='" + producerId + '\'' +
				", values=" + values +
				'}';
	}
}
