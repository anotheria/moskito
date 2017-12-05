package net.anotheria.moskito.core.tag;

import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;

import java.util.Collections;
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
		return Collections.unmodifiableList(lastValues);
	}

	public void addValue(String value) {
		int tagHistorySize = MoskitoConfigurationHolder.getConfiguration().getTaggingConfig().getTagHistorySize();

		lock.writeLock().lock();
		try {
			if (lastValues.peekLast() != null && lastValues.peekLast().equals(value)) {
				return;
			}

			if (lastValues.size() >= tagHistorySize) {
				lastValues.removeFirst();
			}

			lastValues.add(value != null ? value : "");
		} finally {
			lock.writeLock().unlock();
		}

	}

	@Override
	public int compareTo(Tag o){
		if (getType().equals(o.getType()))
			return getName().compareTo(o.getName());
		return getType().compareTo(o.getType());
	}
}
