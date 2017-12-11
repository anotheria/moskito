package net.anotheria.moskito.core.tag;

import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
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
	 * List of last tag values.
	 */
	private final LinkedList<String> lastValues;

	/**
	 * Lock for write operations.
	 */
	private final ReadWriteLock lock = new ReentrantReadWriteLock();

	public Tag(String name, TagType type, String source) {
		this.name = name;
		this.type = type;
		this.source = source;
		this.lastValues = new LinkedList<>();
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

	public List<String> getLastValues() {
		return new ArrayList<>(lastValues);
	}

	public void addValue(String value) {
		int tagHistorySize = MoskitoConfigurationHolder.getConfiguration().getTaggingConfig().getTagHistorySize();

		lock.writeLock().lock();
		try {
			final String lastValue = value != null ? value : NULL;
			if (lastValues.contains(lastValue)) {
				return;
			}

			if (lastValues.size() >= tagHistorySize) {
				lastValues.removeFirst();
			}

			lastValues.add(lastValue);
		} finally {
			lock.writeLock().unlock();
		}

	}

	@Override
	public int compareTo(Tag o) {
		if (getType().equals(o.getType()))
			return getName().compareTo(o.getName());
		return getType().compareTo(o.getType());
	}
}
