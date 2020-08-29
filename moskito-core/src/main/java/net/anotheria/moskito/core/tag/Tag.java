package net.anotheria.moskito.core.tag;

import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Tag.
 *
 * @author esmakula
 */
public class Tag implements Comparable<Tag> {

	/**
	 * String null value.
	 */
	private final static String NULL = "null";
	/**
	 * Tag name.
	 */
	private final String name;

	/**
	 * {@link TagType}.
	 */
	private final TagType type;

	/**
	 * Source for tag value.
	 */
	private final String source;

	/**
	 * Map of tag values and its entry amount.
	 */
	private final Map<String, Integer> values;

	/**
	 * Lock for write operations.
	 */
	private final ReadWriteLock lock = new ReentrantReadWriteLock();

	public Tag(String name, TagType type, String source) {
		this.name = name;
		this.type = type;
		this.source = source;
		this.values = new LinkedHashMap<>();
	}

	public String getName() {
		return name;
	}

	public TagType getType() {
		return type;
	}

	public String getSource() {
		return source;
	}

	public Map<String, Integer> getLastValues() {
		return new LinkedHashMap<>(values);
	}

	public void addValue(String value) {
		int tagHistorySize = MoskitoConfigurationHolder.getConfiguration().getTaggingConfig().getTagHistorySize();

		lock.writeLock().lock();
		try {
			final String lastValue = value != null ? value : NULL;
			Integer amount = values.get(lastValue);
			if (amount != null) {
				values.put(lastValue, ++amount);
				return;
			}

			if (values.size() >= tagHistorySize) {
				values.remove(values.keySet().iterator().next());
			}

			values.put(lastValue, 1);
		} finally {
			lock.writeLock().unlock();
		}

	}

	@Override
	public int compareTo(Tag o) {
		int typeCompare = getType().compareTo(o.getType());
		return typeCompare != 0 ?
				typeCompare : getName().compareTo(o.getName());
	}

	@Override
	public boolean equals(Object obj) {
		return getType()==((Tag)obj).getType() &&
				getName().equals(((Tag)obj).getName());
	}
}
