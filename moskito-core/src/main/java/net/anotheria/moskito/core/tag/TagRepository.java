package net.anotheria.moskito.core.tag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class contains tags and their values that were set at runtime.
 *
 * @author lrosenberg, strel
 */
public enum TagRepository {

	/**
	 * Singleton instance.
	 */
	INSTANCE;

	/**
	 * Map of tag values.
	 */
	private final Map<String, Tag> tagsMap = new ConcurrentHashMap<>();

	/**
	 * Lock for write operations.
	 */
	private final ReadWriteLock lock = new ReentrantReadWriteLock();

	/**
	 * Adds tag and its value.
	 *
	 * @param name  Tag name
	 * @param value Tag value
	 */
	public void addTag(String name, String value, TagType type, String source) {
		Tag tag = getTag(name, type, source);
		tag.addValue(value);
	}

	private Tag getTag(String name, TagType type, String source) {
		lock.writeLock().lock();
		try {
			Tag tag = tagsMap.get(name);
			if (tag == null) {
				tag = new Tag(name, type, source);
				tagsMap.put(name, tag);
			}
			return tag;
		} finally {
			lock.writeLock().unlock();
		}
	}

	/**
	 * Return available tags.
	 *
	 * @return available tags
	 */
	public List<Tag> getTags() {
		List<Tag> tags = new ArrayList<>(tagsMap.values());
		Collections.sort(tags);
		return tags;
	}

}
