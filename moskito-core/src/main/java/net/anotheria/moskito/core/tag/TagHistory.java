package net.anotheria.moskito.core.tag;

import net.anotheria.moskito.core.config.MoskitoConfigurationHolder;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * This class contains 10 last tag values that were set at runtime.
 * This class is an one-value-enum-singleton described by J. Bloch.
 *
 * @author lrosenberg, strel
 */
public enum TagHistory {

	/**
	 * Singleton instance.
	 */
	INSTANCE;

	//its ok to return the original list, since all operations create a copy. The only thing we need to care about is
	//simultaneous add, and that is guarded by the lock.
	/**
	 * Map of tag values.
	 */
	private Map<String, LinkedList<String>> tags = new ConcurrentHashMap<>();

	/**
	 * Lock for write operations.
	 */
	private ReadWriteLock lock = new ReentrantReadWriteLock();

	/**
	 * Adds tag value to history.
	 * History size is limited, and this limit can be configured in TaggingConfig.
	 *
	 * @param name Tag name
	 * @param value Tag value
	 */
	public void addTag(String name, String value){
		lock.writeLock().lock();
		try {
			// Obtaining maximum history size
			int tagHistorySize = MoskitoConfigurationHolder.getConfiguration().getTaggingConfig().getTagHistorySize();

			LinkedList<String> tagValues = tags.get(name);
			if (tagValues == null) {
				tagValues = new LinkedList<>();
			}

			// If we have more then maximum allowed tag values, remove first
			if (tagValues.size() >= tagHistorySize) {
				tagValues.removeFirst();
			}

			// Add new value to history
			tagValues.add(value != null ? value : "");
			tags.put(name, tagValues);
		} finally {
			lock.writeLock().unlock();
		}
	}

	/**
	 * Returns last tag values.
	 */
	public List<String> getTagValues(String tagName){
		return tags.get(tagName);
	}
}
